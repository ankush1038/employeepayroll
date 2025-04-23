package com.bridgelabz.employeepayroll.service;

import com.bridgelabz.employeepayroll.dto.LoginDTO;
import com.bridgelabz.employeepayroll.dto.RegisterDTO;
import com.bridgelabz.employeepayroll.dto.ResponseDTO;
import com.bridgelabz.employeepayroll.model.User;

import java.util.Optional;

public interface UserInterface {
    ResponseDTO registerUser(RegisterDTO registerDTO);
    ResponseDTO loginUser(LoginDTO loginDTO);
    boolean matchPassword(String rawPassword, String encodedPassword);
    boolean existsByEmail(String email);
    Optional<User> getUserByEmail(String email);
}
