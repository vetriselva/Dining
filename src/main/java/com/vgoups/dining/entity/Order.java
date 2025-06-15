package com.vgoups.dining.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vg_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "o_request_id", referencedColumnName = "u_id", nullable = false)
    private User user;

    @Column(name = "o_status", columnDefinition = "TINYINT")
    private Boolean status = Boolean.TRUE;

    @Column(name = "o_palaced_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime placedAt;

    @Column(name = "o_prepared_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime preparedAt;

    @Column(name = "o_completed_at", columnDefinition =  "TIMESTAMP")
    private LocalDateTime completedAt;

    @OneToOne
    @JoinColumn(name = "o_created_by", referencedColumnName = "u_id", nullable = true)
    private User createdBy;

}
