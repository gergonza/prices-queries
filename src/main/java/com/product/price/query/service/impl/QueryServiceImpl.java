package com.product.price.query.service.impl;

import static java.util.Comparator.comparing;

import com.product.price.query.domain.dto.Response;
import com.product.price.query.domain.entity.Detail;
import com.product.price.query.exception.NotFoundException;
import com.product.price.query.repository.QueryRepository;
import com.product.price.query.service.QueryService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final QueryRepository repository;

    @Override
    public Response getPriceDetail(int productId, int brandId, LocalDateTime applicationDateTime) {
        List<Detail> priceDetails = this.repository.findAll();
        Detail price = priceDetails.stream().filter(
                detail -> productId == detail.getProductId()
                && brandId == detail.getBrandId()
                && !applicationDateTime.isBefore(detail.getStartDate())
                && !applicationDateTime.isAfter(detail.getEndDate()))
                .max(comparing(Detail::getPriority))
                .orElseThrow(() -> {
                    throw new NotFoundException("There is no price information by this data: productId ("
                            + productId + "), brandId (" + brandId + ") and applicationDateTime ("
                            + applicationDateTime + ")");
                });
        return Response.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceListId(price.getPriceListId())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .totalPrice(price.getPrice())
                .build();
    }
}
