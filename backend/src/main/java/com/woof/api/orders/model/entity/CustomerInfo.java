package com.woof.api.orders.model.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class CustomerInfo {
    Long idx;
    String name;
    String place;
    String phNum;
    String orderDetails;
}