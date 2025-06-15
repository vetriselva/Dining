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
@Table(name = "vg_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_name")
    private String name;

    @Column(name = "u_email", nullable = false)
    private String email;

    @Column(name = "u_password", nullable = false)
    private String password;

    @Column(name = "u_status", columnDefinition = "TINYINT")
    private String status;

    @Column(name = "u_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "u_updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name= "u_deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Column(name = "u_created_by", nullable = false)
    private Long createdBy;

    @Column(name = "u_updated_by", nullable = false)
    private Long updatedBy;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "vg_user_roles",
            joinColumns = @JoinColumn(name = "ur_user_id", referencedColumnName = "u_id"),
            inverseJoinColumns = @JoinColumn(name = "ur_role_id", referencedColumnName = "r_id")
    )

    private Set<Role> roles = new HashSet<>();

}
