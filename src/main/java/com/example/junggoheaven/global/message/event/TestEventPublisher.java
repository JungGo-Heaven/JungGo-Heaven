package com.example.junggoheaven.global.message.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.junggoheaven.global.message.event.finder.ProductRegisteredEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestEventPublisher {
	private final ApplicationEventPublisher eventPublisher;

	public void publishProductRegisteredEvent(String version, String name) {
		if (version.equals("v1")) {
			eventPublisher.publishEvent(new ProductRegisteredEvent(this, 2L, name));
		}
	}
}
