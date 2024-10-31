package com.commons.event;

import java.util.Date;
import java.util.UUID;

import com.commons.dto.OrderRequestDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderEvent implements Event {
	
	private UUID eventId = UUID.randomUUID();
	private Date eventDate = new Date();
	private OrderRequestDto orderRequestDto;
	private OrderStatus orderStatus;
	@Override
	public UUID getEventId() {
		return eventId;
	}
	@Override
	public Date getDate() {
		return eventDate;
	}
	
	public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
		super();
		this.orderRequestDto = orderRequestDto;
		this.orderStatus = orderStatus;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public OrderRequestDto getOrderRequestDto() {
		return orderRequestDto;
	}
	public void setOrderRequestDto(OrderRequestDto orderRequestDto) {
		this.orderRequestDto = orderRequestDto;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setEventId(UUID eventId) {
		this.eventId = eventId;
	}
	
	
	
	
}
