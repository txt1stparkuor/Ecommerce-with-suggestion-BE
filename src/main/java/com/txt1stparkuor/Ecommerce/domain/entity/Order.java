package com.txt1stparkuor.Ecommerce.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false, updatable = false)
    private String orderCode;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String shippingAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void generateOrderCode() {
        if (this.orderCode == null) {
            String shortId = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            this.orderCode = "ORD-" + shortId;
        }
    }
}
