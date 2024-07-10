package com.woof.api.product.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFileDto {
    private Long idx;
    private String originalFilename;
    private String downloadUrl;
}
