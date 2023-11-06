package com.poly.truongnvph29176.controller;

import com.poly.truongnvph29176.dto.request.OrderRequest;
import com.poly.truongnvph29176.dto.response.OrderResponse;
import com.poly.truongnvph29176.entity.Order;
import com.poly.truongnvph29176.entity.Product;
import com.poly.truongnvph29176.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("")
    public ResponseEntity<?> getAllOrders(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "12") Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
        Page<OrderResponse> orderPages = orderService.getAll(pageable);
        return ResponseEntity.ok(orderPages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrders(@PathVariable("id") Long id) {
        try {
            Order existingOrders = orderService.getOrderById(id);
            return ResponseEntity.ok(existingOrders);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/account/{username}")
    public ResponseEntity<?> getOrdersUsername(@PathVariable("username") String username) {
        try {
            List<Order> orders = orderService.findByAccountUsername(username);
            return ResponseEntity.ok(orders);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest,
                                         BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Order order = orderService.createOrder(orderRequest);
                return ResponseEntity.ok(order);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") Long id,
                                         @Valid @RequestBody OrderRequest orderRequest,
                                         BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Order order = orderService.updateOrder(id, orderRequest);
                return ResponseEntity.ok(order);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
