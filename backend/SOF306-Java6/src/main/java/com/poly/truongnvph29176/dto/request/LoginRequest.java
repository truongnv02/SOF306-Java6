package com.poly.truongnvph29176.dto.request;

import jakarta.validation.constraints.NotBlank;
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
@Builder
@Data
public class LoginRequest {
    @NotBlank(message = "Gmail không được để trống")
    private String gmail;

    @NotBlank(message = "Password không được để trống")
    private String password;
}
