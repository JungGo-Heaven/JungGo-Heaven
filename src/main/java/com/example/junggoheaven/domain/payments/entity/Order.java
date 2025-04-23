package com.example.junggoheaven.domain.payments.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.junggoheaven.domain.payments.enums.OrderStatus;
import com.example.junggoheaven.domain.payments.enums.PaymentMethod;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String orderKey;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private User buyer;
	@Column(nullable = false)
	private String customerKey;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private User seller;
	@Column(nullable = false)
	private String details;
	@Column(nullable = false)
	private Long amount;
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Builder
	private Order(Product product, User buyer, User seller, String details, Long amount, PaymentMethod method) {
		this.product = product;
		this.buyer = buyer;
		this.customerKey = buyer.getCustomerKey();
		this.seller = seller;
		this.details = details;
		this.amount = amount;
		this.paymentMethod = method;
		this.status = OrderStatus.DONE;
	}

	public static Order of(Product product, User buyer, User seller, String details, Long amount, PaymentMethod method) {
		return Order.builder()
			.product(product).buyer(buyer).seller(seller).details(details).amount(amount).method(method).build();
	}

	public void updateStatus(OrderStatus orderStatus) {
		this.status = orderStatus;
	}

	@PostPersist
	public void generateOrderKey() {
		if (this.orderKey == null && this.id != null) {
			this.orderKey = UUID.nameUUIDFromBytes(("order-" + this.id).getBytes()).toString();
		}
	}
}
