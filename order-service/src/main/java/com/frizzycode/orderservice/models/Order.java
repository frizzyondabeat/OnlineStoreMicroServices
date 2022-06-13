package com.frizzycode.orderservice.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_list")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItemsList;

}
