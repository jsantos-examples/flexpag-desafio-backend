package com.flexpag.paymentscheduler.controllers;

import com.flexpag.paymentscheduler.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createTest")
    @ApiOperation(value = "Cria um cenário de teste, com usuário e suas faturas.")
    public ResponseEntity<Object> createTest() {
        return userService.createTest();
    }
}
