package org.hyundai.backend.affterSale;

import java.time.LocalDate;
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


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAffterSaleRequest {
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
}
