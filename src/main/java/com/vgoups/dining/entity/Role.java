package com.vgoups.dining.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vg_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long roleId;

    @Column(name = "r_role_name", nullable = false, length = 50)
    private String roleName;

    @Column(name = "r_status", columnDefinition = "TINYINT")
    private Boolean status;

    @Column(name = "r_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "r_updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "r_deleted_at", columnDefinition =  "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Column(name = "r_created_by", nullable = true)
    private Long createdBy;

    @Column(name = "r_updated_by", nullable = true)
    private Long updatedBy;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }
}
