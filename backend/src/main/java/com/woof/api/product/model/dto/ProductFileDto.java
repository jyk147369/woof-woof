package com.woof.api.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFileDto {
    private Long id;
    private String originalFilename;
    private String downloadUrl;
}
