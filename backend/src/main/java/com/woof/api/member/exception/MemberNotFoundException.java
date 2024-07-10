package com.woof.api.member.exception;

import com.woof.api.common.error.ErrorCode;
import com.woof.api.common.error.exception.EntityNotFoundException;

public class MemberNotFoundException extends EntityNotFoundException {
    public MemberNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public static MemberNotFoundException forMemberEmail(String memberEmail) {
        return new MemberNotFoundException(ErrorCode.MEMBER_NOT_EXISTS, String.format("%s은 존재하지 않는 회원입니다.", memberEmail));
    }

    public static MemberNotFoundException forMemberIdx(Long memberIdx) {
        return new MemberNotFoundException(ErrorCode.MEMBER_NOT_EXISTS, String.format("%s은 존재하지 않는 회원입니다.", memberIdx));
    }

    public static MemberNotFoundException forChatRoomId() {
        return new MemberNotFoundException(ErrorCode.MEMBER_NOT_EXISTS, "해당 채팅방에 속한 멤버는 없습니다.");
    }
}