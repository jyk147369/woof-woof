package com.woof.api.member.model.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMemberLoginRes {
    String accessToken;
}
