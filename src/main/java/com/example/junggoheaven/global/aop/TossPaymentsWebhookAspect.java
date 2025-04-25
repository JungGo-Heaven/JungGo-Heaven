package com.example.junggoheaven.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TossPaymentsWebhookAspect {

	private final ObjectMapper objectMapper;

	@Around("@annotation(com.example.junggoheaven.global.aop.Payment)")
	public void handlerLogging(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] getArgs = joinPoint.getArgs();

		log.info("args: ");
		for (Object arg : getArgs) {
			String s = objectMapper.writeValueAsString(arg);
			log.info(s);
		}

		Object proceed = objectMapper.writeValueAsString(joinPoint.proceed());
		log.info("proceed: {}", proceed);
	}
}
