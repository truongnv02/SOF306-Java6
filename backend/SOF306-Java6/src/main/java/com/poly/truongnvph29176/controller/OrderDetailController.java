package com.poly.truongnvph29176.controller;

import com.poly.truongnvph29176.dto.request.OrderDetailRequest;
import com.poly.truongnvph29176.dto.response.OrderDetailResponse;
import com.poly.truongnvph29176.entity.OrderDetail;
import com.poly.truongnvph29176.service.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/order-details")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("id") Long id) {
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
            return ResponseEntity.ok(orderDetail);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        try {
            List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
            List<OrderDetailResponse> list = orderDetails
                    .stream()
                    .map(orderDetail -> OrderDetailResponse.builder()
                            .id(orderDetail.getId())
                            .orderId(orderDetail.getOrder().getId())
                            .productId(orderDetail.getProduct().getId())
                            .price(orderDetail.getPrice())
                            .quantity(orderDetail.getQuantity())
                            .build())
                    .toList();
            return ResponseEntity.ok(list);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailRequest orderDetailRequest,
                                               BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                OrderDetail orderDetail = orderDetailService.createOrderDetail(orderDetailRequest);
                OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                        .id(orderDetail.getId())
                        .orderId(orderDetail.getOrder().getId())
                        .productId(orderDetail.getProduct().getId())
                        .price(orderDetail.getPrice())
                        .quantity(orderDetail.getQuantity())
                        .build();
                return ResponseEntity.ok(orderDetailResponse);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable("id") Long id,
                                               @Valid @RequestBody OrderDetailRequest orderDetailRequest,
                                               BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailRequest);
                OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                        .id(orderDetail.getId())
                        .orderId(orderDetail.getOrder().getId())
                        .productId(orderDetail.getProduct().getId())
                        .price(orderDetail.getPrice())
                        .quantity(orderDetail.getQuantity())
                        .build();
                return ResponseEntity.ok(orderDetailResponse);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok("Deleted Order Detail with id = " + id);
    }
}
