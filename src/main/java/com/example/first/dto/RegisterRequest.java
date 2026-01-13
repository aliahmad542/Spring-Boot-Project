package com.example.first.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "الاسم الأول مطلوب")
    private String firstName;

    @NotBlank(message = "الاسم الأخير مطلوب")
    private String lastName;

    @NotBlank(message = "البريد الإلكتروني مطلوب")
    @Email(message = "البريد الإلكتروني غير صالح")
    private String email;

    @NotBlank(message = "كلمة المرور مطلوبة")
    private String password;

    private String phone;
    private String role;
}