package com.woof.api.member.model.response;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMemberSignupRes {
    @NotBlank
    private Long idx;

    @NotBlank
    @Size(max = 45)
    private String email;

    @NotBlank
    @Size(max = 20)
    private String name;

    private String role;

}
