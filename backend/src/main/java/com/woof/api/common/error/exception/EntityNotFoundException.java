package com.woof.api.common.error.exception;

import com.woof.api.common.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
