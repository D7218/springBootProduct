package org.example.service;

import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get message for API test endpoint
     */
    public String getMessage() {
        return "Hello from AppService";
    }

    /**
     * Get all users
     */
    public List<UserDTO> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get user by ID
     */
    public UserDTO getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    /**
     * Get user by email
     */
    public UserDTO getUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        return userRepository.findByEmail(email)
                .map(this::convertToDTO)
                .orElse(null);
    }

    /**
     * Create new user
     */
    public UserDTO createUser(UserDTO userDTO) {
        logger.info("Creating new user with email: {}", userDTO.getEmail());

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            logger.warn("User with email {} already exists", userDTO.getEmail());
            return null;
        }

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setActive(userDTO.getActive() != null ? userDTO.getActive() : true);

        User savedUser = userRepository.save(user);
        logger.info("User created successfully with ID: {}", savedUser.getId());

        return convertToDTO(savedUser);
    }

    /**
     * Update existing user
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        logger.info("Updating user with ID: {}", id);

        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDTO.getName());
                    user.setPhone(userDTO.getPhone());
                    user.setAddress(userDTO.getAddress());
                    if (userDTO.getActive() != null) {
                        user.setActive(userDTO.getActive());
                    }
                    User updatedUser = userRepository.save(user);
                    logger.info("User updated successfully");
                    return convertToDTO(updatedUser);
                })
                .orElse(null);
    }

    /**
     * Delete user by ID
     */
    public boolean deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.info("User deleted successfully");
            return true;
        }

        logger.warn("User with ID {} not found", id);
        return false;
    }

    /**
     * Get total user count
     */
    public long getUserCount() {
        logger.info("Fetching total user count");
        return userRepository.count();
    }

    /**
     * Convert User entity to UserDTO
     */
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getAddress(),
                user.getActive()
        );
    }
}

