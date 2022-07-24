package app.pizzahut.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderModel {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String shippingAddress;

    private double totalPrice;

}
