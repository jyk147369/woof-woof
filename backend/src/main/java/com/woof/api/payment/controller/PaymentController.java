package com.woof.api.payment.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.woof.api.common.BaseResponse;
import com.woof.api.payment.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    // 정기 결제
    @ApiOperation(value="정기 결제", notes="회원이 매달 정기적으로 일정 금액을 결제한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/validation/subscribe")
    public BaseResponse validationSubscribe(String impUid) throws IamportResponseException, IOException {
        if(paymentService.subscribeValidation(impUid)) {
            return BaseResponse.successRes("code입니다",true,"결제 성공","아무거나0");

        }

        return BaseResponse.error("결제금액이상",true,"결제 성공","아무거나0");
    }

    // 구독 취소
    @RequestMapping(method = RequestMethod.POST, value = "/subscribe/cancel")
    public IamportResponse<com.siot.IamportRestClient.response.Payment> cancelSubscription(@RequestParam String customerUid) {
        try {
            return paymentService.cancelSubscription(customerUid);
        } catch (IamportResponseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
