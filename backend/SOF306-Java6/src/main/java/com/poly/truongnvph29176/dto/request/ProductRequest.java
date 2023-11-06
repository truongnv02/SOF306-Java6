package com.poly.truongnvph29176.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class ProductRequest {
    @NotBlank(message = "Name Không được để trống")
    private String name;

    @NotBlank(message = "Image Không được để trống")
    private String image;

    @NotNull(message = "Price Không được để trống")
    @Min(value = 0, message = "Price phải lớn hơn hoặc bằng 0")
    @Max(value = 10000000, message = "Price phải nhỏ hơn hoặc bằng 10,000,000")
    private Float price;

    @NotNull(message = "Available Không được để trống")
    private Boolean available;

    @NotBlank(message = "Id Category Không được để trống")
    private String categoryId;
}
