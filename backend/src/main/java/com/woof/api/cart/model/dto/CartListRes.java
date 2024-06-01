package com.woof.api.cart.model.dto;

import lombok.Builder;
import lombok.Data;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Builder
@Data
public class CartListRes {
    private Long cartIdx;
    private Long memberIdx;
    private Long productCeoIdx;
    private Long productManagerIdx;

    @NotNull
//    @Pattern(regexp = "[가-힣0-9]{2,5}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}$")
    private String productCeoStoreName;
    @NotNull
//    @Pattern(regexp = "[가-힣0-9]{2,5}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}$")
    private String productMangerName;
}
