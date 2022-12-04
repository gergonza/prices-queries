package com.product.price.query.controller;

import com.product.price.query.domain.dto.Response;
import com.product.price.query.service.QueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller that enables an API to retrieve information about products and their prices.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/price/query")
public class QueryController {

    private final QueryService service;

    /**
     * Endpoint to publish a product information based in the search criteria described below.
     *
     * @param productId Identification of the Product
     * @param brandId Identification of the Brand
     * @param applicationDateTime DateTime of the Price Application
     * @return Response DTO with product information to be shown
     *
     */
    @GetMapping(path = "/details/get/{productId}/{brandId}/{applicationDate}")
    public Response getPriceDetail(@PathVariable("productId") @Valid @Min(value = 1) final int productId,
                                   @PathVariable("brandId") @Valid @Min(value = 1) final int brandId,
                                   @PathVariable("applicationDate") @Valid @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss") @NotNull final LocalDateTime applicationDateTime) {
        return this.service.getPriceDetail(productId, brandId, applicationDateTime);
    }
}
