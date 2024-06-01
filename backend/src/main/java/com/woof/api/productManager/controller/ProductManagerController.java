package com.woof.api.productManager.controller;

import com.woof.api.productManager.model.ProductManager;
import com.woof.api.productManager.model.dto.ProductManagerCreateReq;
import com.woof.api.productManager.model.dto.ProductManagerCreateRes;
import com.woof.api.productManager.model.dto.ProductManagerCreateResult;
import com.woof.api.productManager.model.dto.ProductManagerUpdateReq;
import com.woof.api.productManager.service.ProductManagerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/productManager")
@CrossOrigin("*")
public class ProductManagerController {
    ProductManagerService productManagerService;

    public ProductManagerController(ProductManagerService productManagerService) {
        this.productManagerService = productManagerService;
    }

    @ApiOperation(value="매니저회원 정보 등록", notes="매니저회원이 정보를 입력하여 등록한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/createManager")
    public ResponseEntity createManager(
                                 @RequestPart ProductManagerCreateReq postProductReq,
                                 @RequestPart MultipartFile[] uploadFiles) {
        ProductManager productManager = productManagerService.createManager(postProductReq);

        for (MultipartFile uploadFile:uploadFiles) {
            String uploadPath = productManagerService.uploadFileManager(uploadFile);
            productManagerService.saveFileManager(productManager.getIdx(), uploadPath);
        }

        ProductManagerCreateRes response = ProductManagerCreateRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(ProductManagerCreateResult.builder().idx(productManager.getIdx()).build())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value="매니저회원 리스트 조회", notes="회원이 매니저회원 전체 목록을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/listManager")
    public ResponseEntity listManager() {

        return ResponseEntity.ok().body(productManagerService.listManager());
    }

    @ApiOperation(value="특정 매니저회원 조회", notes="회원이 특정 매니저의 idx를 입력하여 특정 매니저회원을 조회한다.")
    @GetMapping("/{idx}")
    public ResponseEntity readManager(@PathVariable Long idx) {
        return ResponseEntity.ok().body(productManagerService.readManager(idx));

    }

    @ApiOperation(value="매니저회원 정보 수정", notes="매니저회원이 정보를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/updateManager")
    public ResponseEntity updateManager(@RequestBody ProductManagerUpdateReq productManagerUpdateReq) {
        productManagerService.updateManager(productManagerUpdateReq);

        return ResponseEntity.ok().body("수정");
    }

    @ApiOperation(value="매니저회원 정보 삭제", notes="매니저회원이 정보를 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteManager")
    public ResponseEntity deleteManager(@RequestParam Long idx) {
        productManagerService.deleteManager(idx);
        return ResponseEntity.ok().body("삭제");

    }
}
