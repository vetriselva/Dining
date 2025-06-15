package com.vgoups.dining.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vg_items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="i_id")
    private Long id;

    @Column(name = "i_name", nullable = false, length = 255)
    private String name;

    @Column(name = "i_description", nullable = true, columnDefinition = "LONGTEXT")
    private String description;

    @Column(name = "i_status", columnDefinition = "TINYINT")
    private Boolean status;

    @Column(name = "i_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "i_updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "i_deleted_at", columnDefinition =  "TIMESTAMP")
    private LocalDateTime deletedAt;

    @Column(name = "i_created_by", nullable = true)
    private Long createdBy;

    @Column(name = "i_updated_by", nullable = true)
    private Long updatedBy;
}
