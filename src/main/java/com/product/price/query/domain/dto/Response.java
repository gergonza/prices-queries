package com.product.price.query.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

/**
 * Java Object Representation that contains the product information to be shown in the endpoint response.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
@Data
@Builder
public class Response {

    private int productId;
    private int brandId;
    private int priceListId;
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    @JsonFormat(shape = STRING)
    private BigDecimal price;
}
