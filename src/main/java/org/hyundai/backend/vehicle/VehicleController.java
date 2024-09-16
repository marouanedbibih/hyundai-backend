package org.hyundai.backend.vehicle;

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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    // Endpoint to get page of vehicles
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @GetMapping("/api/v1/vehicles")
    public ResponseEntity<MyResponse> getVehicles(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = vehicleService.getVehicles(page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to search vehicle page by keyword
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @GetMapping("/api/v1/vehicles/search")
    public ResponseEntity<MyResponse> searchVehicles(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        MyResponse response = vehicleService.searchVehicles(keyword, page, size);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to get vehicle by id
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @GetMapping("/api/v1/vehicle/{id}")
    public ResponseEntity<MyResponse> getVehicle(@PathVariable Long id) {
        MyResponse response = vehicleService.getVehicle(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to create a vehicle
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @PostMapping("/api/v1/vehicle")
    public ResponseEntity<MyResponse> createVehicle(@RequestBody VehicleRequest request) {
        MyResponse response = vehicleService.createVehicle(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to update a vehicle
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @PutMapping("/api/v1/vehicle/{id}")
    public ResponseEntity<MyResponse> updateVehicle(@PathVariable Long id, @RequestBody VehicleRequest request) {
        MyResponse response = vehicleService.updateVehicle(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    // Endpoint to delete a vehicle
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SELLER', 'AFFTER_SALES_MANAGER')")
    @DeleteMapping("/api/v1/vehicle/{id}")
    public ResponseEntity<MyResponse> deleteVehicle(@PathVariable Long id) {
        MyResponse response = vehicleService.deleteVehicle(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
