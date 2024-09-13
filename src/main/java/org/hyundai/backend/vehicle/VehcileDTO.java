package org.hyundai.backend.vehicle;

import org.hyundai.backend.utils.MyDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class VehcileDTO extends MyDTO {
    private String model;
    private Integer year;
    private Double price;
    private String color;
    private VehicleStatus status;

    private Boolean isPromotion;

    private Double discount;

    // Equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof VehcileDTO))
            return false;
        VehcileDTO vehicle = (VehcileDTO) obj;
        return this.getId().equals(vehicle.getId());
    }

    // HashCode method
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    // ToString method
    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + this.getId() +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", status=" + status +
                ", isPromotion=" + isPromotion +
                ", discount=" + discount +
                '}';
    }

    // ToEntity method
    public Vehicle toEntity() {
        return Vehicle.builder()
                .id(this.getId())
                .model(this.model)
                .year(this.year)
                .price(this.price)
                .color(this.color)
                .status(this.status)
                .isPromotion(this.isPromotion)
                .discount(this.discount)
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    // toDTO method
    public static VehcileDTO toDTO(Vehicle vehicle) {
        return VehcileDTO.builder()
                .id(vehicle.getId())
                .model(vehicle.getModel())
                .year(vehicle.getYear())
                .price(vehicle.getPrice())
                .color(vehicle.getColor())
                .status(vehicle.getStatus())
                .isPromotion(vehicle.getIsPromotion())
                .discount(vehicle.getDiscount())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .build();
    }
}
