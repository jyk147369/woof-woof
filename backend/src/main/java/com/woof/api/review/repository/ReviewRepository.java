package com.woof.api.review.repository;



import com.woof.api.product.model.entity.ProductManager;
import com.woof.api.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductManagerIdxAndStatus(Long productManagerIdx, Integer status);
    List<Review> findByProductSchoolIdxAndStatus(Long productSchoolIdx, Integer status);
    List<Review> findByMemberIdx(Long memberIdx);
    public Optional<Review> findByIdx(Long idx);
}

