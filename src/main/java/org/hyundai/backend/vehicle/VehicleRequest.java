package org.hyundai.backend.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Year;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Year is required")
    @Min(value = 1886, message = "Year must be no earlier than 1886")
    @Max(value = Year.MAX_VALUE, message = "Year must be no later than the current year")
    private Integer year;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @NotBlank(message = "Color is required")
    private String color;

    @NotNull(message = "Status is required")
    private VehicleStatus status;

    @NotNull(message = "Promotion flag is required")
    private Boolean isPromotion;

    private Double discount;

    @AssertTrue(message = "If promotion is active, discount must be positive and less than or equal to the price")
    private boolean isDiscountValid() {
        // Validate the discount only if promotion is active and discount is not null
        if (Boolean.TRUE.equals(isPromotion) && discount != null) {
            return discount > 0 && discount <= price;
        }
        // If promotion is not active or discount is null, no validation is needed
        return true;
    }
}
