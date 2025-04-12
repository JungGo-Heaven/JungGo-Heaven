package com.example.junggoheaven.domain.inquiry.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.inquiry.dto.request.InquiryStatusRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.request.RespondInquiryRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.response.AdminInquiryListResponseDto;
import com.example.junggoheaven.domain.inquiry.dto.response.AdminInquiryResponseDto;
import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.inquiry.eunms.InquiryStatus;
import com.example.junggoheaven.domain.inquiry.exception.AlreadyCompletedInquiryException;
import com.example.junggoheaven.domain.inquiry.exception.AlreadyDeletedInquiryException;
import com.example.junggoheaven.domain.inquiry.exception.InquiryStatusSameException;
import com.example.junggoheaven.domain.inquiry.exception.InvalidRespondentException;
import com.example.junggoheaven.domain.inquiry.exception.RespondNotFoundException;
import com.example.junggoheaven.domain.inquiry.service.component.InquiryFinder;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.service.component.UserFinder;

@ExtendWith(MockitoExtension.class)
class InquiryAdminServiceTest {

	@InjectMocks
	private InquiryAdminService inquiryAdminService;

	@Mock
	private InquiryFinder inquiryFinder;
	@Mock
	private UserFinder userFinder;

	User writer;
	User admin;
	Inquiry inquiry1;
	Inquiry inquiry2;
	Inquiry inquiry3;

	@BeforeEach
	void setUp() {
		writer = new User("email", "password", "writer", "010-0000-0001", "address");
		admin = new User("email", "password", "admin", "010-0000-1000", "address");

		ReflectionTestUtils.setField(writer, "id", 1L);
		ReflectionTestUtils.setField(writer, "createdAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(writer, "modifiedAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));

		ReflectionTestUtils.setField(admin, "id", 2L);
		ReflectionTestUtils.setField(admin, "role", UserRole.ROLE_ADMIN);
		ReflectionTestUtils.setField(admin, "createdAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(admin, "modifiedAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));

		inquiry1 = new Inquiry(writer, "title1", "b");
		inquiry2 = new Inquiry(writer, "title2", "b");
		inquiry3 = new Inquiry(writer, "title3", "b");

		ReflectionTestUtils.setField(inquiry1, "id", 1L);
		ReflectionTestUtils.setField(inquiry1, "createdAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(inquiry1, "modifiedAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));

		ReflectionTestUtils.setField(inquiry2, "id", 2L);
		ReflectionTestUtils.setField(inquiry2, "createdAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(inquiry2, "modifiedAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(inquiry2, "status", InquiryStatus.COMPLETED);
		ReflectionTestUtils.setField(inquiry2, "respondent", admin);

		ReflectionTestUtils.setField(inquiry3, "id", 3L);
		ReflectionTestUtils.setField(inquiry3, "createdAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(inquiry3, "modifiedAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(inquiry3, "status", InquiryStatus.DELETED);
	}

	@Test
	void respondToInquiry() {
		RespondInquiryRequestDto requestDto = new RespondInquiryRequestDto("답안 작성");

		given(inquiryFinder.findInquiryById(any())).willReturn(inquiry1);
		given(userFinder.findByUserId(any())).willReturn(admin);

		assertThat(inquiry1.getStatus()).isEqualTo(InquiryStatus.WAITING);

		AdminInquiryResponseDto responseDto = inquiryAdminService.respondToInquiry(1L, 2L, requestDto);

		assertThat(responseDto).isNotNull();
		assertThat(inquiry1.getStatus()).isEqualTo(InquiryStatus.COMPLETED);
		assertThat(inquiry1.getRespondent()).isEqualTo(admin);
	}

	@Test
	void respondToInquiry_이미_답변한_문의() {
		RespondInquiryRequestDto requestDto = new RespondInquiryRequestDto("답안 작성");

		given(inquiryFinder.findInquiryById(any())).willReturn(inquiry2);

		assertThrows(AlreadyCompletedInquiryException.class, () -> {
			inquiryAdminService.respondToInquiry(2L, 2L, requestDto);
		});
	}

	@Test
	void respondToInquiry_이미_삭제된_문의() {
		RespondInquiryRequestDto requestDto = new RespondInquiryRequestDto("답안 작성");

		given(inquiryFinder.findInquiryById(any())).willReturn(inquiry3);

		assertThrows(AlreadyDeletedInquiryException.class, () -> {
			inquiryAdminService.respondToInquiry(3L, 2L, requestDto);
		});
	}

	@Test
	void updateRespond() {
		String before = inquiry2.getBody();
		String after = "답안 수정";

		RespondInquiryRequestDto requestDto = new RespondInquiryRequestDto(after);
		given(inquiryFinder.findInquiryById(any())).willReturn(inquiry2);

		AdminInquiryResponseDto responseDto = inquiryAdminService.updateRespond(2L, 2L, requestDto);

		assertThat(responseDto).isNotNull();
		assertThat(inquiry2.getResponse()).isNotEqualTo(before);
		assertThat(inquiry2.getResponse()).isEqualTo(after);
	}

	@Test
	void updateRespond_응답이_없는_문의() {
		RespondInquiryRequestDto requestDto = new RespondInquiryRequestDto("수정");
		given(inquiryFinder.findInquiryById(any())).willReturn(inquiry1);

		assertThat(inquiry1.getStatus()).isEqualTo(InquiryStatus.WAITING);
		assertThrows(RespondNotFoundException.class, () -> {
			inquiryAdminService.updateRespond(1L, 2L, requestDto);
		});
	}

	@Test
	void updateRespond_응답_작성자가_일치하지_않음() {
		RespondInquiryRequestDto requestDto = new RespondInquiryRequestDto("수정");
		given(inquiryFinder.findInquiryById(any())).willReturn(inquiry2);

		assertThrows(InvalidRespondentException.class, () -> {
			inquiryAdminService.updateRespond(2L, 1L, requestDto);
		});
	}

	@Test
	void changeInquiryStatus() {
		InquiryStatus status = InquiryStatus.COMPLETED;
		InquiryStatusRequestDto requestDto = new InquiryStatusRequestDto(1L, status);
		given(inquiryFinder.findInquiryById(any())).willReturn(inquiry1);

		AdminInquiryResponseDto responseDto = inquiryAdminService.changeInquiryStatus(requestDto);

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getInquiryStatus()).isEqualTo(status.name());
		assertThat(responseDto.getInquiryStatus()).isEqualTo(inquiry1.getStatus().name());
	}

	@Test
	void changeInquiryStatus_동일한_상태코드() {
		InquiryStatus status = inquiry1.getStatus();
		InquiryStatusRequestDto requestDto = new InquiryStatusRequestDto(1L, status);
		given(inquiryFinder.findInquiryById(any())).willReturn(inquiry1);

		assertThrows(InquiryStatusSameException.class, () -> {
			inquiryAdminService.changeInquiryStatus(requestDto);
		});
	}

	@Test
	void getInquiryList() {
		String status = InquiryStatus.COMPLETED.name();
		Page<Inquiry> inquiryPage = new PageImpl(List.of(inquiry2, inquiry3));

		given(inquiryFinder.findAllByForAdmin(any(), any(), any(), any())).willReturn(inquiryPage);

		Page<AdminInquiryListResponseDto> responseDto = inquiryAdminService.getInquiryList(null, status, null, 0, 10);
		List<AdminInquiryListResponseDto> inquiries = responseDto.getContent();

		assertThat(responseDto.getTotalElements()).isEqualTo(2);
		assertThat(inquiries.get(0).getInquiryId()).isEqualTo(inquiry2.getId());

	}
}