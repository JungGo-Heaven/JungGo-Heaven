package com.example.junggoheaven.global.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Aspect
@Component
public class FilterAspect {

	@PersistenceContext
	private EntityManager entityManager;

	@Around("execution(* com.example.junggoheaven.domain.user.repository.*Repository*.*(..))")
	public Object enableFilter(ProceedingJoinPoint joinPoint) throws Throwable {
		Session session = entityManager.unwrap(Session.class);
		session.enableFilter("deletedFilter");

		try {
			return joinPoint.proceed();
		} finally {
			session.disableFilter("deletedFilter");
		}
	}
}
