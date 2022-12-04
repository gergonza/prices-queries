package com.product.price.query.exception;

/**
 * Custom Exception that supports a Product Not Found scenario.
 *
 * @author Germán González
 * @version 1.0
 * @since 2022-12-03
 *
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructor of the Exception.
     *
     * @param errorMessage Message of the Exception
     *
     */
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
