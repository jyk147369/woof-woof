package com.woof.api.payment.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.woof.api.common.Response;
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
    public Response validationSubscribe(String impUid) throws IamportResponseException, IOException {
        if(paymentService.subscribeValidation(impUid)) {
            return Response.success("결제 성공");
        }

        return Response.error("결제 금액 이상");
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

    // 환불
    @RequestMapping(method = RequestMethod.POST, value = "/payments/cancel")
    public IamportResponse<Payment> refundPayment(@RequestParam String impUid, @RequestParam BigDecimal amount, @RequestParam String reason) {
        try {
            return paymentService.refundPayment(impUid, amount, reason);
        } catch (IamportResponseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
