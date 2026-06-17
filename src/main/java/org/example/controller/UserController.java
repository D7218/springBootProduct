package org.example.controller;

import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
    }

    /**
     * Test endpoint - Hello API
     */
    @GetMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> hello() {
        logger.info("Hello endpoint called");
        String msg = userService.getMessage();
        return ResponseEntity.ok(msg);
    }

    /**
     * GET all users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("GET /api/users - Fetching all users");
        List<UserDTO> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.warn("No users found");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    /**
     * GET user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("GET /api/users/{} - Fetching user", id);
        UserDTO user = userService.getUserById(id);
        if (user == null) {
            logger.warn("User with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * GET user by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        logger.info("GET /api/users/email/{} - Fetching user", email);
        UserDTO user = userService.getUserByEmail(email);
        if (user == null) {
            logger.warn("User with email {} not found", email);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    /**
     * POST to create new user
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        logger.info("POST /api/users - Creating new user");

        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            logger.error("Email is required");
            return ResponseEntity.badRequest().build();
        }

        UserDTO createdUser = userService.createUser(userDTO);
        if (createdUser == null) {
            logger.error("Failed to create user - email already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * PUT to update existing user
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        logger.info("PUT /api/users/{} - Updating user", id);
        UserDTO updatedUser = userService.updateUser(id, userDTO);

        if (updatedUser == null) {
            logger.warn("User with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedUser);
    }

    /**
     * DELETE user by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("DELETE /api/users/{} - Deleting user", id);
        boolean deleted = userService.deleteUser(id);

        if (!deleted) {
            logger.warn("User with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * GET total user count
     */
    @GetMapping("/stats/count")
    public ResponseEntity<Long> getUserCount() {
        logger.info("GET /api/users/stats/count - Fetching user count");
        long count = userService.getUserCount();
        return ResponseEntity.ok(count);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        logger.info("Health check endpoint called");
        return ResponseEntity.ok("User API is healthy");
    }
}

