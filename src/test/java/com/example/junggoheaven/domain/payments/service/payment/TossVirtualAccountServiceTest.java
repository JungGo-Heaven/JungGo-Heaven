package com.example.junggoheaven.domain.payments.service.payment;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.example.junggoheaven.domain.payments.dto.response.OrderResponseDto;
import com.example.junggoheaven.domain.payments.dto.response.PaymentApproveResponseDto;
import com.example.junggoheaven.domain.payments.dto.toss.VirtualAccountInfo;
import com.example.junggoheaven.domain.payments.dto.toss.WebhookDataInfo;
import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.payments.entity.VirtualAccount;
import com.example.junggoheaven.domain.payments.enums.OrderStatus;
import com.example.junggoheaven.domain.payments.enums.PaymentMethod;
import com.example.junggoheaven.domain.payments.exception.OrderAmountException;
import com.example.junggoheaven.domain.payments.service.order.OrderFinder;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class TossVirtualAccountServiceTest {

	@InjectMocks
	private TossVirtualAccountService tossVirtualAccountService;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private OrderFinder orderFinder;
	@Mock
	private VirtualAccountWriter virtualAccountWriter;

	VirtualAccount virtualAccount;
	VirtualAccountInfo accountInfo;
	PaymentApproveResponseDto approve;

	Order order;
	User buyer;
	User seller;

	@BeforeEach
	void setUp() {
		buyer = User.of("buyer@email.com", "buyer", "123");
		seller = User.of("buyer@email.com", "buyer", "123");
		Product product = new Product(seller, "상품", "판매 상품", 500L);
		order = Order.of(product, buyer, seller, "상품 이름", 300L, PaymentMethod.VIRTUAL_ACCOUNT);

		ReflectionTestUtils.setField(order, "id", 1L);
		ReflectionTestUtils.setField(order, "status", OrderStatus.DONE);
		ReflectionTestUtils.setField(order, "customerKey", "customerKey");
		ReflectionTestUtils.setField(order, "orderKey", "orderKey");

		accountInfo = VirtualAccountInfo.of("accountNumber", "06", "customerName", "dueDate",
			true, "settlementStatus", "refundStatus");
		approve = PaymentApproveResponseDto.of("paymentKey", "orderId", "orderName", "status",
			"requestedAt", "approvedAt", 500L, "method", accountInfo);
		virtualAccount = VirtualAccount.of(order, "KB", approve);
	}

	@Test
	void createVirtualAccount() {
		ResponseEntity<PaymentApproveResponseDto> responseEntity = ResponseEntity.of(Optional.of(approve));

		given(orderFinder.findByOrderId(any())).willReturn(order);
		given(restTemplate.postForEntity(
			any(String.class),
			any(HttpEntity.class),
			eq(PaymentApproveResponseDto.class)
		)).willReturn(responseEntity);

		PaymentApproveResponseDto response = tossVirtualAccountService.createVirtualAccount(1L, 1L, "KB");

		assertThat(response).isNotNull();
		assertThat(response.getVirtualAccount().getAccountNumber()).isEqualTo(virtualAccount.getAccountNumber());
	}

	@Test
	void virtualWebhook() {
		String DONE = "DONE";
		String CANCELED = "CANCELED";
		String ABORTED = "ABORTED";
		String EXPIRED = "EXPIRED";

		WebhookDataInfo data = WebhookDataInfo.of("lastTransactionKey", "paymentKey", "orderId", "orderName",
			DONE, "requestedAt", "useEscrow", "secret", 300L, 30L, "method", accountInfo, null, null, null, null);

		given(orderFinder.findByOrderKey(any())).willReturn(order);

		tossVirtualAccountService.virtualWebhook("event", "orderId", DONE, data);
		assertThat(order.getStatus()).isEqualTo(OrderStatus.PAYMENT_COMPLETED);

		tossVirtualAccountService.virtualWebhook("event", "orderId", CANCELED, null);
		assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELED);

		tossVirtualAccountService.virtualWebhook("event", "orderId", ABORTED, null);
		assertThat(order.getStatus()).isEqualTo(OrderStatus.FAILED);

		String expired = tossVirtualAccountService.virtualWebhook("event", "orderId", EXPIRED, null);
		assertThat(order.getStatus()).isEqualTo(OrderStatus.EXPIRED);

		String result = "update status: " + order.getStatus().name();
		assertThat(expired).isNotNull();
		assertThat(expired).isEqualTo(result);
	}

	@Test
	void virtualWebhook_가격_비교_실패() {
		WebhookDataInfo data = WebhookDataInfo.of("lastTransactionKey", "paymentKey", "orderId", "orderName",
			"DONE", "requestedAt", "useEscrow", "secret", 3000L, 30L, "method", accountInfo, null, null, null, null);
		given(orderFinder.findByOrderKey(any())).willReturn(order);

		assertThrows(OrderAmountException.class, () -> {
			tossVirtualAccountService.virtualWebhook("event", "orderId", "DONE", data);
		});
	}

	@Test
	void sending() {
		given(orderFinder.findByOrderId(any())).willReturn(order);
		OrderResponseDto result = tossVirtualAccountService.sending(1L);
		assertThat(result).isNotNull();
		assertThat(result.getOrderId()).isEqualTo(order.getOrderKey());
	}
}