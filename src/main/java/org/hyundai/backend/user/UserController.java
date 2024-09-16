package org.hyundai.backend.user;

import org.hyundai.backend.utils.MyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Endpoint to get list of users
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/v1/users")
    public ResponseEntity<MyResponse> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = userService.getAll(page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to search a list of users
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/v1/users/search")
    public ResponseEntity<MyResponse> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = userService.search(keyword, page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to get a user by id
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<MyResponse> getById(@PathVariable Long id) {
        MyResponse response = userService.getById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to create a user
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/api/v1/user")
    public ResponseEntity<MyResponse> create(@RequestBody @Valid UserRequest request) {
        MyResponse response = userService.create(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to update a user
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/api/v1/user/{id}")
    public ResponseEntity<MyResponse> update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        MyResponse response = userService.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to delete a user
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/api/v1/user/{id}")
    public ResponseEntity<MyResponse> delete(@PathVariable Long id) {
        MyResponse response = userService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
