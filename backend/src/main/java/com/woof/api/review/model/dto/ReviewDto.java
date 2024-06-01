package com.woof.api.review.model.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class ReviewDto {
    private  Long idx;

    private final String name;
//    private final Integer productNumber; // Add productNumber field
    private final String text;

    private Long ordersIdx;
}








