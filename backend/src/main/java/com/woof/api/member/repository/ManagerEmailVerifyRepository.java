package com.woof.api.member.repository;

import com.woof.api.member.model.entity.ManagerEmailVerify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerEmailVerifyRepository extends JpaRepository<ManagerEmailVerify, Long> {
    Optional<ManagerEmailVerify> findByEmail(String email);
}
