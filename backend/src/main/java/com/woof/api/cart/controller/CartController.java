package com.woof.api.cart.controller;




import com.woof.api.cart.model.dto.CartCreateReq;
import com.woof.api.cart.service.CartService;
import com.woof.api.common.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;



    @ApiOperation(value="즐겨찾기 추가", notes="회원이 업체나 매니저를 즐겨찾기에 추가한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/in")
    public ResponseEntity create(@RequestBody CartCreateReq cartCreateReq) {
        Response response = cartService.cartIn(cartCreateReq);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value="즐겨찾기 조회", notes="회원이 즐겨찾기 목록을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/cartlist")
    public ResponseEntity<Object> cartList(Long memberIdx) {

        return ResponseEntity.ok().body(cartService.cartList(memberIdx));
    }


    @ApiOperation(value="즐겨찾기 삭제", notes="회원이 즐겨찾기 idx를 입력하여 즐겨찾기를 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/cartremove/{idx}")
    public ResponseEntity cartRemove(/*@AuthenticationPrincipal*/@PathVariable Long idx) {

        cartService.cartRemove(idx);
        return ResponseEntity.ok().body("ok");
    }



}
