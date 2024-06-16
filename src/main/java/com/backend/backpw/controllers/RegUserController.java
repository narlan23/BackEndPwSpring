package com.backend.backpw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backpw.dto.UserDTO;
import com.backend.backpw.reponse.ErrorResponse;
import com.backend.backpw.servicies.UserService;

@RestController
@RequestMapping("/api")
public class RegUserController {
	
	@Autowired
    private UserService userService;

	@PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        String response = userService.registerUser(userDTO);
        if (response.startsWith("Erro")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(response));
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
