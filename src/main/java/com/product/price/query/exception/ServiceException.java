package com.product.price.query.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends Exception {

    private String code;

    public ServiceException(String code, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.code = code;
    }
}
