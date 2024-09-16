package org.hyundai.backend.sale;

import org.hyundai.backend.client.Client;
import org.hyundai.backend.client.ClientService;
import org.hyundai.backend.exception.MyException;
import org.hyundai.backend.jwt.JwtUtils;
import org.hyundai.backend.user.User;
import org.hyundai.backend.utils.MyErrorResponse;
import org.hyundai.backend.utils.MyResponse;
import org.hyundai.backend.vehicle.Vehicle;
import org.hyundai.backend.vehicle.VehicleRepository;
import org.hyundai.backend.vehicle.VehicleService;
import org.hyundai.backend.vehicle.VehicleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SaleService {

        private final SaleRepository saleRepository;
        private final ClientService clientService;
        private final VehicleService vehicleService;
        private final JwtUtils jwtUtils;
        private final VehicleRepository vehicleRepository;

        // Service to get all sales with pagination
        public MyResponse getAll(Integer page, Integer size) {
                // Create a page request
                Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
                // Get Page of sales
                Page<Sale> salesPage = saleRepository.findAll(pageable);
                // Return the list of sales DTOs
                if (salesPage.getContent().isEmpty()) {
                        return MyResponse.builder()
                                        .status(HttpStatus.NO_CONTENT)
                                        .message("No sales found")
                                        .build();
                } else {
                        // Build the sale DTOs
                        List<SaleDTO> saleDTOs = salesPage.getContent().stream().map(SaleDTO::toDTO).toList();
                        // Build meta data object
                        Map<String, Object> meta = Map.of(
                                        "currentPage", page,
                                        "totalPages", salesPage.getTotalPages(),
                                        "totalItems", salesPage.getTotalElements(),
                                        "size", size);
                        return MyResponse.builder()
                                        .data(saleDTOs)
                                        .meta(meta)
                                        .status(HttpStatus.OK)
                                        .build();

                }
        }

        // Service to search sales by keyword
        public MyResponse search(String keyword, Integer page, Integer size) {
                // Create a page request
                Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").descending());
                // Get Page of sales
                Page<Sale> salesPage = saleRepository.search(keyword, pageable);
                // Return the list of sales DTOs
                if (salesPage.getContent().isEmpty()) {
                        return MyResponse.builder()
                                        .status(HttpStatus.NO_CONTENT)
                                        .message("No sales found")
                                        .build();
                } else {
                        // Build the sale DTOs
                        List<SaleDTO> saleDTOs = salesPage.getContent().stream().map(SaleDTO::toDTO).toList();
                        // Build meta data object
                        Map<String, Object> meta = Map.of(
                                        "currentPage", page,
                                        "totalPages", salesPage.getTotalPages(),
                                        "totalItems", salesPage.getTotalElements(),
                                        "size", size);
                        return MyResponse.builder()
                                        .data(saleDTOs)
                                        .meta(meta)
                                        .status(HttpStatus.OK)
                                        .build();

                }
        }

        // Service to get a sale by id
        public MyResponse getById(Long id) throws MyException {
                // Find the sale by id
                Sale sale = this.findById(id);
                // Return the sale DTO
                return MyResponse.builder()
                                .data(SaleDTO.toDTO(sale))
                                .status(HttpStatus.OK)
                                .build();
        }

        // Service to create a sale
        @Transactional
        public MyResponse create(SaleRequest request, String bearerToken) throws MyException {
                // Find the vehicle
                Vehicle vehicle = vehicleService.findById(request.getVehicleId());
                // Throw an exception if the vehicle is already sold
                if (vehicle.getStatus() == VehicleStatus.SOLD) {
                        throw new MyException(MyErrorResponse.builder()
                                        .message("Vehicle " + vehicle.getModel() + " is already sold")
                                        .build());
                }
                // Find the client
                Client client = clientService.findById(request.getClientId());
                // Find the user
                User user = jwtUtils.extractUserFromBeaererToken(bearerToken);
                // Chnage the vehicle status to sold
                vehicle.setStatus(VehicleStatus.SOLD);
                // Save the sale
                Sale sale = Sale.builder()
                                .date(LocalDate.now())
                                .totalPrice(request.getTotalPrice())
                                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                                .installments(request.getInstallments())
                                .client(client)
                                .user(user)
                                .vehicle(vehicle)
                                .build();
                saleRepository.save(sale);
                // Return the sale
                return MyResponse.builder()
                                .status(HttpStatus.CREATED)
                                .message("Vehicle " + vehicle.getModel() + " sold to " + client.getFirstName() + " "
                                                + client.getLastName())
                                .build();
        }

        // Service to update a sale
        @Transactional
        public MyResponse update(Long id, UpdateSaleRequest request) throws MyException {
                // Find the sale by id
                Sale sale = this.findById(id);
                // Update the sale informations
                sale.setTotalPrice(request.getTotalPrice());
                sale.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
                sale.setInstallments(request.getInstallments());
                // Save the updated sale
                saleRepository.save(sale);
                // Return response
                return MyResponse.builder()
                                .status(HttpStatus.OK)
                                .message("Sale updated successfully")
                                .build();
        }

        // Service to delete a sale
        @Transactional
        public MyResponse delete(Long id) throws MyException {
                // Find the sale by id
                Sale sale = this.findById(id);
                // Change the vehicle status to available
                Vehicle vehicle = sale.getVehicle();
                vehicle.setStatus(VehicleStatus.AVAILABLE);
                vehicleRepository.save(vehicle);
                // Delete the sale
                saleRepository.delete(sale);
                // Return response
                return MyResponse.builder()
                                .status(HttpStatus.OK)
                                .message("Sale deleted successfully")
                                .build();
        }

        // Utils to find sale by id
        public Sale findById(Long id) throws MyException {
                return saleRepository.findById(id).orElseThrow(
                                () -> new MyException(MyErrorResponse.builder()
                                                .message("Sale not found")
                                                .build()));
        }

}
