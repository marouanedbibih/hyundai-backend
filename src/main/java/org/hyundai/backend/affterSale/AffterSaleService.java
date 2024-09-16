package org.hyundai.backend.affterSale;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hyundai.backend.client.Client;
import org.hyundai.backend.client.ClientService;
import org.hyundai.backend.jwt.JwtUtils;
import org.hyundai.backend.user.User;
import org.hyundai.backend.utils.MyResponse;
import org.hyundai.backend.vehicle.Vehicle;
import org.hyundai.backend.vehicle.VehicleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AffterSaleService {

    private final AffterSaleRepository serviceRepository;
    private final ClientService clientService;
    private final VehicleService vehicleService;
    private final JwtUtils jwtUtils;

    // Service to get page of affter sales
    public MyResponse getList(Integer page, Integer size) {
        // Create the pageable object
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        // Get the page of affter sales
        Page<AffterSale> affterSalesPage = serviceRepository.findAll(pageable);
        if (affterSalesPage.getContent().isEmpty()) {
            return MyResponse.builder()
                    .message("No affter sales found")
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            // Build list of DTOs
            List<AffterSaleDTO> affterSaleDTOs = affterSalesPage.getContent()
                    .stream()
                    .map(AffterSaleDTO::toDTO)
                    .collect(Collectors.toList());
            // Build the meta object
            Map<String, Object> meta = Map.of(
                    "currentPage", affterSalesPage.getNumber() + 1,
                    "totalItems", affterSalesPage.getTotalElements(),
                    "totalPages", affterSalesPage.getTotalPages(),
                    "size", size);
            // Return response
            return MyResponse.builder()
                    .data(affterSaleDTOs)
                    .meta(meta)
                    .status(HttpStatus.OK)
                    .build();
        }
    }

    // Service to search affter sales by keyword
    public MyResponse search(String keyword, Integer page, Integer size) {
        // Create the pageable object
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        // Get the page of affter sales
        Page<AffterSale> affterSalesPage = serviceRepository.search(keyword, pageable);
        if (affterSalesPage.getContent().isEmpty()) {
            return MyResponse.builder()
                    .message("No affter sales found")
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            // Build list of DTOs
            List<AffterSaleDTO> affterSaleDTOs = affterSalesPage.getContent()
                    .stream()
                    .map(AffterSaleDTO::toDTO)
                    .collect(Collectors.toList());
            // Build the meta object
            Map<String, Object> meta = Map.of(
                    "currentPage", affterSalesPage.getNumber() + 1,
                    "totalItems", affterSalesPage.getTotalElements(),
                    "totalPages", affterSalesPage.getTotalPages(),
                    "size", size);
            // Return response
            return MyResponse.builder()
                    .data(affterSaleDTOs)
                    .meta(meta)
                    .status(HttpStatus.OK)
                    .build();
        }
    }

    // Service to get an affter sale by ID
    public MyResponse getAffterSaleById(Long id) {
        // Find the affter sale
        AffterSale affterSale = this.findById(id);
        // Return response
        return MyResponse.builder()
                .data(AffterSaleDTO.toDTO(affterSale))
                .status(HttpStatus.OK)
                .build();
    }

    // Service to create new service
    public MyResponse create(CreateAffterSaleRequest request, String bearerToken) {
        // Find the client
        Client client = clientService.findById(request.getClientId());
        // Find the vehicle
        Vehicle vehicle = vehicleService.findById(request.getVehicleId());
        // Find the user
        User user = jwtUtils.extractUserFromBeaererToken(bearerToken);
        // Create the affter sale
        AffterSale affterSale = AffterSale.builder()
                .description(request.getDescription())
                .cost(request.getCost())
                .appointment(request.getAppointment())
                .status(AffterSaleStatus.valueOf(request.getStatus()))
                .type(AffterSaleType.valueOf(request.getType()))
                .client(client)
                .vehicle(vehicle)
                .user(user)
                .build();
        // Save the affter sale
        serviceRepository.save(affterSale);
        // Return response
        return MyResponse.builder()
                .message("Affter sale created successfully")
                .status(HttpStatus.CREATED)
                .build();
    }

    // Service to update an affter sale
    public MyResponse update(UpdateAffterSaleRequest request, Long id) {
        // Find the affter sale
        AffterSale affterSale = this.findById(id);
        // Update the affter sale
        affterSale.setDescription(request.getDescription());
        affterSale.setCost(request.getCost());
        affterSale.setAppointment(request.getAppointment());
        affterSale.setStatus(AffterSaleStatus.valueOf(request.getStatus()));
        affterSale.setType(AffterSaleType.valueOf(request.getType()));
        // Save the affter sale
        serviceRepository.save(affterSale);
        // Return response
        return MyResponse.builder()
                .message("Affter sale updated successfully")
                .status(HttpStatus.OK)
                .build();
    }

    // Service to delete an affter sale
    public MyResponse delete(Long id) {
        // Find the affter sale
        AffterSale affterSale = this.findById(id);
        // Delete the affter sale
        serviceRepository.delete(affterSale);
        // Return response
        return MyResponse.builder()
                .message("Affter sale deleted successfully")
                .status(HttpStatus.OK)
                .build();
    }

    // Util to find an affter sale by ID
    public AffterSale findById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Affter sale not found with ID: " + id));
    }

}
