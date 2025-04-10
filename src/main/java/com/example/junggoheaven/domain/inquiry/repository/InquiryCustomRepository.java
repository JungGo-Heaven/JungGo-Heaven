package com.example.junggoheaven.domain.inquiry.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;

public interface InquiryCustomRepository {
	Page<Inquiry> findAllByForAdmin(String title, String status, String writer, Pageable pageable);
}
