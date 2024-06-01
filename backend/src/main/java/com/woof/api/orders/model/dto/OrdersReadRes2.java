package com.woof.api.orders.model.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class OrdersReadRes2 {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private OrdersReadRes result;
    private Boolean success;


}