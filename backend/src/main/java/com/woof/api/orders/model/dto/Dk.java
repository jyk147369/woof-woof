package com.woof.api.orders.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class Dk {
    Long idx;
    String name;
    String place;
    String phNum;
    String orderDetails;
}