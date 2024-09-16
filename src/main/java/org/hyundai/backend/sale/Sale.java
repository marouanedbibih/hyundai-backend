package org.hyundai.backend.sale;

import java.time.LocalDate;

import org.hyundai.backend.client.Client;
import org.hyundai.backend.user.User;
import org.hyundai.backend.utils.BasicEntity;
import org.hyundai.backend.vehicle.Vehicle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "sales")
public class Sale extends BasicEntity {

    private LocalDate date;
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = true)
    private Integer installments;

    // Relationships

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "vehicle_id",nullable = false)
    private Vehicle vehicle;

    // Equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Sale))
            return false;
        Sale sale = (Sale) obj;
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
        return "Sale{" +
                "id=" + this.getId() +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                ", paymentMethod=" + paymentMethod +
                ", installments=" + installments +
                ", clientName=" + client.getLastName() + client.getFirstName() +
                '}';
    }

}
