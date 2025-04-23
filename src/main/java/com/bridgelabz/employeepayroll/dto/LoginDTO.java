package com.bridgelabz.employeepayroll.dto;

import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginDTO{
    @NotBlank(message = "Email cannot be Empty")
    @Email(message= "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min=6, message ="Password must be at least 6 characters long" )
    private String password;
}
