package com.woof.api.member.model.response;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMemberFindPwRes {
    private LocalDateTime updatedAt;
}
