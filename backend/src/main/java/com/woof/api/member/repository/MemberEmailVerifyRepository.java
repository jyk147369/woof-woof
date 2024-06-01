package com.woof.api.member.repository;

import com.woof.api.member.model.entity.MemberEmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberEmailVerifyRepository extends JpaRepository<MemberEmailVerify, Long> {
    Optional<MemberEmailVerify> findByEmail(String email);
}
