package com.pooling.ledger.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ride_groups")
public class RideGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Column(unique = true)
    private String inviteCode;

    @ManyToMany
    @JoinTable(
            name = "resident_ride_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "resident_id")
    )
    private List<Resident> members = new ArrayList<>();

    @OneToMany(mappedBy = "rideGroup")
    private List<Ride> rides = new ArrayList<>();

    public RideGroup() {
        this.inviteCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public RideGroup(String name, String description) {
        this.name = name;
        this.description = description;
        this.inviteCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInviteCode() { return inviteCode; }
    public void setInviteCode(String inviteCode) { this.inviteCode = inviteCode; }

    public List<Resident> getMembers() { return members; }
    public void setMembers(List<Resident> members) { this.members = members; }

    public List<Ride> getRides() { return rides; }
    public void setRides(List<Ride> rides) { this.rides = rides; }
}