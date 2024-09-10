package org.hyundai.backend.auth;

import java.util.Map;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.hyundai.backend.exception.MyAuthException;
import org.hyundai.backend.jwt.JwtUtils;
import org.hyundai.backend.user.User;
import org.hyundai.backend.user.UserDTO;
import org.hyundai.backend.user.UserRole;
import org.hyundai.backend.user.UserService;
import org.hyundai.backend.utils.MyErrorResponse;
import org.hyundai.backend.utils.MyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    /**
     * Login service
     */

    public MyResponse login(AuthRequest request) throws MyAuthException {

        // Get the session
        try {
            // Try to authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            // Check if the user exists
            User user = userService.findByUsername(request.getUsername());
            // Build the User DTO
            UserDTO userDTO = UserDTO.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .role(user.getRole())
                    .build();

            // Create the JWT token
            String token = jwtUtils.createToken(userDTO);
            String role = userDTO.getRole().toString();
            Map<String, Object> data = Map.of("token", token, "role", role, "user",userDTO);

            // Return the BasicResponse with the JWT token
            return MyResponse.builder()
                    .data(data)
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return MyResponse.builder()
                    .message("Invalid credentials: " + e.getMessage())
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();

        }
    }

}
