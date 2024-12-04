package com.ramsa.company.service;

import com.ramsa.company.dto.UserDTO;
import com.ramsa.company.entity.User;
import com.ramsa.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public UserDTO createUser (UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser  = userRepository.save(user);
        return convertToDTO(savedUser );
    }

    public UserDTO updateUser (Long id, UserDTO userDTO) {
        Optional<User> existingUser  = userRepository.findById(id);
        if (existingUser .isPresent()) {
            User user = convertToEntity(userDTO);
            user.setId(id); // Ensure the ID is set for the update
            User updatedUser  = userRepository.save(user);
            return convertToDTO(updatedUser );
        }
        return null; // Or throw an exception
    }

    public void deleteUser (Long id) {
        userRepository.deleteById(id);
    }

    public Page<UserDTO> searchUsers(String username, Pageable pageable) {
        return userRepository.findByUsernameContaining(username, pageable)
                .map(this::convertToDTO);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}