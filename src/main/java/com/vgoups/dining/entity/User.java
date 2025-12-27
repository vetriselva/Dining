package com.vgoups.dining.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "vg_users")
@Where(clause = "u_deleted_at IS NULL")

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
    @JsonIgnore
    private String password;

    @Column(name = "u_status", columnDefinition = "TINYINT")
    private Boolean status;

    @Column(name = "u_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "u_updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name= "u_deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Column(name = "u_created_by")
    private Long createdBy;

    @Column(name = "u_updated_by")
    private Long updatedBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vg_user_roles",
            joinColumns = @JoinColumn(name = "ur_user_id", referencedColumnName = "u_id"),
            inverseJoinColumns = @JoinColumn(name = "ur_role_id", referencedColumnName = "r_id")
    )
    @JsonManagedReference
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = 1L;
        this.updatedBy = 1L;
    }

}
