package com.woof.api.member.model.entity;

import com.woof.api.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class EmailVerify extends BaseEntity {
    private String email;
    private String uuid;
}
