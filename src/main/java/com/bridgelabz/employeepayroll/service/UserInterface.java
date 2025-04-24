package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.*;
import com.bridgelabz.employeepayroll.model.User;

import java.util.Optional;

public interface UserInterface {
    ResponseDTO registerUser(RegisterDTO registerDTO);
    ResponseDTO loginUser(LoginDTO loginDTO);
    boolean matchPassword(String rawPassword, String encodedPassword);
    boolean existsByEmail(String email);
    Optional<User> getUserByEmail(String email);
    ResponseDTO forgotPassword(ForgotPasswordDTO forgotPasswordDTO);

    ResponseDTO<String, String> resetPassword(ResetPasswordDTO resetPasswordDTO);
}
