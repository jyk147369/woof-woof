package com.woof.api.payment.controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import com.woof.api.common.Response;
import com.woof.api.payment.service.PaymentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {
    private final PaymentService paymentService;
    @ApiOperation(value="정기 결제", notes="회원이 매달 정기적으로 일정 금액을 결제한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/validation/subscribe")
    public Response validationSubscribe(String impUid) throws IamportResponseException, IOException {
        if(paymentService.subscribeValidation(impUid)) {
            return Response.success("결제 성공");
        }

        return Response.error("결제 금액 이상");
    }

}
