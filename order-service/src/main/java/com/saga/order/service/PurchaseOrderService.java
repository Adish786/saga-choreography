package com.saga.order.service;

import java.util.List;

import com.commons.dto.OrderRequestDto;
import com.saga.order.entity.PurchaseOrder;


public interface PurchaseOrderService {
		
	PurchaseOrder createOrder(OrderRequestDto orderRequestDto);
	List<PurchaseOrder> getAllPurchseOrder();

}
