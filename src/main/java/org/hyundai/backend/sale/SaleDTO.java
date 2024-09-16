package org.hyundai.backend.sale;

import org.hyundai.backend.client.Client;
import org.hyundai.backend.user.User;
import org.hyundai.backend.utils.MyDTO;
import org.hyundai.backend.vehicle.Vehicle;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SaleDTO extends MyDTO {

    // Sale attributes
    private LocalDate date;
    private Double totalPrice;
    private PaymentMethod paymentMethod;
    private Integer installments;
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
        if (!(obj instanceof SaleDTO))
            return false;
        SaleDTO sale = (SaleDTO) obj;
        return this.getId().equals(sale.getId());
    }

    // HashCode method
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    // ToString method
    @Override
    public String toString() {
        return "SaleDTO{" +
                "id=" + this.getId() +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                ", paymentMethod=" + paymentMethod +
                ", installments=" + installments +
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

    // ToEntity method
    public Sale toEntity(Client client, User user, Vehicle vehicle) {
        return Sale.builder()
                .id(this.getId())
                .date(this.date)
                .totalPrice(this.totalPrice)
                .paymentMethod(this.paymentMethod)
                .installments(this.installments)
                .client(client)
                .user(user)
                .vehicle(vehicle)
                .build();
    }

    // toDTO method
    public static SaleDTO toDTO(Sale sale) {
        return SaleDTO.builder()
                .id(sale.getId())
                .date(sale.getDate())
                .totalPrice(sale.getTotalPrice())
                .paymentMethod(sale.getPaymentMethod())
                .installments(sale.getInstallments())
                .clientId(sale.getClient().getId())
                .clientName(sale.getClient().getFirstName() + " " + sale.getClient().getLastName())
                .userId(sale.getUser().getId())
                .username(sale.getUser().getUsername())
                .vehicleId(sale.getVehicle().getId())
                .vehicleModel(sale.getVehicle().getModel())
                .vehicleColor(sale.getVehicle().getColor())
                .vehicleYear(sale.getVehicle().getYear())
                .vehiclePrice(sale.getVehicle().getPrice())
                .build();
    }


}
