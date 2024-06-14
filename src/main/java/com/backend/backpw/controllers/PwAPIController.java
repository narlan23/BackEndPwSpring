package com.backend.backpw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.backpw.servicies.PwAPIService;

import reactor.core.publisher.Mono;

@RestController
public class PwAPIController {

    private final PwAPIService pwAPIService;

    @Autowired
    public PwAPIController(PwAPIService pwAPIService) {
        this.pwAPIService = pwAPIService;
    }

    @GetMapping("/consultar-dados")
    public Mono<String> consultarDados(@RequestParam String userId, @RequestParam String function) {
        return pwAPIService.consultarDados(userId, function);
    }
    
    
}

