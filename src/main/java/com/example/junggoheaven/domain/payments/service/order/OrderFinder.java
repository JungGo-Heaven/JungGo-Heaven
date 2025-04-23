package com.example.junggoheaven.domain.payments.service.order;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.payments.exception.OrderNotFoundException;
import com.example.junggoheaven.domain.payments.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class OrderFinder {

	private final OrderRepository orderRepository;

	public Order findByOrderId(Long orderId){
		return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
	}

	public Order findByOrderKey(String orderKey){
		return orderRepository.findByOrderKey(orderKey).orElseThrow(OrderNotFoundException::new);
	}
}
