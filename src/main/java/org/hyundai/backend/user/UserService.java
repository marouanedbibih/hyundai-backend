package org.hyundai.backend.user;

import java.util.List;
import java.util.Map;

import org.hyundai.backend.exception.MyAlreadyExistException;
import org.hyundai.backend.exception.MyNotFoundException;
import org.hyundai.backend.utils.MyError;
import org.hyundai.backend.utils.MyErrorResponse;
import org.hyundai.backend.utils.MyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Service to get a user by id
    public MyResponse getById(Long id) {
        // Find the user by id
        User user = this.findById(id);
        // Return the response
        return MyResponse.builder()
                .data(UserDTO.toDTO(user))
                .status(HttpStatus.OK)
                .build();
    }

    // Service to search a users page by keyword
    public MyResponse search(String keyword, Integer page, Integer size) {
        // Create a pageable object
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        // Get the page of users
        Page<User> usersPage = userRepository.search(keyword, pageable);
        // Build the user DTOs
        List<UserDTO> userDTOs = usersPage.getContent().stream().map(UserDTO::toDTO).toList();
        // Build the meta data
        Map<String, Object> meta = Map.of(
                "currentPage", page,
                "totalPages", usersPage.getTotalPages(),
                "totalItems", usersPage.getTotalElements(),
                "size", size);
        // Return the response
        return MyResponse.builder()
                .data(userDTOs)
                .meta(meta)
                .status(HttpStatus.OK)
                .build();
    }

    // Service to get page of users
    public MyResponse getAll(Integer page, Integer size) {
        // Create a pageable object
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        // Get the page of users
        Page<User> usersPage = userRepository.findAll(pageable);
        // Build the user DTOs
        List<UserDTO> userDTOs = usersPage.getContent().stream().map(UserDTO::toDTO).toList();
        // Build the meta data
        Map<String, Object> meta = Map.of(
                "currentPage", page,
                "totalPages", usersPage.getTotalPages(),
                "totalItems", usersPage.getTotalElements(),
                "size", size);
        // Return the response
        return MyResponse.builder()
                .data(userDTOs)
                .meta(meta)
                .status(HttpStatus.OK)
                .build();

    }

    // Service to create a user
    public MyResponse create(UserRequest request) {
        // Check if username already exists
        this.checkUsername(request.getUsername());
        // Create a user object
        User user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.valueOf(request.getRole()))
                .build();
        // Save the user
        userRepository.save(user);
        // Return the response
        return MyResponse.builder()
                .data(UserDTO.toDTO(user))
                .message("User created successfully")
                .status(HttpStatus.CREATED)
                .build();
    }

    // Service to update a user
    public MyResponse update(Long id, UpdateUserRequest request) {
        // Find the user by id
        User user = this.findById(id);
        // Check if username already exists
        if (!user.getUsername().equals(request.getUsername())) {
            this.checkUsername(request.getUsername());
        }
        // If password is provided, encode it
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        // Update the user object
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setRole(UserRole.valueOf(request.getRole()));
        // Save the user
        userRepository.save(user);
        // Return the response
        return MyResponse.builder()
                .data(UserDTO.toDTO(user))
                .message("User updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    // Service to delete a user
    public MyResponse delete(Long id) {
        // Find the user by id
        User user = this.findById(id);
        // Delete the user
        userRepository.delete(user);
        // Return the response
        return MyResponse.builder()
                .message("User deleted successfully")
                .status(HttpStatus.OK)
                .build();
    }

    // Util method to find user by username
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new MyNotFoundException(MyErrorResponse.builder()
                        .message("User not found with username: " + username)
                        .build()));
    }

    // Utils to find user by id
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException(MyErrorResponse.builder()
                        .message("User not found with id: " + id)
                        .build()));
    }

    // Utils to check if username already exists
    public void checkUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new MyAlreadyExistException(MyErrorResponse.builder()
                    .errors(List.of(MyError.builder().key("username").message("Username already exists").build()))
                    .build());
        }
    }

}
