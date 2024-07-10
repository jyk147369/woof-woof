package com.woof.api.orders.service;

import com.woof.api.common.BaseResponse;
import com.woof.api.member.model.entity.Member;
import com.woof.api.orders.model.dto.OrderDto;
import com.woof.api.orders.model.entity.CustomerInfo;
import com.woof.api.orders.model.entity.Orders;
import com.woof.api.orders.model.request.OrdersUpdateReq;
import com.woof.api.orders.model.response.OrdersListRes;
import com.woof.api.orders.model.response.OrdersReadRes;
import com.woof.api.orders.model.response.OrdersReadRes2;
import com.woof.api.orders.repository.OrderRepository;
import com.woof.api.product.model.entity.ProductSchool;
import com.woof.api.product.model.entity.ProductManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;


    @Transactional
    public BaseResponse create(OrderDto orderDto) {
        orderRepository.save(
                Orders.builder()
                        .productSchool(
                                ProductSchool.builder()
                                        .idx(orderDto.getProductCeoIdx())
//                                        .productName(orderDto.getProductName())
                                        .build())
                        .productManager(
                                ProductManager.builder()
                                        .idx(orderDto.getProductManagerIdx())
                                        .build())
                        .member(
                                Member.builder()
                                        .idx(orderDto.getMemberIdx())
                                        .build())
                        .name(orderDto.getName())
                        .phoneNumber(orderDto.getPhoneNumber())
                        .time(orderDto.getTime())
                        .orderDetails(orderDto.getOrderDetails())
                        .place(orderDto.getPlace())
                        .reservationStatus(0)
                        .build());

        return BaseResponse.successRes("Orders_001", true, "주문을 완료했습니다", orderRepository);
    }
//
    public OrdersListRes list() {
        List<Orders> result = orderRepository.findAll();
        List<OrdersReadRes> orderDtos = new ArrayList<>();

        for (Orders orders : result) {

            List<OrdersReadRes> ordersReadRes = new ArrayList<>();

            OrdersReadRes ordersReadRes1 = OrdersReadRes.builder()
                    .idx(orders.getIdx())
                    .name(orders.getName())
                    .place(orders.getPlace())
                    .time(orders.getTime())
                    .phoneNumber(orders.getPhoneNumber())
//                    .reservation_status(orders.getReservation_status())
                    .build();

            orderDtos.add(ordersReadRes1);
        }
        return OrdersListRes.builder()
                .code(1000)
                .message("조회에 성공하였습니다")
                .success(true)
                .isSuccess(true)
                .result(orderDtos)
                .build();

    }

    public OrdersReadRes2 read(Long id) {
        Optional<Orders> result = orderRepository.findById(id);
        List<OrdersReadRes2> orderDto2 = new ArrayList<>();

        if (result.isPresent()) {
            Orders orders = result.get();


            OrdersReadRes ordersReadRes = OrdersReadRes.builder()
                    .idx(orders.getIdx())
                    .name(orders.getName())
                    .phoneNumber(orders.getPhoneNumber())
                    .time(orders.getTime())
                    .place(orders.getPlace())
                    .reservationStatus(orders.getReservationStatus())
                    .build();

            return OrdersReadRes2.builder()
                    .code(1000)
                    .message("상품 불러오기에 성공하였습니다")
                    .success(true)
                    .isSuccess(true)
                    .result(ordersReadRes)
                    .build();
        } else {
            return OrdersReadRes2.builder()
                    .code(400)
                    .message("상품 불러오기를 실패했습니다")
                    .success(false)
                    .isSuccess(false)
                    .build();
        }
    }

    public BaseResponse <OrdersReadRes2> update(OrdersUpdateReq ordersUpdateReq) {
//        OrdersReadRes2를 반환하는 업데이트 메소드
        //OrderDto를 매개변수로 받아온다
        Optional<Orders> result = orderRepository.findById(ordersUpdateReq.getIdx());
        //오더 레포에서 id를 찾아 result에 저장한다

        if (result.isPresent()) {
            //만약 result에 값이 있다면
            Orders orders = result.get();
            //orders에 result를 저장한다

//            orders.setStatus(orderDto.getStatus());
            //orders의 status는 orderDto의 status를 찾아 가져온다

//            Orders orders1 = Orders.builder()
//                    .idx(ordersUpdateReq.getIdx())
//                    .time(ordersUpdateReq.getTime())
//                    .build();
            orders.setTime(ordersUpdateReq.getTime());
            orders.setOrderDetails(ordersUpdateReq.getOrderDetails());
            orders.setPlace(ordersUpdateReq.getPlace());

            Orders result1 = orderRepository.save(orders);
            //order레포에 orders를 저장한다

            OrdersReadRes2 response = OrdersReadRes2.builder()
                    .code(1000)
                    .message("요청 성공")
                    .success(true)
                    .isSuccess(true)
                    .result(OrdersReadRes.builder()
                            .idx(result1.getIdx())
                            .phoneNumber(result1.getPhoneNumber())
                            .place(result1.getPlace())
                            .time(result1.getTime())
                            .build())
                    .build();
            //주문 요청 성공시 "주문 수정 성공"을 반환한다

            return BaseResponse.successRes("Orders_2", true, "주문 정보 수정에 성공하였습니다", response);

        } else {
            OrdersReadRes2 responseFail = OrdersReadRes2.builder()
                    .code(400)
                    .message("요청 실패.")
                    .success(false)
                    .isSuccess(false)
                    .build();

            return BaseResponse.successRes("Orders_003", false, "주문 정보 수정 실패", responseFail);
        }//주문 실패시 "주문 실패"를 반환한다

    }

    public BaseResponse<Void> delete(Long idx) {
        //  한 번에 삭제
        orderRepository.deleteAllByOrdersIdx(idx);
        //2=삭제
        OrderDto orderDto = orderRepository.findByIdx(idx).get();
        orderDto.setStatus(2);


        return BaseResponse.successRes("Orders_004", true, "주문 삭제 성공.", null);
    }

    public List<CustomerInfo> getOrdersByMemberIdx(Long memberIdx) {
        List<Orders> byMemberIdx = orderRepository.findByMemberIdx(memberIdx);
        List<CustomerInfo> dkRes = new ArrayList<>();
        for (Orders order : byMemberIdx) {
            CustomerInfo res = CustomerInfo.builder()
                    .idx(order.getIdx())
                    .name(order.getName())
                    .phNum(order.getPhoneNumber())
                    .orderDetails(order.getOrderDetails())
                    .place(order.getPlace())
                    .build();
            dkRes.add(res);
        }
        return dkRes;
    }

}
