package org.hyundai.backend.order;

import org.hyundai.backend.utils.BasicEntity;
import org.hyundai.backend.vehicle.Vehicle;
import org.springframework.cglib.core.Local;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Data
@SuperBuilder
public class Order extends BasicEntity {

    private String supplier;
    private LocalDate orderDate;
    private LocalDate expectedDeliveryDate;
    private LocalDate deliveryDate;
    private OrderStatus status;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    // Equal method
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Order))
            return false;
        if (!super.equals(o))
            return false;

        Order order = (Order) o;

        if (supplier != null ? !supplier.equals(order.supplier) : order.supplier != null)
            return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null)
            return false;
        if (expectedDeliveryDate != null ? !expectedDeliveryDate.equals(order.expectedDeliveryDate)
                : order.expectedDeliveryDate != null)
            return false;
        if (deliveryDate != null ? !deliveryDate.equals(order.deliveryDate) : order.deliveryDate != null)
            return false;
        if (status != order.status)
            return false;
        return vehicle != null ? vehicle.equals(order.vehicle) : order.vehicle == null;
    }

    // Hashcode method
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (supplier != null ? supplier.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (expectedDeliveryDate != null ? expectedDeliveryDate.hashCode() : 0);
        result = 31 * result + (deliveryDate != null ? deliveryDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (vehicle != null ? vehicle.hashCode() : 0);
        return result;
    }

    // To string method
    public String toString() {
        return "Order{" +
                "supplier='" + supplier + '\'' +
                ", orderDate=" + orderDate +
                ", expectedDeliveryDate=" + expectedDeliveryDate +
                ", deliveryDate=" + deliveryDate +
                ", status=" + status +
                ", vehicle=" + vehicle +
                '}';
    }

}
