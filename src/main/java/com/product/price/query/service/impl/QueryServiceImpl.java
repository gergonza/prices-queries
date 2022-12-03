package com.product.price.query.service.impl;

import com.product.price.query.domain.dto.Response;
import com.product.price.query.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    @Override
    public Response getPriceDetail(int productId, int brandId, LocalDate applicationDate) {
        return null;
    }
}
