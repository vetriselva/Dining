package com.vgoups.dining.entity;

import com.vgoups.dining.dto.order.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "vg_orders")
@Where(clause = "o_deleted_at IS NULL")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "o_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "o_request_id", referencedColumnName = "u_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "o_status", nullable = false)
    private OrderStatus status = OrderStatus.STARTED;

    @Column(name = "o_placed_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime placedAt;

    @Column(name = "o_prepared_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime preparedAt;

    @Column(name = "o_completed_at", columnDefinition =  "TIMESTAMP")
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "o_created_by", referencedColumnName = "u_id")
    private User createdBy;

    @Column(name = "o_deleted_at", columnDefinition =  "TIMESTAMP")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    private void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
    }

    @PrePersist
    protected void onCreate() {
        this.placedAt = LocalDateTime.now();
    }

}
