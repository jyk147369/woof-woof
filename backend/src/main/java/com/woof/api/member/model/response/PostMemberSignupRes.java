package com.woof.api.member.model.response;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostMemberSignupRes {
    @NotBlank
    private Long memberIdx;

    @NotBlank
    @Size(max = 45)
    private String memberEmail;

    @NotBlank
    @Size(max = 20)
    private String memberName;

}
