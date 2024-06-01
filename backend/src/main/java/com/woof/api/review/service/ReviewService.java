package com.woof.api.review.service;


import com.woof.api.orders.model.Orders;
import com.woof.api.review.model.Review;
import com.woof.api.review.model.dto.ReviewDto;
import com.woof.api.review.model.dto.ReviewResDto;
import com.woof.api.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {
    ReviewRepository reviewRepository;



    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }

    public ReviewResDto create(ReviewDto reviewDto) {
        Review review = Review.builder()
//                .name(reviewDto.getName())
                .text(reviewDto.getText())
//                .productNumber(reviewDto.getProductNumber())
//                .orders(Orders.builder()
//                        .idx(reviewDto.getOrdersIdx())
//                        .build())
//                .product(Product.builder()
//                        .idx(reviewDto.getProductIdx())
//                        .build()) //상품idx 가져온 것
                .build();

        Review result = reviewRepository.save(review);


        return ReviewResDto.builder()
                .idx(result.getIdx())
//                .name(result.getName())
                .text(result.getText())
//                .productNumber(result.getProductNumber())
//                .ordersIdx(result.getOrders().getIdx())
                .status("리뷰가 생성되었습니다.")
                .build();
    }

    public ReviewDto read(Long idx) {
        Review review = reviewRepository.findById(idx).orElseThrow(()->new ReviewNotFoundException(""));

        return ReviewDto.builder()
                .idx(review.getIdx())
                .ordersIdx(review.getOrders().getIdx())
                .text(review.getText())
                .name(review.getName())
//                .productNumber(review.getProductNumber())
                .build();
    }

    public void update(Long idx, ReviewDto reviewDto) {
        Optional<Review> result = reviewRepository.findById(idx);
        if(result.isPresent()) {
            Review review = result.get();
            review.setText(reviewDto.getText());
            reviewRepository.save(review);
        }

    }

    public void delete(Long idx) {
        reviewRepository.delete(Review.builder().idx(idx).build());
    }
}