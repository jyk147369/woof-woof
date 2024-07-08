package com.woof.api.orders.model.dto;

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
