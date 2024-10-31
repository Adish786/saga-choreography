package com.saga.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.commons.dto.OrderRequestDto;
import com.commons.event.OrderStatus;
import com.saga.order.entity.PurchaseOrder;
import com.saga.order.repository.PurchaseOrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	private PurchaseOrderRepository purchaseOrderRepository;
	private OrderStatusPublisher orderStatusPublisher;

	@Override
	@Transactional
	public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
		PurchaseOrder order = purchaseOrderRepository.save(convertDtoToEntity(orderRequestDto));
		orderRequestDto.setOrderId(order.getId());
		// produce kafka event with status Order_CREATED
		this.orderStatusPublisher.publisheOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
		return order;
	}

	@Override
	public List<PurchaseOrder> getAllPurchseOrder() {
		return this.purchaseOrderRepository.findAll();
	}
	
	private PurchaseOrder convertDtoToEntity(OrderRequestDto dto) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setProductId(dto.getProductId());
		purchaseOrder.setUserId(dto.getUserId());
		purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
		purchaseOrder.setPrice(dto.getAmount());
		return purchaseOrder;
	}


	
}
