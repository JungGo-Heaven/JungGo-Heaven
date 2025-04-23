package com.example.junggoheaven.domain.payments.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.payments.enums.OrderStatus;
import com.example.junggoheaven.domain.payments.enums.PaymentMethod;
import com.example.junggoheaven.domain.payments.repository.OrderRepository;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class OrderWriterTest {

	@InjectMocks
	private OrderWriter orderWriter;
	@Mock
	private OrderRepository orderRepository;

	Order order;

	@BeforeEach
	void setUp() {
		User buyer = User.of("buyer@email.com", "buyer", "123");
		User seller = User.of("buyer@email.com", "buyer", "123");
		Product product = new Product(seller, "상품", "판매 상품", 500L);
		order = Order.of(product, buyer, seller, "상품 이름", 300L, PaymentMethod.VIRTUAL_ACCOUNT);

		ReflectionTestUtils.setField(order, "id", 1L);
		ReflectionTestUtils.setField(order, "status", OrderStatus.DONE);
	}

	@Test
	void saveOrder() {
		given(orderRepository.save(any())).willReturn(order);
		Order save = orderWriter.saveOrder(order);
		assertThat(save).isNotNull();
		assertThat(save.getId()).isEqualTo(order.getId());
	}

	@Test
	void cancelOrder() {
		orderWriter.cancelOrder(order);
		assertThat(order).isNotNull();
	}
}