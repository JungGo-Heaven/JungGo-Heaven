package com.example.junggoheaven.global.message.service.email;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender mailSender;
	private final SimpleMailMessage templateMessage;

	@Async
	public void sendEmail(String to, String subject, String text) {
		// Create a thread-safe "copy" of the template message and customize it
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);

		msg.setTo(to);
		msg.setSubject(subject);
		msg.setText(text);

		try {
			mailSender.send(msg);
			log.info("Email sent successfully");
		} catch (MailException e) {
			log.error("{} 에게 메일을 보낼 수 없습니다.\nReason: {}", to, e.getMessage());
		}
	}
}
