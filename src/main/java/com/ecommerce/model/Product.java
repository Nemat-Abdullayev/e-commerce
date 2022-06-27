package com.ecommerce.model;

import com.ecommerce.model.base.AbstractBaseEntity;
import lombok.*;

import javax.persistence.*;


@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends AbstractBaseEntity {

    private Integer amountAvailable;
    private double cost;
    private String productName;
    private Long sellerId;
}
