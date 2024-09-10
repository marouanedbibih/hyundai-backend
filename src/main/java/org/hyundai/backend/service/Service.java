package org.hyundai.backend.service;

import org.hyundai.backend.client.Client;
import org.hyundai.backend.user.User;
import org.hyundai.backend.utils.BasicEntity;
import org.hyundai.backend.vehicle.Vehicle;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "services")
@Data
@SuperBuilder
public class Service extends BasicEntity {

    private ServiceType type;
    private String description;
    private Double cost;
    private LocalDate appointment;
    private ServiceStatus status;

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
        if (!(obj instanceof Service))
            return false;
        Service service = (Service) obj;
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
