package com.product.price.query.controller;

import com.product.price.query.domain.dto.Response;
import com.product.price.query.service.QueryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/price/query")
public class QueryController {

    private final QueryService service;

    @GetMapping(path = "/details/get/{productId}/{brandId}/{applicationDate}")
    public Response getPriceDetail(@PathVariable("productId") @Valid @NotBlank final int productId,
                                   @PathVariable("brandId") @Valid @NotBlank final int brandId,
                                   @PathVariable("applicationDate") @Valid @NotBlank final LocalDate applicationDate) {
        return this.service.getPriceDetail(productId, brandId, applicationDate);
    }
}
