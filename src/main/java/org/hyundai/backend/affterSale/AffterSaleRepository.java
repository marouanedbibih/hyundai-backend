package org.hyundai.backend.affterSale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AffterSaleRepository extends JpaRepository<AffterSale, Long> {

    // Method to search affter sales by keyword (client first name, client last name,
    // vehicle model, vehicle color,user username)
    @Query("SELECT a FROM AffterSale a WHERE a.client.firstName LIKE %:keyword% OR a.client.lastName LIKE %:keyword% OR a.vehicle.model LIKE %:keyword% OR a.vehicle.color LIKE %:keyword% OR a.user.username LIKE %:keyword% or a.description LIKE %:keyword%")
    Page<AffterSale> search(@Param("keyword") String keyword, Pageable pageable);

}
