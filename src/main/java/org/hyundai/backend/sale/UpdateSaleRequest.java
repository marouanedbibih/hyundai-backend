package org.hyundai.backend.sale;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSaleRequest {
    @NotNull(message = "Total price is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Total price must be greater than or equal to 0.01")
    private Double totalPrice;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @NotNull(message = "Installments field is required")
    @Min(value = 1, message = "Installments must be at least 1")
    @Max(value = 60, message = "Installments must not exceed 60")
    private Integer installments;
}
