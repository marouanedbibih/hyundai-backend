package org.hyundai.backend.vehicle;

import org.hyundai.backend.sale.Sale;
import org.hyundai.backend.utils.BasicEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "vehicles")
public class Vehicle extends BasicEntity {

    private String model;
    private Integer year;
    private Double price;
    private String color;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    private Boolean isPromotion;

    @Column(nullable = true)
    private Double discount;

    // Relationships
    @OneToOne(mappedBy = "vehicle")
    private Sale sale;

    // Equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Vehicle))
            return false;
        Vehicle vehicle = (Vehicle) obj;
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

}
