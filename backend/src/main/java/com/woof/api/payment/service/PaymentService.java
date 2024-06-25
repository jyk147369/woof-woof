package com.woof.api.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.AgainPaymentData;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.ScheduleEntry;
import com.siot.IamportRestClient.request.UnscheduleData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Schedule;
import com.woof.api.payment.model.SubscribeInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PaymentService {
    private final IamportClient iamportClient;

    public void regularPayment() throws IamportResponseException, IOException {
        AgainPaymentData again_data = new AgainPaymentData("test14", new Date().toString(), BigDecimal.valueOf(1005));
        IamportResponse<Payment> payment_response = iamportClient.againPayment(again_data);
        System.out.println(payment_response.getResponse());
    }

    public Boolean subscribeValidation(String impUid) throws IamportResponseException, IOException {
        IamportResponse<Payment> response = iamportClient.paymentByImpUid(impUid);

        Integer amount = response.getResponse().getAmount().intValue();

        String subscribeType = StringUtils.removeEnd(response.getResponse().getMerchantUid(), "4");

        // 구독한 사람이 가입한 타입에 맞는 가격으로 결제 되었는지 확인
        if (amount == SubscribeInfo.valueOf(subscribeType).getPrice()) {
            System.out.println("정상");
            return true;
        }

        System.out.println("비정상");

        return false;
    }

    // 구독 취소
    public IamportResponse<Payment> cancelSubscription(String customerUid) throws IamportResponseException, IOException {
        UnscheduleData unscheduleData = new UnscheduleData(customerUid);
        IamportResponse<List<Schedule>> response = iamportClient.unsubscribeSchedule(unscheduleData);

        // 구독 취소 처리 후, 결과 반환
        if (response.getResponse() != null && !response.getResponse().isEmpty()) {
            Schedule entry = response.getResponse().get(0);
            if ("cancelled".equals(entry.getStatus())) {
                System.out.println("구독 취소 완료");
                return iamportClient.paymentByImpUid(entry.getImp_uid());
            }
        }

        System.out.println("구독 취소 실패");
        return null;
    }

    // 환불
    public IamportResponse<Payment> refundPayment(String impUid, BigDecimal amount, String reason) throws IamportResponseException, IOException {
        CancelData cancelData = new CancelData(impUid, true, amount);
        cancelData.setReason(reason);

        IamportResponse<Payment> response = iamportClient.cancelPaymentByImpUid(cancelData);
        if (response.getResponse() != null) {
            System.out.println("환불 성공: " + response.getResponse().getImpUid());
        } else {
            System.out.println("환불 실패: " + response.getMessage());
        }

        return response;
    }

}
