package com.backend.backpw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.backend.backpw.dto.GameUserDTO;
import com.backend.backpw.servicies.GameUserService;


@RestController
@RequestMapping("/api")
public class UserController {
    
	@Autowired
    private GameUserService userService;
	
	@GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        GameUserDTO userDTO = userService.findByUsername(username);
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(userDTO);
    }
  
}
