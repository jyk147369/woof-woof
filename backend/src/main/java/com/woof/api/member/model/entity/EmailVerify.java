package com.woof.api.member.model.entity;

import com.woof.api.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
@Entity
public class EmailVerify extends BaseEntity {
    private String email;
    private String uuid;
}
