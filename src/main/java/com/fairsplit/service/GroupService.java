package com.fairsplit.service;

import com.fairsplit.dto.GroupDTO;
import com.fairsplit.dto.UserDTO;
import com.fairsplit.entity.AppGroup;
import com.fairsplit.entity.auth.AppUser;
import com.fairsplit.repository.AppGroupRepository;
import com.fairsplit.repository.auth.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CODE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    @Autowired
    private AppGroupRepository groupRepository;

    @Autowired
    private AppUserRepository userRepository;

    private AppUser getLoggedInUser() {
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String generateInviteCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder(8);
            for (int i = 0; i < 8; i++) {
                sb.append(CODE_CHARS.charAt(RANDOM.nextInt(CODE_CHARS.length())));
            }
            code = sb.toString();
        } while (groupRepository.findByInviteCode(code).isPresent());
        return code;
    }

    @Transactional
    public GroupDTO createGroup(String name) {
        AppUser user = getLoggedInUser();

        AppGroup group = new AppGroup();
        group.setName(name);
        group.setInviteCode(generateInviteCode());
        group.getMembers().add(user);

        return mapToDTO(groupRepository.save(group));
    }

    @Transactional
    public GroupDTO joinGroup(String inviteCode) {
        AppUser user = getLoggedInUser();

        AppGroup group = groupRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Invalid invite code"));

        if (group.getMembers().contains(user)) {
            throw new RuntimeException("You are already a member of this group");
        }

        group.getMembers().add(user);
        return mapToDTO(groupRepository.save(group));
    }

    public List<GroupDTO> getMyGroups() {
        AppUser user = getLoggedInUser();
        return groupRepository.findByMembersContaining(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public GroupDTO getGroup(Long id) {
        AppUser user = getLoggedInUser();
        AppGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getMembers().contains(user)) {
            throw new RuntimeException("You are not a member of this group");
        }

        return mapToDTO(group);
    }

    @Transactional
    public GroupDTO regenerateCode(Long id) {
        AppUser user = getLoggedInUser();
        AppGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getMembers().contains(user)) {
            throw new RuntimeException("You are not a member of this group");
        }

        group.setInviteCode(generateInviteCode());
        return mapToDTO(groupRepository.save(group));
    }

    @Transactional
    public void deleteGroup(Long id) {
        AppUser user = getLoggedInUser();
        AppGroup group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        if (!group.getMembers().contains(user)) {
            throw new RuntimeException("You are not a member of this group");
        }

        groupRepository.delete(group);
    }

    private GroupDTO mapToDTO(AppGroup group) {
        GroupDTO dto = new GroupDTO();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setInviteCode(group.getInviteCode());
        dto.setMembers(group.getMembers().stream()
                .map(m -> new UserDTO(m.getId(), m.getName(), m.getEmail()))
                .collect(Collectors.toList()));
        return dto;
    }
}
