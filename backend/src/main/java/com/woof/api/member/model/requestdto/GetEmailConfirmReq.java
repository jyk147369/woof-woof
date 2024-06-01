package com.woof.api.member.model.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetEmailConfirmReq {

    private String email;
    private String jwt;
    private String uuid;
}
