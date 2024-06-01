package com.woof.api.member.model.responsedto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostManagerSignupRes {
    private Boolean isSuccess;
    private Long code;
    private String message;
    private Map<String, Long> result;
    private Boolean success;

}
