package com.product.price.query.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends Exception {

    private final String code = "400";

    public BadRequestException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
