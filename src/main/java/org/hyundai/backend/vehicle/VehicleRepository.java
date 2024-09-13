package org.hyundai.backend.vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // Method to search vehicles by any field
    @Query("SELECT v FROM Vehicle v WHERE " +
            "v.model LIKE %:keyword% OR " +
            "v.color LIKE %:keyword%")
    Page<Vehicle> search(@Param("keyword") String keyword, Pageable pageable);

}
