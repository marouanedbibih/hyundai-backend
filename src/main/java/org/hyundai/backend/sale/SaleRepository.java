package org.hyundai.backend.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Method to search sales by keyword (client first name, client last name,
    // vehicle model, vehicle color,user username)
    @Query("SELECT s FROM Sale s WHERE s.client.firstName LIKE %:keyword% OR s.client.lastName LIKE %:keyword% OR s.vehicle.model LIKE %:keyword% OR s.vehicle.color LIKE %:keyword% OR s.user.username LIKE %:keyword%")
    Page<Sale> search(@Param("keyword") String keyword, Pageable pageable);

}
