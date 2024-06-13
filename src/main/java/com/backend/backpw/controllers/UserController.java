package com.backend.backpw.controllers;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.backpw.entities.User;
import com.backend.backpw.servicies.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String login, @RequestParam String passwd, @RequestParam String repasswd, @RequestParam String email) {
        return userService.registerUser(login, passwd, repasswd, email);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<User>> listarTodosUsuario(){
    	List<User> usuarios = userService.listarTodosUsuario();
        return ResponseEntity.ok(usuarios);
    }
}
