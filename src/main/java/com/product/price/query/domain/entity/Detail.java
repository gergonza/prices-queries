package com.product.price.query.domain.entity;

import static jakarta.persistence.GenerationType.AUTO;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "prices")
@Table(name = "prices", schema = "brands")
public class Detail {

    @Id @GeneratedValue(strategy = AUTO) private int id;
    @Column(name = "product_id") private int productId;
    @Column(name = "brand_id") private int brandId;
    @Column(name = "price_list") private int priceListId;
    private int priority;
    private String curr;
    @Column(name = "start_date") private LocalDateTime startDate;
    @Column(name = "end_date") private LocalDateTime endDate;
    private BigDecimal price;
}
