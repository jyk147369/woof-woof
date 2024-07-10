package com.woof.api.member.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatchMemberUpdateImgRes {

    private String profileImg;
    private LocalDateTime updatedAt;

}