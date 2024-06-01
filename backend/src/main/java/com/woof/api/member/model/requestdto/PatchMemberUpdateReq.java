package com.woof.api.member.model.requestdto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchMemberUpdateReq {

    private String email;
    private String password;
    private String nickname;
    private String authority;

}
