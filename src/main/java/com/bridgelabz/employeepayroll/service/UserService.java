package com.bridgelabz.employeepayroll.service;


import com.bridgelabz.employeepayroll.dto.*;
import com.bridgelabz.employeepayroll.model.User;
import com.bridgelabz.employeepayroll.repository.UserRepository;
import com.bridgelabz.employeepayroll.Utility.JwtUtility;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

/**
 * Service class for managing user operations like registration and login.
 */
@Slf4j
@Service
public class UserService implements  UserInterface{
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Autowired
    JwtUtility jwtUtility;


    private static final Logger log= LoggerFactory.getLogger(UserService.class);
    /**
     * Registers a new user in the Employee Payroll system.
     *
     * @param registerDTO the user registration details
     * @return a response object containing a message and registration status
     */
    @Override
    public ResponseDTO<String, String>registerUser(RegisterDTO registerDTO){
        log.info("Registering user: {}", registerDTO.getEmail());
        ResponseDTO<String, String> res= new ResponseDTO<>();
        if(existsByEmail(registerDTO.getEmail())){
            log.warn("Registration failed: User already exists with email {}", registerDTO.getEmail());
            res.setMessage("error");
            res.setData("User Already Exists");
            return res;
        }
        User user= new User();
        user.setFullName(registerDTO.getFullName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User {} registered successfully!", user.getEmail());
        emailService.sendEmail(user.getEmail(), "Registed in Employee Payroll App", "Hii...."
                +"\n You have been successfully registered");
        res.setMessage("message");
        res.setData("User Registed Successfully");
        return res;
    }

    /**
     * Authenticates a user with provided login credentials and returns a JWT token if successful.
     *
     * @param loginDTO the login credentials of the user
     * @return a response object containing a message and either a token or an error
     */
        @Override
        public  ResponseDTO<String, String> loginUser(LoginDTO loginDTO){
            log.info("Login attempt for user: {}", loginDTO.getEmail());
            ResponseDTO<String, String> res= new ResponseDTO<>();
            Optional<User> userExists= getUserByEmail(loginDTO.getEmail());

            if(userExists.isPresent()){
                User user= userExists.get();
                if(matchPassword(loginDTO.getPassword(), user.getPassword())){
                    String token= jwtUtility.generateToken(user.getEmail());
                    user.setToken(token);
                    userRepository.save(user);
                    log.debug("Login successful for user: {}- Token generated", user.getEmail());
                    emailService.sendEmail(user.getEmail(), "Logged in Employee Payroll App", "Hii"
                            +"\n You have been successfully logged in!"+token);
                    res.setMessage("message");
                    res.setData("User Logged In Successfully: "+token);
                    return res;

                }
                else{
                    log.warn("Invalid credentials for user: {}", loginDTO.getEmail());
                    res.setMessage("error");
                    res.setData("Invalid Crendentials");
                    return res;
                }

            }
            else{
                log.error("User not found with email: {}", loginDTO.getEmail());
                res.setMessage("error");
                res.setData("User Not Found");
                return res;
            }
        }

    /**
     * Compares raw and encoded password to verify login credentials.
     *
     * @param rawPassword the plain-text password provided during login
     * @param encodedPassword the encrypted password stored in the database
     * @return true if the passwords match, false otherwise
     */
        @Override
        public boolean matchPassword(String rawPassword, String encodedPassword){
            log.debug("Matching password for login Attempt");
            return passwordEncoder.matches(rawPassword, encodedPassword);
        }

    /**
     * Checks if a user exists with the given email.
     *
     * @param email the email to check
     * @return true if the user exists, false otherwise
     */
        @Override
        public boolean existsByEmail(String email){
            log.debug("Checking if user exists by email: {}", email);
            return userRepository.findByEmail(email).isPresent();
        }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user to retrieve
     * @return an Optional containing the User object if found, or empty otherwise
     */
        @Override
        public Optional<User> getUserByEmail(String email){
            log.debug("Fetching user by email: {}", email);
            return userRepository.findByEmail(email);
        }

        @Override
        public ResponseDTO<String, String> forgotPassword(ForgotPasswordDTO forgotPasswordDTO){
            User user = userRepository.findByEmail(forgotPasswordDTO.getEmail()).orElse(null);
            if (user == null){
                return new ResponseDTO("error", "User not found");
            }

            String otp = generateOTP();

            user.setOtp(otp);
            userRepository.save(user);

            String subject = "Your OTP for Password Reset";
            String body = "Your OTP is : " + otp + ". Please use this to reset your password.";
            emailService.sendEmail(user.getEmail(), subject, body);

            return new ResponseDTO("success", "OTP sent to mail.");
        }

        private  String generateOTP(){
            Random random = new Random();
            int otp = 100000 + random.nextInt(900000);
            return String.valueOf(otp);
        }

    @Override
    public ResponseDTO<String, String> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(resetPasswordDTO.getEmail());

        if (optionalUser.isEmpty()) {
            return new ResponseDTO<>("error", "User not found");
        }

        User user = optionalUser.get();

        if (!user.getOtp().equals(resetPasswordDTO.getOtp())) {
            return new ResponseDTO<>("error", "Invalid OTP");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        user.setOtp(null); // Clear OTP after reset
        userRepository.save(user);

        return new ResponseDTO<>("message", "Password reset successful");
    }


}

