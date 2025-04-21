package com.example.junggoheaven.domain.like.entity;

import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes"
	, uniqueConstraints = @UniqueConstraint(name = "unique_product_user", columnNames = {"product_id", "users_id"}))
public class Like {

	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = false)
	private User user;

	@Builder
	private Like(Product product, User user) {
		this.product = product;
		this.user = user;
	}

	public static Like of(Product product, User user) {
		return Like.builder()
			.product(product)
			.user(user)
			.build();
	}
}
