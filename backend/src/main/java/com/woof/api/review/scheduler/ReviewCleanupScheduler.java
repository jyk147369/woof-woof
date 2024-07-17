package com.woof.api.review.scheduler;

import com.woof.api.review.model.entity.Review;
import com.woof.api.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReviewCleanupScheduler {

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    @Scheduled(fixedRate = 3600000) // 매 1시간마다 실행
    public void cleanUpOldReviews() {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24); //status가 2가 되면 24시간내 정리해서 삭제

        List<Review> oldReviews = reviewRepository.findByStatusAndUpdatedAtBefore(2, twentyFourHoursAgo);
        for (Review review : oldReviews) {
            reviewRepository.delete(review);
        }

        System.out.println("Old reviews cleaned up at: " + LocalDateTime.now());
    }
}
