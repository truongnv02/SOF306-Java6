package com.poly.truongnvph29176.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetailRequest {
    @NotNull(message = "Id Order Không được để trống")
    private Long orderId;

    @NotNull(message = "Id Product Không được để trống")
    private Integer productId;

    @NotNull(message = "Price Không được để trống")
    @Min(value = 0, message = "Price phải lớn hơn hoặc bằng 0")
    @Max(value = 10000000, message = "Price phải nhỏ hơn hoặc bằng 10,000,000")
    private Float price;

    @NotNull(message = "Quantity Không được để trống")
    @Min(value = 1, message = "Quantity phải lớn hơn hoặc bằng 1")
    @Max(value = 20, message = "Quantity phải nhỏ hơn hoặc bằng 20")
    private Integer quantity;
}
