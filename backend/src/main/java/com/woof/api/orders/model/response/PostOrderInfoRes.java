package com.woof.api.orders.model.dto;


import com.woof.api.orders.model.Orders;
import com.woof.api.payment.model.dto.response.GetPortOneRes;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class PostOrderInfoRes {
    private String impUid;
    private LocalDate localDate;

    public static PostOrderInfoRes toEntity(String impUid, GetPortOneRes getPortOneRes, Orders orders) {
        return PostOrderInfoRes.builder()
                .impUid(impUid)
                .localDate(orders.getOrderDate())
                .build();
    }
}
