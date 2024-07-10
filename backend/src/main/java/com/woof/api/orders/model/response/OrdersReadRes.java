package com.woof.api.orders.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class OrdersReadRes {
    private Long idx;
    private String name;
    private String phoneNumber;
    private Integer time;
    private String place;
    private Integer reservationStatus; // 0 (대기), 1 (확정), 2 (delete), 3 (완료)
}
