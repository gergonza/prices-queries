package com.product.price.query.service.impl;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Comparator.comparing;
import static java.time.LocalDateTime.parse;

import com.product.price.query.domain.dto.Response;
import com.product.price.query.domain.entity.Detail;
import com.product.price.query.exception.NotFoundException;
import com.product.price.query.repository.QueryRepository;
import com.product.price.query.service.QueryService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service Implementation that enables the function and routines where to be used external APIs and database repositories.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final QueryRepository repository;

    /**
     * Implementation of the Method to invoke the feature of retrieve product information.
     *
     * @param productId Identification of the Product
     * @param brandId Identification of the Brand
     * @param applicationDateTime DateTime of the Price Application
     * @return Response DTO with product information to be shown
     *
     */
    @Override
    public Response getPriceDetail(int productId, int brandId, LocalDateTime applicationDateTime) {
        LocalDateTime finalDateTime = getFinalFormatDateTime(applicationDateTime);
        List<Detail> priceDetails = this.repository.findAll();
        Detail price = priceDetails.stream().filter(
                detail -> productId == detail.getProductId()
                && brandId == detail.getBrandId()
                && !finalDateTime.isBefore(detail.getStartDate())
                && !finalDateTime.isAfter(detail.getEndDate()))
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
                .price(price.getPrice())
                .build();
    }

    /**
     * Method to convert a DateTime object with a first format in other object with a new format.
     *
     * @param dateTime DateTime Object with the original format
     * @return A DateTime Object with a new format
     *
     */
    private LocalDateTime getFinalFormatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter pattern = ofPattern("yyyy-MM-dd HH:mm:ss");
        return parse(dateTime.format(pattern), pattern);
    }
}
