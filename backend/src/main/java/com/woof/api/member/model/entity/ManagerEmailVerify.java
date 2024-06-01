package com.woof.api.member.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class ManagerEmailVerify {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long idx;
    private String email;
    private String uuid;

}
