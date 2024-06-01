package com.woof.api.member.model.responsedto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchMemberUpdateRes {

    private String email;
    private String password;
    private String nickname;
    private String authority;

}