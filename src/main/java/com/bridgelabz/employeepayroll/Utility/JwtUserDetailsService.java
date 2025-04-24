package com.bridgelabz.employeepayroll.Utility;

import com.bridgelabz.employeepayroll.model.User;
import com.bridgelabz.employeepayroll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * Service class to load user-specific data for Spring Security authentication.
 * Implements {@link UserDetailsService} to fetch user details from the database.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    /**
     * Loads the user from the database using the provided email.
     *
     * @param email the email of the user trying to authenticate
     * @return the UserDetails object containing user information
     * @throws UsernameNotFoundException if the user with the given email is not found
     */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return user;
    }
}
