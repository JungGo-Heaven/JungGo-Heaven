package com.example.junggoheaven.domain.payments.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.junggoheaven.domain.payments.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

	@EntityGraph(attributePaths = {"product", "buyer", "seller"})
	Optional<Order> findById(Long orderId);

	@EntityGraph(attributePaths = {"product", "buyer", "seller"})
	Optional<Order> findByOrderKey(String orderKey);
}
