package com.woof.api.member.repository;

import com.woof.api.member.model.entity.MemberProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileImageRepository extends JpaRepository<MemberProfileImage,Long> {
}
