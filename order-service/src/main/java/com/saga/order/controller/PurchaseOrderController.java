package com.saga.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commons.dto.OrderRequestDto;
import com.saga.order.entity.PurchaseOrder;
import com.saga.order.service.PurchaseOrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class PurchaseOrderController {
	
	private PurchaseOrderService purchaseOrderService;
	
	
	@PostMapping("/create")
	public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequestDto) {
		return purchaseOrderService.createOrder(orderRequestDto);
	}
	
	@GetMapping("/getOrder")
	public List<PurchaseOrder> getAllPurcheseOrder(){
		return this.purchaseOrderService.getAllPurchseOrder();
	}
	

}
