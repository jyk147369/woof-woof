package com.woof.api.orders.model.response;

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
