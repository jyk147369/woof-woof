package com.woof.api.member.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberReadRes {

    private String email;
    private String name;
    private String nickname;
    private String authority;
    private String profileImage;

}
