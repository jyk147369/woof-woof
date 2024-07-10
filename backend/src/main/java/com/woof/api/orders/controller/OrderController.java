package com.woof.api.orders.controller;

import com.woof.api.common.BaseResponse;
import com.woof.api.common.error.ErrorCode;
import com.woof.api.member.model.entity.Member;
import com.woof.api.orders.exception.OrdersException;
import com.woof.api.orders.model.entity.OrderDto;
import com.woof.api.orders.model.entity.Orders;
import com.woof.api.orders.model.response.*;
//import com.woof.api.orders.model.response.PostOrderInfoRes;
import com.woof.api.orders.model.entity.CustomerInfo;
import com.woof.api.orders.model.request.OrdersUpdateReq;
import com.woof.api.orders.model.response.OrdersReadRes2;
import com.woof.api.payment.service.PaymentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@CrossOrigin("*")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @ApiOperation(value="예약 작성", notes="회원이 예약 정보를 입력하여 예약서를 생성한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderDto orderDto){
        //수정 필요
        orderService.create(orderDto);

        return ResponseEntity.ok().body("예약에 성공하였습니다");
    }
    //TODO:baseentity로 바디에 나타나게 하기

    //예약 상태 변경 코드
    @RequestMapping(method = RequestMethod.PATCH, value = "/create/accept/{idx}")
    public void accept(@PathVariable Long idx)
    {
        orderService.accept(idx);
    }


    //예약승인 코드
    @RequestMapping(method = RequestMethod.PATCH, value = "/create/{idx}")
    public void lch(@PathVariable Long idx) {

    }
}

    @ApiOperation(value="예약 목록 조회", notes="회원이 예약한 전체 목록을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity list(){
        return ResponseEntity.ok().body(orderService.list());
    }

    @ApiOperation(value="예약 단일 조회", notes="회원이 예약한 단일 내역을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity read(Long idx){
        return ResponseEntity.ok().body(orderService.read(idx));
    }


    @ApiOperation(value="예약 수정", notes="회원이 예약서의 정보를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity <OrdersReadRes2> update(@RequestBody OrdersUpdateReq ordersUpdateReq) {
        //ResponseEntity 반환, orderDto를 매개변수로 받아옴
        OrdersReadRes2 result = orderService.update(ordersUpdateReq).getResult();
        //orderService의 업데이트 메소드에 orderDto를 받아옴

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value="예약 삭제", notes="회원이 예약서를 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{idx}")
    public ResponseEntity delete (@PathVariable Long idx){
        orderService.delete(idx);

        return ResponseEntity.ok().body("삭제를 성공했습니다.");
    }

//    @ApiOperation(value="회원 예약목록 조회", notes="회원이 예약한 목록을 회원 idx를 입력하여 조회한다.")
//    @GetMapping("/{memberIdx}")
//    public ResponseEntity getOrders(@PathVariable Long memberIdx) {
//        List<CustomerInfo> orders = orderService.getOrdersByMemberIdx(memberIdx);
//        return ResponseEntity.ok().body(orders);
//    }
//
//    @ApiOperation(value = "상품 주문")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "email", value = "이메일을 받기 위한 토큰 입력",
//                    required = true, paramType = "query", dataType = "string", defaultValue = ""),
//            @ApiImplicitParam(name = "impUid", value = "주문 번호 입력",
//                    required = true, paramType = "query", dataType = "string", defaultValue = "")})
//    @RequestMapping(method = RequestMethod.POST, value = "/validation")
//    public BaseResponse<List<PostOrderInfoRes>> ordersCreate(@AuthenticationPrincipal Member member,
//                                                             @RequestBody Map<String, String> requestBody, OrderDto orderDto) {
//        String impUid = requestBody.get("impUid");
//        log.info("Received impUid for validation: {}", impUid);
//        try {
//            if(paymentService.paymentValidation(impUid)){
//                log.info("Payment validation successful, creating order...");
//                return orderService.create(orderDto);
//            } else {
//                log.error("Payment validation failed for impUid: {}", impUid);
//                throw new OrdersException(ErrorCode.NOT_MATCH_ORDERS);
//            }
//        } catch (Exception e) {
//            log.error("Server error occurred", e);
//            throw new OrdersException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    //고객이 구매를 취소
//    @ApiOperation(value = "주문 취소")
//    @ApiImplicitParams(
//            @ApiImplicitParam(name = "impUid", value = "취소할 주문의 주문 번호 입력",
//                    required = true, paramType = "query", dataType = "string", defaultValue = ""))
//    @RequestMapping(method = RequestMethod.GET,value = "/cancel")
//    public BaseResponse<String> orderCancel(String impUid) throws IOException {
//        return paymentService.paymentCancel(impUid);
//    }
}
