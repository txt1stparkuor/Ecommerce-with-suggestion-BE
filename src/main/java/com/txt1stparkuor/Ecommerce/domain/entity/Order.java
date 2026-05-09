package com.txt1stparkuor.Ecommerce.domain.entity;

import com.txt1stparkuor.Ecommerce.constant.enums.OrderStatus;
import com.txt1stparkuor.Ecommerce.domain.entity.common.DateAuditing;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false, updatable = false)
    private String orderCode;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String shippingAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;

    @PrePersist
    public void generateOrderCode() {
        if (this.orderCode == null) {
            String shortId = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            this.orderCode = "ORD-" + shortId;
        }
    }
}
