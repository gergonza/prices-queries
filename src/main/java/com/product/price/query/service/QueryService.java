package com.product.price.query.service;

import com.product.price.query.domain.dto.Response;

import java.time.LocalDate;

public interface QueryService {

    Response getPriceDetail(final int productId, final int brandId, final LocalDate applicationDate);
}