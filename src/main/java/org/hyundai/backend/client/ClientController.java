package org.hyundai.backend.client;

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
public class ClientController {

    private final ClientService clientService;

    // Endpoint to list clients with pagination
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @GetMapping("/api/v1/clients")
    public ResponseEntity<MyResponse> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = clientService.list(page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to search clients by keyword with pagination
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER',AFFTER_SALES_MANAGER)")
    @GetMapping("/api/v1/clients/search")
    public ResponseEntity<MyResponse> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = clientService.search(keyword, page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to get a client by id
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @GetMapping("/api/v1/client/{id}")
    public ResponseEntity<MyResponse> get(@PathVariable Long id) {
        MyResponse response = clientService.get(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to create a new client
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @PostMapping("/api/v1/client")
    public ResponseEntity<MyResponse> create(@Valid @RequestBody ClientRequest request) {
        MyResponse response = clientService.create(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to update a client
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @PutMapping("/api/v1/client/{id}")
    public ResponseEntity<MyResponse> update(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        MyResponse response = clientService.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to delete a client
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @DeleteMapping("/api/v1/client/{id}")
    public ResponseEntity<MyResponse> delete(@PathVariable Long id) {
        MyResponse response = clientService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
