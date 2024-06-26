package com.woof.api.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewFileDto {
    private Long idx;
    private String originalFilename;
    private String downloadUrl;
}
