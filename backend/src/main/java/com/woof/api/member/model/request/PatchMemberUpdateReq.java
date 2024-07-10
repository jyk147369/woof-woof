package com.woof.api.member.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchMemberUpdateReq {

    private String nickname;
    private String petName;
    private String phoneNumber;
    private String pw;
    private String checkPw;

}
