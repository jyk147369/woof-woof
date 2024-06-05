package com.woof.api.admin.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSignUpAdminRes {

    private String email;
    private String name;

}
