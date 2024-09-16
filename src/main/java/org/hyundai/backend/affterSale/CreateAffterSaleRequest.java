package org.hyundai.backend.affterSale;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAffterSaleRequest {

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Cost is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
    private Double cost;

    @NotNull(message = "Appointment time is mandatory")
    @Future(message = "Appointment time must be in the future")
    private LocalDate appointment;

    @NotBlank(message = "Status is mandatory")
    private String status;

    @NotNull(message = "Vehicle ID is mandatory")
    private Long vehicleId;

    @NotNull(message = "Client ID is mandatory")
    private Long clientId;

}
