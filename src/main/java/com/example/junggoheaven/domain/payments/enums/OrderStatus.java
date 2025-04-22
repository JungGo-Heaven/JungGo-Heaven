package com.example.junggoheaven.domain.payments.enums;

public enum OrderStatus {
	DONE,					// 거래 성사
	COMPLETED,				// 거래 완료
	FAILED,					// 결제 실패

	VIRTUAL_ACCOUNT_ISSUED,	// 가상 계좌 발급
	CARD_PAYMENT,			// 카드 결제 선택
	SAFE_PAYMENT,			// 안전 결제 선택
	INSTALLMENT_PAYMENT,	// 분할 결제 선택

	DEPOSIT_PAYMENT,		// 보증금 지급
	BALANCE_PAYMENT,		// 잔여금 지급

	PAYMENT_PENDING,		// 입금 대기
	PAYMENT_COMPLETED ,		// 입금 완료

	PREPARING,				// 상품 준비
	DELIVERY_READY,			// 배송 준비
	DELIVERY_COMPLETED,		// 배송 완료

	MEETING_SCHEDULED,		// 만남 약속
	MEETING_COMPLETED,		// 만남 완료

	ESCROW_HOLDING,			// 정산 대기
	SETTLEMENT_REQUESTED,	// 정산 요청
	SETTLED,				// 정산 완료

	CANCELED,				// 거래 취소
	RETURNED,				// 반품
	EXPIRED,				// 거래 시간 초과

	REFUND_REQUESTED,		// 환불 요청
	REFUND_PENDING,			// 환불 대기
	REFUND_COMPLETED,		// 환불 완료

	DISPUTE_INITIATED,		// 문제 제기
	DISPUTE_RESOLVED,		// 문제 해결
}
