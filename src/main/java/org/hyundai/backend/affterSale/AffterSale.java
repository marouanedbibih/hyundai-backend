package org.hyundai.backend.affterSale;

import org.hyundai.backend.client.Client;
import org.hyundai.backend.user.User;
import org.hyundai.backend.utils.BasicEntity;
import org.hyundai.backend.vehicle.Vehicle;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "affter_sales")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AffterSale extends BasicEntity {

    private String description;
    private Double cost;
    private LocalDate appointment;
    @Enumerated(EnumType.STRING)
    private AffterSaleStatus status;
    @Enumerated(EnumType.STRING)
    private AffterSaleType type;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof AffterSale))
            return false;
            AffterSale service = (AffterSale) obj;
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
        return "Service{" +
                "id=" + this.getId() +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", appointment=" + appointment +
                ", vehicle=" + vehicle +
                ", client=" + client +
                ", user=" + user +
                '}';
    }

}
