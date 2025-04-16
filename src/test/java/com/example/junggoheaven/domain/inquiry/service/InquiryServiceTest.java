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

import com.example.junggoheaven.domain.inquiry.dto.request.CreateInquiryRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.request.UpdateInquiryRequestDto;
import com.example.junggoheaven.domain.inquiry.dto.response.UserInquiryListResponseDto;
import com.example.junggoheaven.domain.inquiry.dto.response.UserInquiryResponseDto;
import com.example.junggoheaven.domain.inquiry.entity.Inquiry;
import com.example.junggoheaven.domain.inquiry.enums.InquiryStatus;
import com.example.junggoheaven.domain.inquiry.exception.InvalidInquiryException;
import com.example.junggoheaven.domain.inquiry.service.component.InquiryFinder;
import com.example.junggoheaven.domain.inquiry.service.component.InquiryWriter;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;

@ExtendWith(MockitoExtension.class)
class InquiryServiceTest {

	@InjectMocks
	private InquiryService inquiryService;

	@Mock
	private InquiryWriter inquiryWriter;
	@Mock
	private InquiryFinder inquiryFinder;
	@Mock
	private UserFinder userFinder;

	User writer;
	Long userId = 1L;
	Inquiry inquiry1;
	Inquiry inquiry2;
	Long inquiryId1 = 11L;
	Long inquiryId2 = 12L;

	@BeforeEach
	void setUp() {
		writer = new User("email@email.com", "password", "name", "010-0000-0001", "address");
		ReflectionTestUtils.setField(writer, "id", userId);
		ReflectionTestUtils.setField(writer, "createdAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(writer, "modifiedAt", LocalDateTime.of(2010, 1, 1, 0, 0, 0));

		inquiry1 = new Inquiry(writer, "title1", "body1");
		inquiry2 = new Inquiry(writer, "title2", "body2");
		ReflectionTestUtils.setField(inquiry1, "id", inquiryId1);
		ReflectionTestUtils.setField(inquiry1, "createdAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(inquiry1, "modifiedAt", LocalDateTime.of(2010, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(inquiry2, "id", inquiryId2);
		ReflectionTestUtils.setField(inquiry2, "createdAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
		ReflectionTestUtils.setField(inquiry2, "modifiedAt", LocalDateTime.of(2010, 1, 1, 0, 0, 0));

	}

	@Test
	void createInquiry() {
		String title = "title";
		String body = "body";
		CreateInquiryRequestDto requestDto = new CreateInquiryRequestDto(title, body);

		Inquiry inquiry = new Inquiry(writer, title, body);
		ReflectionTestUtils.setField(inquiry, "createdAt", LocalDateTime.of(2025, 1, 30, 0, 0, 0));

		given(userFinder.findByUserId(userId)).willReturn(writer);
		given(inquiryWriter.saveInquiry(any())).willReturn(inquiry);

		UserInquiryResponseDto response = inquiryService.createInquiry(userId, requestDto);
		assertThat(response).isNotNull();
		assertThat(response.getTitle()).isEqualTo(title);
		assertThat(response.getCreatedAt()).isNotNull();
		assertThat(response.getWriterEmail()).isEqualTo(writer.getEmail());
	}

	@Test
	void getMyInquiries() {
		Page<Inquiry> page = new PageImpl<>(List.of(inquiry1, inquiry2));

		given(inquiryFinder.findAllByWriterForUser(any(), any())).willReturn(page);
		Page<UserInquiryListResponseDto> pages = inquiryService.getMyInquiries(userId, 0, 10);
		List<UserInquiryListResponseDto> lists = pages.getContent();

		assertThat(pages).isNotNull();
		assertThat(pages.getTotalElements()).isEqualTo(2);
		assertThat(lists.get(0).getInquiryId()).isEqualTo(11L);
		assertThat(lists.get(1).getInquiryId()).isEqualTo(12L);
	}

	@Test
	void getInquiry_확인_가능() {
		AuthUser validUser = new AuthUser(userId, writer.getEmail(), UserRole.ROLE_USER, writer.getName());
		AuthUser admin = new AuthUser(2L, "email", UserRole.ROLE_ADMIN, "admin");

		given(inquiryFinder.findByIdForUser(any())).willReturn(inquiry1);

		UserInquiryResponseDto writerUser = inquiryService.getInquiry(validUser, inquiryId1);
		assertThat(writerUser).isNotNull();
		assertThat(writerUser.getWriterEmail()).isEqualTo(writer.getEmail());

		UserInquiryResponseDto adminUser = inquiryService.getInquiry(admin, inquiryId1);
		assertThat(adminUser).isNotNull();
	}

	@Test
	void getInquiry_확인_불가능_일반_다른_유저() {
		AuthUser invalidUser = new AuthUser(2L, "email", UserRole.ROLE_USER, writer.getName());

		given(inquiryFinder.findByIdForUser(any())).willReturn(inquiry1);

		assertThrows(InvalidInquiryException.class, () -> {
			inquiryService.getInquiry(invalidUser, inquiryId1);
		});
	}

	@Test
	void updateInquiry_제목_변경() {
		String title = "변경한 title";
		UpdateInquiryRequestDto requestDto = new UpdateInquiryRequestDto(title, null);

		given(inquiryFinder.findByValidWriter(any(), any())).willReturn(inquiry1);

		UserInquiryResponseDto responseDto = inquiryService.updateInquiry(inquiryId1, userId, requestDto);

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getTitle()).isEqualTo(title);
		assertThat(inquiry1.getTitle()).isEqualTo(title);
	}

	@Test
	void updateInquiry_내용_변경() {
		String body = "내용 변경";
		UpdateInquiryRequestDto requestDto = new UpdateInquiryRequestDto(null, body);

		given(inquiryFinder.findByValidWriter(any(), any())).willReturn(inquiry1);

		UserInquiryResponseDto responseDto = inquiryService.updateInquiry(inquiryId1, userId, requestDto);

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getBody()).isEqualTo(body);
		assertThat(inquiry1.getBody()).isEqualTo(body);
	}

	@Test
	void updateInquiry_삭제된_문의_에러() {
		UpdateInquiryRequestDto requestDto = new UpdateInquiryRequestDto("t", "b");

		ReflectionTestUtils.setField(inquiry2, "status", InquiryStatus.DELETED);
		given(inquiryFinder.findByValidWriter(any(), any())).willReturn(inquiry2);

		assertThrows(InvalidInquiryException.class, () -> {
			inquiryService.updateInquiry(inquiryId2, userId, requestDto);
		});
	}

	@Test
	void deleteInquiry() {
		given(inquiryFinder.findByValidWriter(any(), any())).willReturn(inquiry1);
		inquiryService.deleteInquiry(inquiryId1, userId);

		assertThat(inquiry1.getStatus()).isEqualTo(InquiryStatus.DELETED);
	}
}