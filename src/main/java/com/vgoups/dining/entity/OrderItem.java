package com.vgoups.dining.entity;

import com.vgoups.dining.dto.orderItem.OrderItemStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vg_order_items")
@Where(clause = "oi_deleted_at IS NULL")

public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oi_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oi_order_id", referencedColumnName = "o_id")
    private Order order;

    @Column(name = "oi_qty")
    private Integer qty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oi_item_id", referencedColumnName = "i_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oi_prepared_by_id", referencedColumnName = "u_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "oi_status", nullable = false)
    private OrderItemStatus status;

    @Column(name = "oi_active", nullable = false)
    private Boolean active = true;

    @Column(name = "oi_created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "oi_deleted_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
