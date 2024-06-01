package com.woof.api.orders.controller;


import com.woof.api.orders.model.Orders;
import com.woof.api.orders.model.dto.*;
import com.woof.api.orders.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/orders")
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;


    @ApiOperation(value="예약 작성", notes="회원이 예약 정보를 입력하여 예약서를 생성한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public ResponseEntity create(@RequestBody OrderDto orderDto){
        orderService.create(orderDto);

        return ResponseEntity.ok().body("예약에 성공하였습니다");
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
    public ResponseEntity<OrdersReadRes2> update(@RequestBody OrdersUpdateReq ordersUpdateReq) {
        //ResponseEntity 반환, orderDto를 매개변수로 받아옴
        OrdersReadRes2 result = orderService.update(ordersUpdateReq);
        //orderService의 업데이트 메소드에 orderDto를 받아옴

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value="예약 삭제", notes="회원이 예약서를 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete")
    public ResponseEntity delete(Long idx){
        orderService.delete(idx);

        return ResponseEntity.ok().body("삭제를 성공했습니다.");
    }

    @ApiOperation(value="회원 예약목록 조회", notes="회원이 예약한 목록을 회원 idx를 입력하여 조회한다.")
    @GetMapping("/{memberIdx}")
    public ResponseEntity getOrders(@PathVariable Long memberIdx) {
        List<Dk> orders = orderService.getOrdersByMemberIdx(memberIdx);
        return ResponseEntity.ok().body(orders);
    }



}
