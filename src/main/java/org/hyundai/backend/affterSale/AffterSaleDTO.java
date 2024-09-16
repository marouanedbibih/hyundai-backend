package org.hyundai.backend.affterSale;

import java.time.LocalDateTime;

import org.hyundai.backend.utils.MyDTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AffterSaleDTO extends MyDTO {

    // Service attributes
    private String description;
    private Double cost;
    private LocalDate appointment;
    @Enumerated(EnumType.STRING)
    private AffterSaleStatus status;
    @Enumerated(EnumType.STRING)
    private AffterSaleType type;
    // Client attributes
    private Long clientId;
    private String clientName;
    // User attributes
    private Long userId;
    private String username;
    // Vehicle attributes
    private Long vehicleId;
    private String vehicleModel;
    private String vehicleColor;
    private Integer vehicleYear;
    private Double vehiclePrice;

    // Equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof AffterSaleDTO))
            return false;
        AffterSaleDTO service = (AffterSaleDTO) obj;
        return this.getId().equals(service.getId());
    }

    // HashCode method
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    // ToString method
    @Override
    public String toString() {
        return "ServiceDTO{" +
                "id=" + this.getId() +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", appointment=" + appointment +
                ", status=" + status +
                ", type=" + type +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", vehicleId=" + vehicleId +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", vehicleYear=" + vehicleYear +
                ", vehiclePrice=" + vehiclePrice +
                '}';
    }

    // ToDTO method
    public static AffterSaleDTO toDTO(AffterSale affterSale) {
        return AffterSaleDTO.builder()
                .id(affterSale.getId())
                .description(affterSale.getDescription())
                .cost(affterSale.getCost())
                .appointment(affterSale.getAppointment())
                .status(affterSale.getStatus())
                .type(affterSale.getType())
                .clientId(affterSale.getClient().getId())
                .clientName(affterSale.getClient().getFirstName() + " " + affterSale.getClient().getLastName())
                .userId(affterSale.getUser().getId())
                .username(affterSale.getUser().getName())
                .vehicleId(affterSale.getVehicle().getId())
                .vehicleModel(affterSale.getVehicle().getModel())
                .vehicleColor(affterSale.getVehicle().getColor())
                .vehicleYear(affterSale.getVehicle().getYear())
                .vehiclePrice(affterSale.getVehicle().getPrice())
                .build();
    }
}
