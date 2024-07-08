package com.woof.api.orders.model.request;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrdersUpdateReq {
    private Long idx;

    private Integer time;
    private String place;
    private String orderDetails;

}