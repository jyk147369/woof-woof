package com.woof.api.payment.model;

import com.woof.api.common.Constants;
import lombok.Getter;

@Getter
public enum SubscribeInfo {
    STANDARD(Constants.SubscribeType.STANDARD, 20000),
    VIP(Constants.SubscribeType.VIP, 30000);

    private final Constants.SubscribeType type;
    private final Integer price;

    private SubscribeInfo(Constants.SubscribeType type, Integer price) {
        this.type = type;
        this.price = price;
    }

}
