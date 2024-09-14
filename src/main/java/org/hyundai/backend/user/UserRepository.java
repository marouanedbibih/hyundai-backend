package org.hyundai.backend.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);

    // Search a user by keyword and return a page of users
    @Query("SELECT u FROM User u WHERE u.username LIKE %?1% OR u.name LIKE %?1%")
    Page<User> search(String keyword, Pageable pageable);

}
