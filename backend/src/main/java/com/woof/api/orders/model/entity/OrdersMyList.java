package com.woof.api.orders.model.entity;

import lombok.*;


@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OrdersMyList {
    private Long idx;

//    private String reservation_status;
    private Integer time;
    private  String place;
//    private String orderDetails;
//    private Long productManagerIdx;
//    private Long productCeoIdx;
//    private Long memberIdx;
//    private String productsContents;
//    private String productCeoContents;
//    private String productContents;
//    private String productName;
//    private Integer productPrice;
}
