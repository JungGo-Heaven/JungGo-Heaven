package com.example.junggoheaven.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
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
public class HandlerLoggingAspect {

	private final ObjectMapper objectMapper;

	@Before("@annotation(com.example.junggoheaven.global.aop.Handler)")
	public void handlerLogging(ProceedingJoinPoint joinPoint) throws Throwable {
		String className = joinPoint.getSignature().getDeclaringTypeName();
		Object[] getArgs = joinPoint.getArgs();
		String args = objectMapper.writeValueAsString(getArgs);

		log.info("className: {}", className);
		log.info("args: {}", args);
	}
}
