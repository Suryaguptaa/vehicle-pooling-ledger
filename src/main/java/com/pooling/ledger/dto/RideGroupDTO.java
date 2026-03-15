package com.pooling.ledger.dto;

public class RideGroupDTO {

    private Long id;
    private String name;
    private String description;
    private String inviteCode;

    public RideGroupDTO() {}

    public RideGroupDTO(Long id, String name, String description, String inviteCode) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.inviteCode = inviteCode;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInviteCode() { return inviteCode; }
    public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }
}