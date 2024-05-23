package com.inadvance.prueba.controller;

import com.inadvance.prueba.dto.UserDto;
import com.inadvance.prueba.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Usuarios", description = "Endpoints para la gestion de usuarios")
@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Obtener Usuario por Correo Electrónico", description = "Obtener Usuario por Correo Electrónico")
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam(name = "email") String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @Operation(summary = "Obtener Todos los Usuarios", description = "Obtener Todos los Usuarios")
    @GetMapping("/user/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Crear Usuario", description = "Crear Usuario")
    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        UserDto createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "Actualizar Usuario", description = "Actualizar Usuario")
    @PutMapping("/user")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        UserDto updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Eliminar Usuario", description = "Eliminar Usuario")
    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@RequestParam(name = "email") String email) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

}
