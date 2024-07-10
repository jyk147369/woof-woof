package com.woof.api.member.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchMemberUpdateRes {

    private String nickname;
    private String petName;
    private String phoneNumber;

}