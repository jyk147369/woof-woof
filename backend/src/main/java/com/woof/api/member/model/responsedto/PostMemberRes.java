package com.woof.api.member.model.responsedto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMemberRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private Map<String, Integer> result;
    private Boolean success;

}
