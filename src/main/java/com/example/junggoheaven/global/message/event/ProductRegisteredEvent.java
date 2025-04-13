package com.example.junggoheaven.global.message.event;

import java.time.Clock;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class ProductRegisteredEvent extends ApplicationEvent {
	private final String productName;

	public ProductRegisteredEvent(Object source, String productName) {
		super(source);
		this.productName = productName;
	}

	public ProductRegisteredEvent(Object source, Clock clock, String productName) {
		super(source, clock);
		this.productName = productName;
	}
}
