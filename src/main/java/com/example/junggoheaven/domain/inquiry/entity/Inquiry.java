package com.example.junggoheaven.domain.inquiry.entity;

import java.time.LocalDateTime;

import com.example.junggoheaven.domain.inquiry.enums.InquiryStatus;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.common.entity.TimeStamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Inquiry extends TimeStamp {
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writer_id")
	private User writer;
	@Enumerated(EnumType.STRING)
	private InquiryStatus status;
	@Column(nullable = false)
	private String title;
	@Column(columnDefinition = "LONGTEXT")
	private String body;
	@ManyToOne
	@JoinColumn(name = "respondent_id")
	private User respondent;
	@Column(columnDefinition = "LONGTEXT")
	private String response;
	private LocalDateTime responseAt;

	public Inquiry(User writer, String title, String body) {
		this.writer = writer;
		this.title = title;
		this.body = body;
		this.status = InquiryStatus.WAITING;
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateBody(String body) {
		this.body = body;
	}

	public void delete(){
		this.status = InquiryStatus.DELETED;
	}

	public void respond(User respondent, String response) {
		this.respondent = respondent;
		this.response = response;
		this.responseAt = LocalDateTime.now();
		this.status = InquiryStatus.COMPLETED;
	}

	public void updateResponse(String response) {
		this.response = response;
		this.responseAt = LocalDateTime.now();
	}

	public void updateStatus(InquiryStatus status) {
		this.status = status;
	}
}
