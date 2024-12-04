package com.ramsa.company.controller;

import com.ramsa.company.dto.UserDTO;
import com.ramsa.company.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser (@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser  = userService.createUser (userDTO);
        return ResponseEntity.ok(createdUser );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser (@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser  = userService.updateUser (id, userDTO);
        return updatedUser  != null ? ResponseEntity.ok(updatedUser ) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id) {
        userService.deleteUser (id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUsers(@RequestParam String username, Pageable pageable) {
        Page<UserDTO> users = userService.searchUsers(username, pageable);
        return ResponseEntity.ok(users);
    }
}