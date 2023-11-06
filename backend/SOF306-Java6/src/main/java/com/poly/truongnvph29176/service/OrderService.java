package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.OrderRequest;
import com.poly.truongnvph29176.dto.response.OrderResponse;
import com.poly.truongnvph29176.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<Order> findByAccountUsername(String username);
    Page<OrderResponse> getAll(Pageable pageable);
    Order createOrder(OrderRequest orderRequest) throws Exception;
    Order getOrderById(Long id) throws Exception;
    Order updateOrder(Long id, OrderRequest orderRequest) throws Exception;
    void deleteOrder(Long id);
}
