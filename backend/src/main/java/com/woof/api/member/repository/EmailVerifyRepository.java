package com.woof.api.member.repository;

import com.woof.api.member.model.entity.EmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerifyRepository extends JpaRepository<EmailVerify, Long> {

    Optional<EmailVerify> findByEmail(String email);
}
