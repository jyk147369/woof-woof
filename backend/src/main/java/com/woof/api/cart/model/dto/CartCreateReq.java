package com.woof.api.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartCreateReq {
    private Long productCeoIdx;
    private Long productManagerIdx;
    private Long memberIdx;
}
