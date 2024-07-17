package com.woof.api.member.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchMemberUpdateRes {

    private String nickname;
    private String petName;
    private String phoneNumber;
    private LocalDateTime updatedAt;

}