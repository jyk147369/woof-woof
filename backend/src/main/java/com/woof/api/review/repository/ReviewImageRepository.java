package com.woof.api.review.repository;

import com.woof.api.review.model.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    public List<ReviewImage> findByReviewIdx(Long idx);
    public void deleteAllByReviewIdx(Long idx);
}
