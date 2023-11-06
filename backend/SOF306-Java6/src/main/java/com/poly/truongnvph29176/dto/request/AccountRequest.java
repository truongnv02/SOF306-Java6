package com.poly.truongnvph29176.dto.request;

import jakarta.validation.constraints.Email;
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
@Data
@Builder
public class AccountRequest {
    @NotBlank(message = "Username Không được để trống")
    private String username;

    @NotBlank(message = "Password Không được để trống")
    private String password;

    @NotBlank(message = "Full Name Không được để trống")
    private String fullname;

    @NotBlank(message = "Email Không được để trống")
    @Email(message = "Địa chỉ Email không hợp lệ")
    private String email;

    @NotBlank(message = "Photo Không được để trống")
    private String photo;
}
