package com.woof.api.orders.model.dto;


import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;

@Builder
@Data
public class OrdersUpdateReq {
    private Long idx;

    private Integer time;
    private String place;
    private String orderDetails;

}