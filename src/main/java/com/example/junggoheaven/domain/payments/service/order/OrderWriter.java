package com.example.junggoheaven.domain.payments.service.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.payments.enums.OrderStatus;
import com.example.junggoheaven.domain.payments.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderWriter {

	private final OrderRepository orderRepository;

	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

}
