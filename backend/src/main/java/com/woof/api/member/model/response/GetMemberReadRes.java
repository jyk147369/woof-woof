package com.woof.api.member.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberReadRes {

    private String email;
    private String password;
    private String nickname;
    private String authority;

}
