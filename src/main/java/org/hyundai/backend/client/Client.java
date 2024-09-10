package org.hyundai.backend.client;

import java.util.List;

import org.hyundai.backend.sale.Sale;
import org.hyundai.backend.service.Service;
import org.hyundai.backend.utils.BasicEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client extends BasicEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    // Relationships
    @OneToMany(mappedBy = "client", cascade = { CascadeType.REMOVE })
    private List<Sale> salesList;

    @OneToMany(mappedBy = "client", cascade = { CascadeType.REMOVE })
    private List<Service> servicesList;

    // Equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Client))
            return false;
        Client client = (Client) obj;
        return this.getId().equals(client.getId());
    }

    // HashCode method
    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

    // ToString method
    @Override
    public String toString() {
        return "Client{" +
                "id=" + this.getId() +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
