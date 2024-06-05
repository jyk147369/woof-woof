package com.woof.api.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostMemberSignupReq {

    private String memberEmail;
    private String memberPw;
    private String memberName;
    private String memberNickname;
    private String authority;
}
