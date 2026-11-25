package com.capgemini.test.code.controller;

import com.capgemini.test.code.dto.UserRequest;
import com.capgemini.test.code.entity.User;
import com.capgemini.test.code.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Controlador Rest
@RestController

// Ruta
@RequestMapping("/rooms")

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{roomId}/users")
    public ResponseEntity<Map<String, Long>> crearUsuario(
            @PathVariable Long roomId,
            @RequestBody UserRequest request) {

        User usuarioGuardado = userService.crearUsuario(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("id", usuarioGuardado.getId()));
    }

    @GetMapping("/{roomId}/users/{userId}")

    public ResponseEntity<User> buscarUsuario (@PathVariable Long roomId, @PathVariable Long userId) {

        User user = userService.buscarUsuario(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }
}
