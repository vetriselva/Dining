package com.vgoups.dining.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "vg_order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oi_id")
    private Long id;

    @Column(name = "oi_qty")
    private Integer qty;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oi_item_id", referencedColumnName = "i_id", nullable = false)
    private Item items;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oi_prepareb_by_id", referencedColumnName = "u_id", nullable = false)
    private User user;

    @Column(name = "oi_status", nullable = false)
    private String status;

    @Column(name = "oi_active", nullable = false)
    private Boolean active = true;

    @Column(name = "oi_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;



}
