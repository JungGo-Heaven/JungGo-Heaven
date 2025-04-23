package com.example.junggoheaven.domain.payments.service.virtual;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.payments.dto.response.PaymentApproveResponseDto;
import com.example.junggoheaven.domain.payments.dto.toss.VirtualAccountInfo;
import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.payments.entity.VirtualAccount;
import com.example.junggoheaven.domain.payments.enums.OrderStatus;
import com.example.junggoheaven.domain.payments.enums.PaymentMethod;
import com.example.junggoheaven.domain.payments.repository.VirtualAccountRepository;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class VirtualAccountWriterTest {

	@InjectMocks
	private VirtualAccountWriter virtualAccountWriter;
	@Mock
	private VirtualAccountRepository virtualAccountRepository;

	VirtualAccount virtualAccount;

	@BeforeEach
	void setUp() {
		User buyer = User.of("buyer@email.com", "buyer", "123");
		User seller = User.of("buyer@email.com", "buyer", "123");
		Product product = new Product(seller, "상품", "판매 상품", 500L);
		Order order = Order.of(product, buyer, seller, "상품 이름", 300L, PaymentMethod.VIRTUAL_ACCOUNT);

		ReflectionTestUtils.setField(order, "id", 1L);
		ReflectionTestUtils.setField(order, "status", OrderStatus.DONE);

		VirtualAccountInfo accountInfo = VirtualAccountInfo.of("accountNumber", "bankCode", "customerName", "dueDate", true, "settlementStatus", "refundStatus");
		PaymentApproveResponseDto approve = PaymentApproveResponseDto.of("paymentKey", "orderId", "orderName", "status", "requestedAt", "approvedAt", 500L, "method", accountInfo);
		virtualAccount = VirtualAccount.of(order, "KB", approve);
	}

	@Test
	void save() {
		given(virtualAccountRepository.save(any())).willReturn(virtualAccount);
		VirtualAccount save = virtualAccountWriter.save(virtualAccount);
		assertThat(save).isNotNull();
	}
}