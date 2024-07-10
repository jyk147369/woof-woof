package com.woof.api.product.controller;

import com.woof.api.common.BaseResponse;
import com.woof.api.product.model.entity.ProductImage;
import com.woof.api.product.model.response.*;
import com.woof.api.product.model.request.ProductManagerCreateReq;
import com.woof.api.product.model.request.ProductManagerUpdateReq;
import com.woof.api.product.model.request.ProductSchoolCreateReq;
import com.woof.api.product.model.request.ProductSchoolUpdateReq;
import com.woof.api.product.service.ProductService;
//import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //    @ApiOperation(value="매니저 정보 등록", notes="매니저회원이 매니저 정보를 등록한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/manager/create")
    public ResponseEntity<Object> createManager(
            @RequestPart ProductManagerCreateReq productManagerCreateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

        BaseResponse<ProductManagerCreateResult> baseResponse = productService.createManager(productManagerCreateReq);
        ProductManagerCreateResult productManagerCreateResult = baseResponse.getResult();

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                ProductImage productImage = productService.uploadFile(uploadFile);
                productService.saveFileM(productManagerCreateResult.getIdx(), productImage);
            }
        }
        return ResponseEntity.ok().body(baseResponse);
    }

    //    @ApiOperation(value="매니저 목록 조회", notes="회원이 전체 매니저를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/manager/list")
    public ResponseEntity<Object> listManager() {
        return ResponseEntity.ok().body(productService.listManager());
    }

    //    @ApiOperation(value="특정 매니저 조회", notes="회원이 매니저 idx를 입력하여 특정 매니저를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/manager/read/{idx}")
    public ResponseEntity<Object> readManager(@PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.readManager(idx));
    }

    //    @ApiOperation(value="매니저 정보 수정", notes="매니저회원이 매니저의 정보를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/update")
    public ResponseEntity<Object> updateManager(@RequestBody ProductManagerUpdateReq productManagerUpdateReq) {
        return ResponseEntity.ok().body(productService.updateManager(productManagerUpdateReq));
    }

    //    @ApiOperation(value="매니저 정보 삭제", notes="매니저회원이 매니저의 정보를 삭제한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/delete")
    public ResponseEntity<Object> deleteManager(@RequestParam Long idx) {
        return ResponseEntity.ok().body(productService.deleteManager(idx));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/manager/files")
    public ResponseEntity<Object> listFilesByProductManagerIdx(@RequestParam Long productManagerIdx) {
        return ResponseEntity.ok().body(productService.listFilesByProductManagerIdx(productManagerIdx));
    }

    // ----------------------------------------------------------------------------------------------- //

    @RequestMapping(method = RequestMethod.POST, value = "/school/create")
    public ResponseEntity<Object> createSchool(
            @RequestPart ProductSchoolCreateReq productSchoolCreateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

        BaseResponse<ProductSchoolCreateResult> baseResponse = productService.createSchool(productSchoolCreateReq);
        ProductSchoolCreateResult productSchoolCreateResult = baseResponse.getResult();

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                ProductImage productImage = productService.uploadFile(uploadFile);
                productService.saveFileS(productSchoolCreateResult.getIdx(), productImage);
            }
        }
        return ResponseEntity.ok().body(baseResponse);
    }

    //    @ApiOperation(value="상품 목록 조회", notes="회원이 전체 상품을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/school/list")
    public ResponseEntity<Object> listSchool() {
        return ResponseEntity.ok().body(productService.listSchool());
    }

    //    @ApiOperation(value="특정 상품 조회", notes="회원이 상품 idx를 입력하여 특정 상품을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/school/read/{idx}")
    public ResponseEntity<Object> readSchool(@PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.readSchool(idx));
    }

    //    @ApiOperation(value="상품 정보 수정", notes="업체회원이 상품의 정보를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/school/update")
    public ResponseEntity<Object> updateSchool(@RequestBody ProductSchoolUpdateReq productSchoolUpdateReq) {
        return ResponseEntity.ok().body(productService.updateSchool(productSchoolUpdateReq));
    }

    //    @ApiOperation(value="상품 정보 삭제", notes="업체회원이 상품의 정보를 삭제한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/school/delete")
    public ResponseEntity<Object> deleteSchool(@RequestParam Long idx) {
        return ResponseEntity.ok().body(productService.deleteSchool(idx));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/school/files")
    public ResponseEntity<Object> listFilesByProductSchoolIdx(@RequestParam Long productSchoolIdx) {
        return ResponseEntity.ok().body(productService.listFilesByProductSchoolIdx(productSchoolIdx));
    }

    // ----------------------------------------------------------------------------------------------- //

    @RequestMapping(method = RequestMethod.POST, value = "/uploadFileM")
    private ResponseEntity<String> uploadFileM(MultipartFile uploadFile, Long productManagerIdx) {
        ProductImage productImage = productService.uploadFile(uploadFile);
        productService.saveFileM(productManagerIdx, productImage);
        return ResponseEntity.ok("ProductManager 사진이 성공적으로 업로드되었습니다.");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadFileS")
    private ResponseEntity<String> uploadFileS(MultipartFile uploadFile, Long productSchoolIdx) {
        ProductImage productImage = productService.uploadFile(uploadFile);
        productService.saveFileS(productSchoolIdx, productImage);
        return ResponseEntity.ok("ProductSchool 사진이 성공적으로 업로드되었습니다.");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam Long fileId) {
        productService.deleteFile(fileId);
        return ResponseEntity.ok("파일 idx " + fileId + " 삭제 완료");
    }
}