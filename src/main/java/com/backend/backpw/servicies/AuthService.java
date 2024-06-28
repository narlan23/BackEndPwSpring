package com.backend.backpw.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.backpw.entities.User;
import com.backend.backpw.repositories.UserRepository;

@Service
public class AuthService {
	
	 @Autowired
	 private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User authenticate(String username, String password) {
        User user = userRepository.findByName(username);
        if (user != null && passwordEncoder.matches(password, user.getPasswd2())) {
            return user;
        }
        return null;
    }

}
