package com.woof.api.product.controller;

import com.woof.api.common.BaseResponse;
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
    public ResponseEntity<BaseResponse<ProductManagerCreateResult>> createManager(
            @RequestPart ProductManagerCreateReq productManagerCreateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

        BaseResponse<ProductManagerCreateResult> baseResponse = productService.createManager(productManagerCreateReq);
        ProductManagerCreateResult productManagerCreateResult = baseResponse.getResult();

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = productService.uploadFile(uploadFile, productManagerCreateResult.getIdx());
                productService.saveFile(productManagerCreateResult.getIdx(), uploadPath);
            }
        }
        return ResponseEntity.ok().body(baseResponse);
    }

    //    @ApiOperation(value="매니저 목록 조회", notes="회원이 전체 매니저를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/manager/list")
    public ResponseEntity<BaseResponse<List<ProductManagerReadRes>>> listManager() {
        BaseResponse<List<ProductManagerReadRes>> response = productService.listManager();
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="특정 매니저 조회", notes="회원이 매니저 idx를 입력하여 특정 매니저를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value ="/manager/read/{idx}")
    public ResponseEntity<BaseResponse<ProductManagerReadRes>> readManager(@PathVariable Long idx) {
        BaseResponse<ProductManagerReadRes> response = productService.readManager(idx);
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="매니저 정보 수정", notes="매니저회원이 매니저의 정보를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/update")
    public ResponseEntity<BaseResponse<Void>> updateManager(
            @RequestPart ProductManagerUpdateReq productManagerUpdateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

        BaseResponse<Void> response = productService.updateManager(productManagerUpdateReq);

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = productService.uploadFile(uploadFile, productManagerUpdateReq.getIdx());
                productService.saveFile(productManagerUpdateReq.getIdx(), uploadPath);
            }
        }
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="매니저 정보 삭제", notes="매니저회원이 매니저의 정보를 삭제한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/delete")
    public ResponseEntity<BaseResponse<Void>> deleteManager(@RequestParam Long idx) {
        BaseResponse<Void> response = productService.deleteManager(idx);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/manager/files/{productManagerIdx}")
    public ResponseEntity<List<ProductFileDto>> listFilesByProductManagerIdx(@PathVariable Long productManagerIdx) {
        List<ProductFileDto> files = productService.listFilesByProductManagerIdx(productManagerIdx);
        return ResponseEntity.ok().body(files);
    }

    // ----------------------------------------------------------------------------------------------- //

    @RequestMapping(method = RequestMethod.POST, value = "/school/create")
    public ResponseEntity<BaseResponse<ProductSchoolCreateResult>> createSchool(
            @RequestPart ProductSchoolCreateReq productSchoolCreateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

        BaseResponse<ProductSchoolCreateResult> baseResponse = productService.createSchool(productSchoolCreateReq);
        ProductSchoolCreateResult productSchoolCreateResult = baseResponse.getResult();

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = productService.uploadFile(uploadFile, productSchoolCreateResult.getIdx());
                productService.saveFile(productSchoolCreateResult.getIdx(), uploadPath);
            }
        }
        return ResponseEntity.ok().body(baseResponse);
    }

    //    @ApiOperation(value="상품 목록 조회", notes="회원이 전체 상품을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/school/list")
    public ResponseEntity<BaseResponse<List<ProductSchoolReadRes>>> listSchool() {
        BaseResponse<List<ProductSchoolReadRes>> response = productService.listSchool();
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="특정 상품 조회", notes="회원이 상품 idx를 입력하여 특정 상품을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value ="/school/read/{idx}")
    public ResponseEntity<BaseResponse<ProductSchoolReadRes>> readSchool(@PathVariable Long idx) {
        BaseResponse<ProductSchoolReadRes> response = productService.readSchool(idx);
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="상품 정보 수정", notes="업체회원이 상품의 정보를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/school/update")
    public ResponseEntity<BaseResponse<Void>> updateSchool(
            @RequestPart ProductSchoolUpdateReq productSchoolUpdateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

        BaseResponse<Void> response = productService.updateSchool(productSchoolUpdateReq);

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = productService.uploadFile(uploadFile, productSchoolUpdateReq.getIdx());
                productService.saveFile(productSchoolUpdateReq.getIdx(), uploadPath);
            }
        }
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="상품 정보 삭제", notes="업체회원이 상품의 정보를 삭제한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/school/delete")
    public ResponseEntity<BaseResponse<Void>>  deleteSchool(@RequestParam Long idx) {
        BaseResponse<Void> response = productService.deleteSchool(idx);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/school/files/{productSchoolIdx}")
    public ResponseEntity<List<ProductFileDto>> listFilesByProductSchoolIdx(@PathVariable Long productSchoolIdx) {
        List<ProductFileDto> files = productService.listFilesByProductSchoolIdx(productSchoolIdx);
        return ResponseEntity.ok().body(files);
    }

    // ----------------------------------------------------------------------------------------------- //

    @RequestMapping(method = RequestMethod.POST, value = "/uploadFile")
    private void uploadFile(MultipartFile uploadFile, Long productIdx) {
        String uploadPath = productService.uploadFile(uploadFile, productIdx);
        productService.saveFile(productIdx, uploadPath);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam Long fileId) {
        productService.deleteFile(fileId);
        return ResponseEntity.ok("파일 id " + fileId + " 삭제 완료");
    }
}