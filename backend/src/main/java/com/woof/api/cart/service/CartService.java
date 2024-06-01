package com.woof.api.cart.service;


import com.woof.api.cart.model.Cart;
import com.woof.api.cart.model.dto.*;
import com.woof.api.cart.repository.CartRepository;
import com.woof.api.cart.repository.querydsl.CartRepositoryCustomImpl;
import com.woof.api.common.Response;
import com.woof.api.member.model.entity.Member;
import com.woof.api.member.repository.MemberRepository;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productCeo.repository.ProductCeoRepository;
import com.woof.api.productManager.model.ProductManager;
import com.woof.api.productManager.repository.ProductManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartRepositoryCustomImpl cartRepositoryCustomImpl;



    // TODO: 에러코드 수정
    public Response cartIn(CartCreateReq request) {
        // repo .save();
        Cart cart = cartRepository.save(Cart.builder()
                .member(Member.builder()
                        .idx(request.getMemberIdx())
                        .build())
                .productCeo(ProductCeo.builder()
                        .idx(request.getProductCeoIdx())
                        .build())
                .productManager(ProductManager.builder()
                        .idx(request.getProductManagerIdx())
                        .build())
                .build());

        Response<Object> result = Response.builder()
                .resultCode("등록이 성공됐습니다.")
                .result(CartCreateRes.builder()
                        .cartIdx(cart.getIdx())
                        .memberIdx(cart.getMember().getIdx())
                        .productCeoIdx(cart.getProductCeo().getIdx())
                        .productManagerIdx(cart.getProductManager().getIdx())
                        .build())
                .build();
        return result;


//
//            return Response.success("추가 성공");
//        } else {
//            return Response.error("2222");
//        }
    }

//     즐겨찾기 목록 조회
    public List<CartListRes> cartList(Long memberIdx) {
        List<Cart> result = cartRepositoryCustomImpl.findList(memberIdx);
        List<CartListRes> list = new ArrayList<>();

        for (Cart cart : result) {
            CartListRes cartListRes = CartListRes.builder()
                    .cartIdx(cart.getIdx())
                    .memberIdx(cart.getMember().getIdx())
                    .productCeoIdx(cart.getProductCeo().getIdx())
                    .productCeoStoreName(cart.getProductCeo().getStoreName())
                    .productManagerIdx(cart.getProductManager().getIdx())
                    .productMangerName(cart.getProductManager().getManagerName())
                    .build();
            list.add(cartListRes);
        }
        return list;

    }


    // @Transactional
    public void cartRemove(Long idx) {
        cartRepository.deleteById(idx);
    }

}






