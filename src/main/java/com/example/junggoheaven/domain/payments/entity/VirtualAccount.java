package com.example.junggoheaven.domain.payments.entity;

import com.example.junggoheaven.domain.payments.dto.response.PaymentApproveResponseDto;
import com.example.junggoheaven.domain.payments.enums.Banks;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "virtual_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VirtualAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "orders_id")
	private Order order;
	@Column(nullable = false, name = "orders_key")
	private String orderIdKey;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Banks bank;
	@Column(unique = true, nullable = false)
	private String accountNumber;
	@Column(nullable = false)
	private Double amount;
	@Column(nullable = false)
	private String depositPeriod;
	@Column(nullable = false)
	private String customerKey;

	@Builder
	private VirtualAccount(Order order, String accountNumber, Banks bank, Double amount,
		String depositPeriod, String customerKey) {
		this.order = order;
		this.orderIdKey = order.getOrderKey();
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.amount = amount;
		this.depositPeriod = depositPeriod;
		this.customerKey = customerKey;
	}

	public static VirtualAccount of(Order order, String bank, PaymentApproveResponseDto body) {
		return VirtualAccount.builder()
			.order(order)
			.bank(Banks.of(bank))
			.accountNumber(body.getAccountNumber())
			.depositPeriod(body.getDueDate())
			.amount(body.getTotalAmount().doubleValue())
			.customerKey(order.getCustomerKey())
			.build();
	}
}
