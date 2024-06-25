package com.woof.api.common.error.exception;

import com.woof.api.common.error.ErrorCode;
import lombok.Getter;
@Getter
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;
    private String message;

    public BusinessException(ErrorCode errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
    }

}
