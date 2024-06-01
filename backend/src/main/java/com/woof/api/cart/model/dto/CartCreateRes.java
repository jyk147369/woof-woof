package com.woof.api.cart.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartCreateRes {
    private Long cartIdx;
    private Long memberIdx;
    private Long productCeoIdx;

    private Long productManagerIdx;

}
