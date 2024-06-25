package com.woof.api.admin.exception;

import com.woof.api.common.error.ErrorCode;
import com.woof.api.common.error.exception.BusinessException;

public class AdminException extends BusinessException {
    public AdminException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AdminException(ErrorCode errorCode) {
        super(errorCode);
    }

    public static AdminException forTokenNotExists() {
        return new AdminException(ErrorCode.TOKEN_NOT_EXISTS, "토큰이 존재하지 않습니다. 로그인을 먼저 진행해주세요.");
    }

    public static AdminException forInvalidToken() {
        return new AdminException(ErrorCode.INVALID_VERIFICATION_TOKEN, "유효하지 않는 토큰입니다. 다시 로그인해주세요.");
    }

    public static AdminException forInvalidPassword() {
        return new AdminException(ErrorCode.DIFFERENT_MEMBER_PASSWORD, "비밀번호가 틀렸습니다.");
    }

    public static AdminException forExpiredToken() {
        return new AdminException(ErrorCode.EXPIRED_VERIFICATION_TOKEN, "토큰이 만료되었습니다. 다시 로그인 해주세요.");
    }

    public static AdminException forDifferentPassword() {
        return new AdminException(ErrorCode.SAME_MEMBER_PASSWORD, "기존에 사용하던 비밀번호와 같습니다. 다른 비밀번호를 입력해주세요.");
    }

    public static AdminException forDuplicateEmail() {
        return new AdminException(ErrorCode.DUPLICATE_SIGNUP_ID, "이미 사용중인 이메일입니다. 다른 이메일을 입력해주세요.");
    }
}
