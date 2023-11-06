package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.OrderDetailRequest;
import com.poly.truongnvph29176.entity.Order;
import com.poly.truongnvph29176.entity.OrderDetail;
import com.poly.truongnvph29176.entity.Product;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.OrderDetailRepository;
import com.poly.truongnvph29176.repository.OrderRepository;
import com.poly.truongnvph29176.repository.ProductRepository;
import com.poly.truongnvph29176.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderDetail> findByOrderId(Long id) {
        return orderDetailRepository.findByOrderId(id);
    }

    @Override
    @Transactional
    public OrderDetail createOrderDetail(OrderDetailRequest orderDetailRequest) throws Exception {
        Order existingOrder = orderRepository.findById(orderDetailRequest.getOrderId()).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Order with id = " + orderDetailRequest.getOrderId())
                );
        Product existingProduct = productRepository.findById(orderDetailRequest.getProductId()).orElseThrow(() ->
                new DataNotFoundException("Cannot find Product with id = " + orderDetailRequest.getOrderId())
        );
        OrderDetail orderDetail = OrderDetail.builder()
                .order(existingOrder)
                .product(existingProduct)
                .price(orderDetailRequest.getPrice())
                .quantity(orderDetailRequest.getQuantity())
                .build();
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail getOrderDetail(Long id) throws Exception {
        return orderDetailRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find OrderDetail with id = " + id)
                );
    }

    @Override
    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetailRequest orderDetailRequest) throws Exception {
        OrderDetail existingOrderDetail = getOrderDetail(id);
        if(existingOrderDetail != null) {
            Order existingOrder = orderRepository.findById(orderDetailRequest.getOrderId()).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Order with id = " + orderDetailRequest.getOrderId())
            );
            Product existingProduct = productRepository.findById(orderDetailRequest.getProductId()).orElseThrow(() ->
                    new DataNotFoundException("Cannot find Product with id = " + orderDetailRequest.getOrderId())
            );
            existingOrderDetail.setOrder(existingOrder);
            existingOrderDetail.setProduct(existingProduct);
            existingOrderDetail.setPrice(orderDetailRequest.getPrice());
            existingOrderDetail.setQuantity(orderDetailRequest.getQuantity());
            return orderDetailRepository.save(existingOrderDetail);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
