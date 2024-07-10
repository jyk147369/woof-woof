package com.woof.api.orders.exception;

import com.woof.api.common.error.ErrorCode;

public class OrdersException extends RuntimeException {

    private ErrorCode errorCode;

    public OrdersException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public OrdersException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public OrdersException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
