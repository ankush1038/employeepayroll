package com.bridgelabz.employeepayroll.controller;

import com.bridgelabz.employeepayroll.dto.*;
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

    /**
     * Endpoint for registering a new user.
     *
     * @param registerDTO The registration details provided by the user.
     * @return ResponseEntity with success or error message.
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody RegisterDTO registerDTO) {
        ResponseDTO responseDTO = userService.registerUser(registerDTO);
        return new ResponseEntity<>(responseDTO,
                responseDTO.getMessage().equals("error") ? HttpStatus.CONFLICT : HttpStatus.CREATED);
    }

    /**
     * Endpoint for logging in an existing user.
     *
     * @param loginDTO The login credentials provided by the user.
     * @return ResponseEntity with authentication token or error message.
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        ResponseDTO responseDTO = userService.loginUser(loginDTO);
        return new ResponseEntity<>(responseDTO,
                responseDTO.getMessage().equals("error") ? HttpStatus.UNAUTHORIZED : HttpStatus.OK);
    }

    @PostMapping("/forgot")
    public  ResponseEntity<ResponseDTO> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO){
        ResponseDTO responseDTO = userService.forgotPassword(forgotPasswordDTO);
        return new ResponseEntity(responseDTO,
                responseDTO.getMessage().equals("error") ? HttpStatus.UNAUTHORIZED : HttpStatus.OK);
    }

    @PostMapping("/reset")
    public ResponseEntity<ResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        ResponseDTO responseDTO = userService.resetPassword(resetPasswordDTO);
        return new ResponseEntity<>(responseDTO,
                responseDTO.getMessage().equals("error") ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
    }
}