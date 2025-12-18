package com.showtime.payment_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_orders")
public class UserOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    // 👤 User Details
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;

    // 💰 Order Details
    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String orderStatus;
    // CREATED, PAYMENT_PENDING, SUCCESS, FAILED

    // 💳 Payment Details
    private String paymentMethod;

    @Column(nullable = false, unique = true)
    private String razorpayOrderId;

    // ✅ ADD THESE
    private String razorpayPaymentId;   // transactionId
    private String razorpaySignature;

    // 🕒 Audit
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
