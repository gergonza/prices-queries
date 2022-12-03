package com.product.price.query.exception;

public class ServiceException extends Exception {

    private String code;

    public ServiceException(String code, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.code = code;
    }
}
