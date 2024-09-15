package org.hyundai.backend.user;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String name;

    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private UserRole role;

    // Equals Method
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserDTO))
            return false;

        UserDTO userDTO = (UserDTO) o;

        if (!getId().equals(userDTO.getId()))
            return false;
        if (!getUsername().equals(userDTO.getUsername()))
            return false;
        if (!getName().equals(userDTO.getName()))
            return false;
        if (!getPassword().equals(userDTO.getPassword()))
            return false;
        if (!getCreatedAt().equals(userDTO.getCreatedAt()))
            return false;
        if (!getUpdatedAt().equals(userDTO.getUpdatedAt()))
            return false;
        return getRole() == userDTO.getRole();
    }

    // HashCode Method
    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getCreatedAt().hashCode();
        result = 31 * result + getUpdatedAt().hashCode();
        result = 31 * result + getRole().hashCode();
        return result;
    }


    // ToString Method
    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", role=" + role +
                '}';
    }

    // ToDTO Method
    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .role(user.getRole())
                .build();
    }


}
