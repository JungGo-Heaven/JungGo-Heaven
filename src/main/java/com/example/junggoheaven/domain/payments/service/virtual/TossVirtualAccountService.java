package com.example.junggoheaven.domain.payments.service.virtual;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.junggoheaven.domain.payments.dto.response.OrderResponseDto;
import com.example.junggoheaven.domain.payments.dto.response.PaymentApproveResponseDto;
import com.example.junggoheaven.domain.payments.dto.toss.WebhookDataInfo;
import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.payments.entity.VirtualAccount;
import com.example.junggoheaven.domain.payments.enums.Banks;
import com.example.junggoheaven.domain.payments.enums.OrderStatus;
import com.example.junggoheaven.domain.payments.exception.OrderAmountException;
import com.example.junggoheaven.domain.payments.service.order.OrderFinder;
import com.example.junggoheaven.global.aop.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TossVirtualAccountService implements VirtualAccountService {

	private final RestTemplate restTemplate;
	private final OrderFinder orderFinder;
	private final VirtualAccountWriter virtualAccountWriter;

	@Value("${toss.payments.secret-key}")
	private String SECRET_KEY;

	@Transactional
	@Override
	public PaymentApproveResponseDto createVirtualAccount(Long userId, Long orderId, String bank) {
		HttpHeaders headers = createHeaders();
		Order order = orderFinder.findByOrderId(orderId);

		Map<String, Object> payload = Map.of(
			"amount", order.getAmount(),
			"orderId", order.getOrderKey(),
			"orderName", order.getDetails(),
			"customerName", order.getBuyer().getName(),
			"customerEmail", order.getBuyer().getEmail(),
			"customerMobilePhone", order.getBuyer().getPhoneNumber(),
			"bank", Banks.of(bank).getCode(),
			"useEscrow", true,
			"validHours", 12
		);

		// map과 header를 담아서 toss payments api 요청 -> virtual account 생성
		HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
		ResponseEntity<PaymentApproveResponseDto> responseEntity = restTemplate.postForEntity(
			"https://api.tosspayments.com/v1/virtual-accounts",
			request,
			PaymentApproveResponseDto.class
		);

		VirtualAccount account = VirtualAccount.of(order, bank, responseEntity.getBody());
		virtualAccountWriter.save(account);
		order.updateStatus(OrderStatus.VIRTUAL_ACCOUNT_ISSUED);

		return responseEntity.getBody();
	}

	@Payment
	@Override
	@Transactional
	public String virtualWebhook(String eventType, String orderId, String status, WebhookDataInfo data) {
		String orderKey = data == null ? orderId : data.getOrderId();
		String tossStatus = data == null ? status : data.getStatus();
		Order order = orderFinder.findByOrderKey(orderKey);

		if (data != null && !order.getAmount().equals(data.getTotalAmount())) {
			String message = "주문번호=" + orderKey + "\t기대금액=" + order.getAmount() + "\t실제금액=" + data.getTotalAmount();
			throw new OrderAmountException(message);
		}

		// https://docs.tosspayments.com/reference#payment-%EA%B0%9D%EC%B2%B4 status 상태 코드 확인
		switch (tossStatus) {
			case "DONE" -> order.updateStatus(OrderStatus.PAYMENT_COMPLETED);        // 인증된 결제수단으로 요청한 결제가 승인된 상태
			case "CANCELED" -> order.updateStatus(OrderStatus.CANCELED);            // 승인된 결제가 취소된 상태
			case "ABORTED" -> order.updateStatus(OrderStatus.FAILED);                // 결제 승인이 실패한 상태
			case "EXPIRED" -> order.updateStatus(OrderStatus.EXPIRED);                // 결제 유효 시간 30분이 지나 거래가 취소된 상태
		}

		return "update status: " + order.getStatus().name();
	}

	@Override
	public OrderResponseDto sending(Long orderId) {
		Order order = orderFinder.findByOrderId(orderId);
		return OrderResponseDto.from(order);
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		// secret key에 :(콜론)을 붙여 base64로 인코딩 후 Basic 인증 헤더로 사용
		headers.set("Authorization",
			"Basic " + Base64.getEncoder().encodeToString((SECRET_KEY + ":").getBytes(StandardCharsets.UTF_8)));
		headers.setContentType(MediaType.APPLICATION_JSON);

		return headers;
	}
}
