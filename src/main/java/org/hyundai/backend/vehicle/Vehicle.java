package org.hyundai.backend.vehicle;

import org.hyundai.backend.sale.Sale;
import org.hyundai.backend.utils.BasicEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity
@Table(name = "vehicles")
public class Vehicle extends BasicEntity {

    private String model;
    private Integer year;
    private Double price;
    private String color;
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
