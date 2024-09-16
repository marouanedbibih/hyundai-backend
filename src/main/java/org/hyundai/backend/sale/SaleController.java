package org.hyundai.backend.sale;

import org.hyundai.backend.utils.MyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    // Endpoint to get all sales with pagination
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    @GetMapping("/api/v1/sales")
    public ResponseEntity<MyResponse> getAll(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = saleService.getAll(page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to search sales by keyword
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    @GetMapping("/api/v1/sales/search")
    public ResponseEntity<MyResponse> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = saleService.search(keyword, page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to get a sale by id
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    @GetMapping("/api/v1/sale/{id}")
    public ResponseEntity<MyResponse> getById(@PathVariable Long id) {
        MyResponse response = saleService.getById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to create a sale
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    @PostMapping("/api/v1/sale")
    public ResponseEntity<MyResponse> create(
            @RequestBody SaleRequest request,
            @RequestHeader("Authorization") String bearerToken) {
        MyResponse response = saleService.create(request, bearerToken);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to update a sale
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    @PutMapping("/api/v1/sale/{id}")
    public ResponseEntity<MyResponse> update(
            @PathVariable Long id,
            @RequestBody UpdateSaleRequest request) {
        MyResponse response = saleService.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to delete a sale
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER')")
    @DeleteMapping("/api/v1/sale/{id}")
    public ResponseEntity<MyResponse> delete(@PathVariable Long id) {
        MyResponse response = saleService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
