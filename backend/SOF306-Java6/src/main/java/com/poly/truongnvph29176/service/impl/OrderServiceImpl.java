package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.OrderRequest;
import com.poly.truongnvph29176.dto.response.OrderResponse;
import com.poly.truongnvph29176.entity.Account;
import com.poly.truongnvph29176.entity.Order;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.AccountRepository;
import com.poly.truongnvph29176.repository.OrderRepository;
import com.poly.truongnvph29176.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Order> findByAccountUsername(String username) {
        return orderRepository.findByAccountUsername(username);
    }

    @Override
    public Page<OrderResponse> getAll(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        return orderPage.map(order -> OrderResponse.builder()
                .id(order.getId())
                .username(order.getAccount().getUsername())
                .address(order.getAddress())
                .build());
    }

    @Override
    @Transactional
    public Order createOrder(OrderRequest orderRequest) throws Exception {
        Account account = accountRepository.findById(orderRequest.getUsername()).orElseThrow(() ->
                    new DataNotFoundException("Cannot find with username = " + orderRequest.getUsername())
                );
        Order order = Order.builder()
                .account(account)
                .address(orderRequest.getAddress())
                .build();
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) throws Exception {
        return orderRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find with id = " + id)
                );
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderRequest orderRequest) throws Exception {
        Order order = getOrderById(id);
        if(order != null) {
            Account account = accountRepository
                    .findById(orderRequest.getUsername())
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot find with username = " + orderRequest.getUsername())
            );
            order.setAccount(account);
            order.setAddress(orderRequest.getAddress());
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }
}
