package com.woof.api.member.repository;

import com.woof.api.common.BaseResponse;
import com.woof.api.member.exception.MemberNotFoundException;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.entity.MemberProfileImage;
import com.woof.api.member.model.response.GetMemberReadRes;
import com.woof.api.member.service.MemberService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByMemberEmail(String memberEmail);
}
