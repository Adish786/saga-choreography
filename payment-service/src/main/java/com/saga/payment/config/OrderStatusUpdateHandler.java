package com.saga.payment.config;

import java.util.function.Consumer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.commons.dto.OrderRequestDto;
import com.commons.event.OrderStatus;
import com.commons.event.PaymentStatus;
import com.saga.order.entity.PurchaseOrder;
import com.saga.order.repository.PurchaseOrderRepository;
import com.saga.order.service.OrderStatusPublisher;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OrderStatusUpdateHandler {

	private PurchaseOrderRepository purchaseOrderRepository;

	private OrderStatusPublisher orderStatusPublisher;

	@Transactional
	public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
		this.purchaseOrderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
	}

	private void updateOrder(PurchaseOrder purchaseOrder) {

		boolean isPaymentCompleted = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
		OrderStatus orderStatus = isPaymentCompleted ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
		purchaseOrder.setOrderStatus(orderStatus);
		if (!isPaymentCompleted) {
			this.orderStatusPublisher.publisheOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
		}
	}

	public OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
		OrderRequestDto orderRequestDto = new OrderRequestDto();
		orderRequestDto.setAmount(purchaseOrder.getPrice());
		orderRequestDto.setOrderId(purchaseOrder.getId());
		orderRequestDto.setProductId(purchaseOrder.getProductId());
		orderRequestDto.setUserId(purchaseOrder.getUserId());
		return orderRequestDto;
	}
}
