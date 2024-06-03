package com.woof.api.product.controller;

import com.woof.api.product.model.dto.ProductFileDto;
import com.woof.api.product.model.dto.manager.*;
import com.woof.api.product.model.dto.school.ProductSchoolCreateReq;
import com.woof.api.product.model.dto.school.ProductSchoolCreateRes;
import com.woof.api.product.model.dto.school.ProductSchoolCreateResult;
import com.woof.api.product.model.dto.school.ProductSchoolUpdateReq;
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

    // 추후 전부 BaseResEntity로 변경

//    @ApiOperation(value="매니저 정보 등록", notes="매니저회원이 매니저 정보를 등록한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/manager/create")
    public ResponseEntity<ProductManagerCreateRes> createManager(
            @RequestPart ProductManagerCreateReq productManagerCreateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {
        ProductManagerCreateResult productManagerCreateResult = productService.createManager(productManagerCreateReq);

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = productService.uploadFile(uploadFile, productManagerCreateResult.getIdx());
                productService.saveFile(productManagerCreateResult.getIdx(), uploadPath);
            }
        }

        ProductManagerCreateRes response = ProductManagerCreateRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result("매니저 idx : " + productManagerCreateResult.getIdx())
                .build();
        return ResponseEntity.ok().body(response);
    }

//    @ApiOperation(value="매니저 목록 조회", notes="회원이 전체 매니저를 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/manager/list")
    public ResponseEntity<ProductManagerListRes> listManager() {
        return ResponseEntity.ok().body(productService.listManager());
    }

//    @ApiOperation(value="특정 매니저 조회", notes="회원이 매니저 idx를 입력하여 특정 매니저를 조회한다.")
    @GetMapping("/manager/read/{idx}")
    public ResponseEntity<ProductManagerReadRes> readManager(@PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.readManager(idx));
    }

//    @ApiOperation(value="매니저 정보 수정", notes="매니저회원이 매니저의 정보를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/update")
    public ResponseEntity<String> updateManager(
            @RequestPart ProductManagerUpdateReq productManagerUpdateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

        productService.updateManager(productManagerUpdateReq);

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = productService.uploadFile(uploadFile, productManagerUpdateReq.getIdx());
                productService.saveFile(productManagerUpdateReq.getIdx(), uploadPath);
            }
        }

        return ResponseEntity.ok("수정 완료");
    }

//    @ApiOperation(value="매니저 정보 삭제", notes="매니저회원이 매니저의 정보를 삭제한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/delete")
    public ResponseEntity<String> deleteManager(@RequestParam Long idx) {
        productService.deleteManager(idx);
        return ResponseEntity.ok().body("매니저 idx" + idx + "삭제 완료");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/manager/files/{productManagerIdx}")
    public ResponseEntity<List<ProductFileDto>> listFilesByProductManagerIdx(@PathVariable Long productManagerIdx) {
        List<ProductFileDto> files = productService.listFilesByProductManagerIdx(productManagerIdx);
        return ResponseEntity.ok().body(files);
    }

    // ----------------------------------------------------------------------------------------------- //

    //    @ApiOperation(value="상품 정보 등록", notes="업체 회원이 상품을 정보를 등록한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/school/create")
    public ResponseEntity createSchool(@RequestPart ProductSchoolCreateReq productSchoolCreateReq,
                                       @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {
        ProductSchoolCreateResult productSchoolCreateResult = productService.createSchool(productSchoolCreateReq);

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = productService.uploadFile(uploadFile, productSchoolCreateResult.getIdx());
                productService.saveFile(productSchoolCreateResult.getIdx(), uploadPath);
            }
        }

        ProductSchoolCreateRes response = ProductSchoolCreateRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result("상품 idx : " + productSchoolCreateResult.getIdx())
                .build();
        return ResponseEntity.ok().body(response);
    }

    //    @ApiOperation(value="상품 목록 조회", notes="회원이 전체 상품을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/school/list")
    public ResponseEntity listSchool() {
        return ResponseEntity.ok().body(productService.listSchool());
    }

    //    @ApiOperation(value="특정 상품 조회", notes="회원이 상품 idx를 입력하여 특정 상품을 조회한다.")
    @GetMapping("/school/read/{idx}")
    public ResponseEntity readSchool(@PathVariable Long idx) {
        return ResponseEntity.ok().body(productService.readSchool(idx));
    }

    //    @ApiOperation(value="상품 정보 수정", notes="업체회원이 상품의 정보를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/school/update")
    public ResponseEntity<String> updateSchool(
            @RequestPart ProductSchoolUpdateReq productSchoolUpdateReq,
            @RequestPart(name = "uploadFiles", required = false) MultipartFile[] uploadFiles) {

        productService.updateSchool(productSchoolUpdateReq);

        if (uploadFiles != null) {
            for (MultipartFile uploadFile : uploadFiles) {
                String uploadPath = productService.uploadFile(uploadFile, productSchoolUpdateReq.getIdx());
                productService.saveFile(productSchoolUpdateReq.getIdx(), uploadPath);
            }
        }
        return ResponseEntity.ok("수정 완료");
    }

    //    @ApiOperation(value="상품 정보 삭제", notes="업체회원이 상품의 정보를 삭제한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/school/delete")
    public ResponseEntity deleteSchool(@RequestParam Long idx) {
        productService.deleteSchool(idx);
        return ResponseEntity.ok().body("상품 idx" + idx + "삭제 완료");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/school/files/{productSchoolIdx}")
    public ResponseEntity<List<ProductFileDto>> listFilesByProductSchoolId(@PathVariable Long productSchoolIdx) {
        List<ProductFileDto> files = productService.listFilesByProductSchoolIdx(productSchoolIdx);
        return ResponseEntity.ok().body(files);
    }

    // ----------------------------------------------------------------------------------------------- //

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteFile")
    public ResponseEntity<String> deleteFile(@RequestParam Long fileId) {
        productService.deleteFile(fileId);
        return ResponseEntity.ok("파일 id " + fileId + " 삭제 완료");
    }
}