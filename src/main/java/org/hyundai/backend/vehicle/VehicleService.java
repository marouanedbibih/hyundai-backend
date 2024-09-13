package org.hyundai.backend.vehicle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hyundai.backend.exception.MyNotFoundException;
import org.hyundai.backend.utils.MyErrorResponse;
import org.hyundai.backend.utils.MyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    // Service to get page of vehicles
    public MyResponse getVehicles(Integer page, Integer size) {
        // Create a pageable object
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        // Get the page of vehicles
        Page<Vehicle> vehiclesPage = vehicleRepository.findAll(pageable);
        if (vehiclesPage.getContent().isEmpty()) {
            return MyResponse.builder()
                    .message("No vehicles found")
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            List<VehcileDTO> vehicles = vehiclesPage.stream().map(VehcileDTO::toDTO).collect(Collectors.toList());
            Map<String, Object> meta = Map.of(
                    "currentPage", vehiclesPage.getNumber() + 1,
                    "totalItems", vehiclesPage.getTotalElements(),
                    "totalPages", vehiclesPage.getTotalPages(),
                    "size", size);
            return MyResponse.builder()
                    .data(vehicles)
                    .meta(meta)
                    .status(HttpStatus.OK)
                    .build();
        }
    }

    // Service to search vehicle page by any field
    public MyResponse searchVehicles(String keyword, Integer page, Integer size) {
        // Create a pageable object
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
        // Get the page of vehicles
        Page<Vehicle> vehiclesPage = vehicleRepository.search(keyword, pageable);
        if (vehiclesPage.getContent().isEmpty()) {
            return MyResponse.builder()
                    .message("No vehicles found")
                    .status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            List<VehcileDTO> vehicles = vehiclesPage.stream().map(VehcileDTO::toDTO).collect(Collectors.toList());
            Map<String, Object> meta = Map.of(
                    "currentPage", vehiclesPage.getNumber() + 1,
                    "totalItems", vehiclesPage.getTotalElements(),
                    "totalPages", vehiclesPage.getTotalPages(),
                    "size", size);
            return MyResponse.builder()
                    .data(vehicles)
                    .meta(meta)
                    .status(HttpStatus.OK)
                    .build();
        }
    }

    // Service to get a vehicle by
    public MyResponse getVehicle(Long id) throws MyNotFoundException {
        Vehicle vehicle = this.findById(id);
        return MyResponse.builder()
                .data(VehcileDTO.toDTO(vehicle))
                .status(HttpStatus.OK)
                .build();
    }

    // Service to create a vehicle
    public MyResponse createVehicle(VehicleRequest request) {
        Vehicle vehicle = Vehicle.builder()
                .model(request.getModel())
                .year(request.getYear())
                .price(request.getPrice())
                .color(request.getColor())
                .status(request.getStatus())
                .isPromotion(request.getIsPromotion())
                .discount(request.getDiscount())
                .build();
        vehicleRepository.save(vehicle);
        return MyResponse.builder()
                .status(HttpStatus.CREATED)
                .message("Vehicle created successfully")
                .build();
    }

    // Service to update a vehicle
    public MyResponse updateVehicle(Long id, VehicleRequest request) throws MyNotFoundException {
        // Find the vehicle by id
        Vehicle vehicle = this.findById(id);
        // Update the vehicle informations
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setPrice(request.getPrice());
        vehicle.setColor(request.getColor());
        vehicle.setStatus(request.getStatus());
        vehicle.setIsPromotion(request.getIsPromotion());
        vehicle.setDiscount(request.getDiscount());
        // Save the updated vehicle
        vehicleRepository.save(vehicle);
        // Return response
        return MyResponse.builder()
                .status(HttpStatus.OK)
                .message("Vehicle updated successfully")
                .build();
    }

    // Service to delete a vehicle
    public MyResponse deleteVehicle(Long id) throws MyNotFoundException {
        // Find the vehicle by id
        Vehicle vehicle = this.findById(id);
        // Delete the vehicle
        vehicleRepository.delete(vehicle);
        // Return response
        return MyResponse.builder()
                .status(HttpStatus.OK)
                .message("Vehicle deleted successfully")
                .build();
    }

    // Utils to find vehicle by id
    public Vehicle findById(Long id) throws MyNotFoundException {
        return vehicleRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException(MyErrorResponse.builder()
                        .message("Vehicle with id " + id + " not found")
                        .build()));
    }

}
