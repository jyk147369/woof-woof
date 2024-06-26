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

    private String email;
    private String pw;
    private String name;
    private String nickname;
    private String authority;
}
