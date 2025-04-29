package com.example.junggoheaven.global.message.publisher;

import org.springframework.stereotype.Component;

import com.example.junggoheaven.global.message.event.finder.OrderStatusChangeEvent;
import com.example.junggoheaven.global.message.event.finder.ProductRegisteredEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestEventPublisher {
	private final EventPublisher eventPublisher;

	public void publishProductRegisteredEvent(String version, String name) {
		if (version.equals("v1")) {
			eventPublisher.publishEvent(new ProductRegisteredEvent(this, 2L, name));
		}
	}

	public void publishOrderStatusChangedEvent(Long userId) {
		eventPublisher.publishEvent(new OrderStatusChangeEvent(this, userId, "DONE"));
	}
}
