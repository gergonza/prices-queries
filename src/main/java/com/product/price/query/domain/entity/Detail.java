package com.product.price.query.domain.entity;

import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Id;
import lombok.Value;

@Value
@Entity
public class Detail {

    @Id int id;
    int productId;
    int brandId;
    int priceListId;
    int priority;
    String curr;
    LocalDateTime startDate;
    LocalDateTime endDate;
    BigDecimal price;
}
