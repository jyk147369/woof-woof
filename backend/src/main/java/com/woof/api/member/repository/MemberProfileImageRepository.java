package com.woof.api.member.repository;

import com.woof.api.member.model.entity.Member;
import com.woof.api.member.model.entity.MemberProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberProfileImageRepository extends JpaRepository<MemberProfileImage,Long> {
    MemberProfileImage findByMemberIdx(Long idx);

    MemberProfileImage findByIdx(Long idx);
}
