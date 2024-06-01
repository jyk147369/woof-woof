package com.woof.api.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResDto {
    private  Long idx;

    private String status;
    private  String name;
//    private  Integer productNumber; // Add productNumber field
    private  String text;

    private Long ordersIdx;
}
