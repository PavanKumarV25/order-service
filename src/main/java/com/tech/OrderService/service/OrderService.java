package com.tech.OrderService.service;

import com.tech.OrderService.model.OrderRequest;
import com.tech.OrderService.model.OrderResponse;

public interface OrderService {

    long placeOrder(OrderRequest orderRequest);

    OrderResponse getOrderDetails(long orderId);
}
