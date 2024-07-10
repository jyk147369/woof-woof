package com.woof.api.member.model.entity;

import com.woof.api.common.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MemberProfileImage extends BaseEntity {

    private Long memberIdx;
    private String memberImageAddr;
}
