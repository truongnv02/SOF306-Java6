package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.OrderDetailRequest;
import com.poly.truongnvph29176.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findByOrderId(Long id);
    OrderDetail createOrderDetail(OrderDetailRequest orderDetailRequest) throws Exception;
    OrderDetail getOrderDetail(Long id) throws Exception;
    OrderDetail updateOrderDetail(Long id, OrderDetailRequest orderDetailRequest) throws Exception;
    void deleteOrderDetail(Long id);
}
