package com.example.junggoheaven.global.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.port}")
	private String port;
	@Value("${spring.mail.username}")
	private String userName;
	@Value("${spring.mail.password}")
	private String password;



	@Bean
	JavaMailSender javaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); // 인증 필요
		props.put("mail.smtp.starttls.enable", "true"); // TLS 시작 명령 필요
		props.put("mail.smtp.starttls.required", "true"); // "TLS 안되면 아예 실패" 설정
		props.put("mail.smtp.host", host); // Gmail SMTP 서버
		props.put("mail.smtp.port", port); // TLS 기본 포트

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});
		javaMailSender.setSession(session);
		return javaMailSender;
	}

	@Bean
	SimpleMailMessage templateMessage() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("JungGo_Heaven@gmail.com");
		return simpleMailMessage;
	}
}
