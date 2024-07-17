package com.woof.api.review.repository;



import com.woof.api.member.model.entity.Member;
import com.woof.api.orders.model.entity.Orders;
import com.woof.api.product.model.entity.ProductManager;
import com.woof.api.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductManagerIdxAndStatus(Long productManagerIdx, Integer status);
    List<Review> findByProductSchoolIdxAndStatus(Long productSchoolIdx, Integer status);
    List<Review> findByMemberIdx(Long memberIdx);
    public Optional<Review> findByIdx(Long idx);
    List<Orders> findByOrderIdx(Long orderIdx);

    Optional<Review> findById(Long idx); // 필요 시 특정 리뷰를 찾는 메서드

//    List<Review> findByMember(Member member); // 특정 회원의 리뷰 찾기
//    List<Review> findByOrders(Orders orders); // 특정 주문의 리뷰 찾기

    List<Review> findByStatusAndUpdatedAtBefore(int status, LocalDateTime dateTime);
}

