package com.product.price.query.service;

import com.product.price.query.domain.dto.Response;
import java.time.LocalDateTime;

/**
 * Service that enables the function and routines where to be used external APIs and database repositories.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
public interface QueryService {

    /**
     * Method to invoke the feature of retrieve product information.
     *
     * @param productId Identification of the Product
     * @param brandId Identification of the Brand
     * @param applicationDateTime DateTime of the Price Application
     * @return Response DTO with product information to be shown
     *
     */
    Response getPriceDetail(final int productId, final int brandId, final LocalDateTime applicationDateTime);
}
