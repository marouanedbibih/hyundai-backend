package org.hyundai.backend.affterSale;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AffterSaleController {

    private final AffterSaleService affterSaleService;

    // Endpoint to get page of affter sales
    @PreAuthorize("hasAnyAuthority('ADMIN','AFFTER_SALES_MANAGER')")
    @GetMapping("/api/v1/affter-sales")
    public ResponseEntity<MyResponse> getList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = affterSaleService.getList(page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to search affter sales by keyword
    @PreAuthorize("hasAnyAuthority('ADMIN','AFFTER_SALES_MANAGER')")
    @GetMapping("/api/v1/affter-sales/search")
    public ResponseEntity<MyResponse> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = affterSaleService.search(keyword, page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to get affter sale by id
    @PreAuthorize("hasAnyAuthority('ADMIN','AFFTER_SALES_MANAGER')")
    @GetMapping("/api/v1/affter-sale/{id}")
    public ResponseEntity<MyResponse> getAffterSale(@PathVariable Long id) {
        MyResponse response = affterSaleService.getAffterSaleById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to create affter sale
    @PreAuthorize("hasAnyAuthority('ADMIN','AFFTER_SALES_MANAGER')")
    @PostMapping("/api/v1/affter-sale")
    public ResponseEntity<MyResponse> createAffterSale(
            @RequestBody @Valid CreateAffterSaleRequest request,
            @RequestHeader("Authorization") String bearerToken) {
        MyResponse response = affterSaleService.create(request, bearerToken);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to update affter sale
    @PreAuthorize("hasAnyAuthority('ADMIN','AFFTER_SALES_MANAGER')")
    @PutMapping("/api/v1/affter-sale/{id}")
    public ResponseEntity<MyResponse> updateAffterSale(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAffterSaleRequest request) {
        MyResponse response = affterSaleService.update(request, id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to delete affter sale
    @PreAuthorize("hasAnyAuthority('ADMIN','AFFTER_SALES_MANAGER')")
    @DeleteMapping("/api/v1/affter-sale/{id}")
    public ResponseEntity<MyResponse> deleteAffterSale(@PathVariable Long id) {
        MyResponse response = affterSaleService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
