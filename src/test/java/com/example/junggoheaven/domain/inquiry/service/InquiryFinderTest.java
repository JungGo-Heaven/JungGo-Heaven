package com.example.junggoheaven.domain.inquiry.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.inquiry.exception.InquiryNotFoundException;
import com.example.junggoheaven.domain.inquiry.exception.InvalidInquiryException;
import com.example.junggoheaven.domain.inquiry.repository.InquiryRepository;
import com.example.junggoheaven.domain.inquiry.service.component.InquiryFinder;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserRole;

@ExtendWith(MockitoExtension.class)
class InquiryFinderTest {

	@InjectMocks
	private InquiryFinder inquiryFinder;

	@Mock
	private InquiryRepository inquiryRepository;

	Inquiry inquiry;
	User writer;
	User respondent;

	@BeforeEach
	void setUp() {
		writer = new User("이메일", "비밀번호", "이름", "번호", "주소");
		ReflectionTestUtils.setField(writer, "id", 11L);
		ReflectionTestUtils.setField(writer, "createdAt", LocalDateTime.of(2020,4,1,12,0,0));
		ReflectionTestUtils.setField(writer, "modifiedAt", LocalDateTime.of(2023,5,1,12,0,0));

		respondent = new User("이메일", "비밀번호", "이름", "번호", "주소");
		ReflectionTestUtils.setField(respondent, "id", 12L);
		ReflectionTestUtils.setField(respondent, "createdAt", LocalDateTime.of(2020,4,1,12,0,0));
		ReflectionTestUtils.setField(respondent, "modifiedAt", LocalDateTime.of(2023,5,1,12,0,0));
		ReflectionTestUtils.setField(respondent, "role", UserRole.ROLE_ADMIN);

		inquiry = new Inquiry(writer, "문의 제목", "문의 내용");
		ReflectionTestUtils.setField(inquiry, "id", 1L);
		ReflectionTestUtils.setField(inquiry, "createdAt", LocalDateTime.of(2025,3,1,12,0,0));
		ReflectionTestUtils.setField(inquiry, "modifiedAt", LocalDateTime.of(2025,3,1,12,0,0));
	}

	@Test
	void findInquiryById() {
		given(inquiryRepository.findById(any())).willReturn(Optional.of(inquiry));
		Inquiry find = inquiryFinder.findInquiryById(1L);
		assertThat(find).isEqualTo(inquiry);
	}

	@Test
	void findInquiryById_empty() {
		given(inquiryRepository.findById(any())).willReturn(Optional.empty());
		assertThrows(InquiryNotFoundException.class, () ->{
			inquiryFinder.findInquiryById(1L);
		});
	}

	@Test
	void findByIdForUser() {
		given(inquiryRepository.findByIdAndStatusIsNotDeleted(any())).willReturn(Optional.of(inquiry));
		Inquiry nonDeleted = inquiryFinder.findByIdForUser(1L);
		assertThat(nonDeleted).isEqualTo(inquiry);
	}

	@Test
	void findByIdForUser_empty() {
		given(inquiryRepository.findByIdAndStatusIsNotDeleted(any())).willReturn(Optional.empty());
		assertThrows(InquiryNotFoundException.class, () ->{
			inquiryFinder.findByIdForUser(1L);
		});
	}

	@Test
	void findByValidWriter() {
		given(inquiryRepository.findById(any())).willReturn(Optional.of(inquiry));
		Inquiry validWriter = inquiryFinder.findByValidWriter(1L, 11L);
		assertThat(validWriter).isEqualTo(inquiry);
		assertThat(validWriter.getWriter()).isEqualTo(writer);
	}

	@Test
	void findByValidWriter_작성자가_유효하지_않음() {
		given(inquiryRepository.findById(any())).willReturn(Optional.of(inquiry));
		assertThrows(InvalidInquiryException.class, () ->{
			inquiryFinder.findByValidWriter(1L, 12L);
		});
	}

	@Test
	void findAllByWriterForUser() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Inquiry> page = new PageImpl(List.of(inquiry));
		given(inquiryRepository.findAllByWriterIdAndStatusIsNotDeleted(any(), any())).willReturn(page);

		Page<Inquiry> all = inquiryFinder.findAllByWriterForUser(1L, pageable);

		assertThat(all.getTotalElements()).isEqualTo(1);
		assertThat(all.getContent().get(0).getWriter()).isEqualTo(writer);
	}

	@Test
	void findAllByForAdmin() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Inquiry>  page = new PageImpl(List.of(inquiry));
		given(inquiryRepository.findAllByForAdmin(any(), any(), any(), any())).willReturn(page);

		Page<Inquiry> all = inquiryFinder.findAllByForAdmin(null, null, null, pageable);
		assertThat(all.getTotalElements()).isEqualTo(1);
	}
}