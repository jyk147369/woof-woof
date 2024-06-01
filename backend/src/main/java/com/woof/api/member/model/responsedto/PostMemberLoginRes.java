package com.woof.api.member.model.responsedto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMemberLoginRes {
    String accessToken;
    Long idx;
}
