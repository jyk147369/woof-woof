package com.woof.api.member.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberImageIdx;

    private Long memberIdx;

    private String memberImageAddr;
}
