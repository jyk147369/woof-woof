package com.woof.api.payment.model.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPortOneRes {
    private Long id;
    private Integer price;
}
