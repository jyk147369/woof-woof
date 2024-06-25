package com.woof.api.common.error.exception;

import com.woof.api.common.error.ErrorCode;

public class EntityDuplicateException extends BusinessException{

    public EntityDuplicateException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public EntityDuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
