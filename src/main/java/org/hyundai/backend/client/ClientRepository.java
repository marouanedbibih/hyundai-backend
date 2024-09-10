package org.hyundai.backend.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // Search by first name, last name, email, phone, or address using a query
    @Query("SELECT c FROM Client c WHERE "
            + "c.firstName LIKE %:keyword% OR "
            + "c.lastName LIKE %:keyword% OR "
            + "c.email LIKE %:keyword% OR "
            + "c.phone LIKE %:keyword% OR "
            + "c.address LIKE %:keyword%")
    Page<Client> search(@Param("keyword") String keyword, Pageable pageable);

}
