package com.woof.api.orders.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OrdersListRes {
    private Boolean isSuccess;
    private Integer code;
    private String message;
    private List<OrdersReadRes> result;
    private Boolean success;
}