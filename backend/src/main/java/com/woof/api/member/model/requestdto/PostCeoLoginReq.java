package com.woof.api.member.model.requestdto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCeoLoginReq {
    private String businessnum;
    private String password;
}
