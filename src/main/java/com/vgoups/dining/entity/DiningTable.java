package com.vgoups.dining.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vg_dining_tables")
@Where(clause = "dt_deleted_at IS NULL")

public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dt_dining_id")
    private Long diningId;

    @Column(name = "dt_name", length = 50, nullable = false)
    private String name;

    @Column(name = "dt_member_count", columnDefinition = "INT")
    private Integer memberCount = 4;

    @Column(name = "dt_status", columnDefinition = "TINYINT")
    private Boolean status = Boolean.TRUE;

    @Column(name = "dt_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "dt_updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Column(name= "dt_deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = null;
        deletedAt = null;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
