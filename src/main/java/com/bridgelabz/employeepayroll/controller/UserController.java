package com.bridgelabz.employeepayroll.controller;

import com.bridgelabz.employeepayroll.dto.LoginDTO;
import com.bridgelabz.employeepayroll.dto.RegisterDTO;
import com.bridgelabz.employeepayroll.dto.ResponseDTO;
import com.bridgelabz.employeepayroll.service.UserService;
import com.bridgelabz.employeepayroll.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        ResponseDTO responseDTO = userService.registerUser(registerDTO);
        return new ResponseEntity<>(responseDTO,
                responseDTO.getMessage().equals("error") ? HttpStatus.CONFLICT : HttpStatus.CREATED);
    }

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        ResponseDTO responseDTO = userService.loginUser(loginDTO);
        return new ResponseEntity<>(responseDTO,
                responseDTO.getMessage().equals("error") ? HttpStatus.UNAUTHORIZED : HttpStatus.OK);
    }
}