package com.woof.api.productCeo.controller;

//import com.woof.api.member.model.entity.Member;
import com.woof.api.productCeo.model.ProductCeo;
import com.woof.api.productCeo.model.dto.ProductCeoCreateReq;
import com.woof.api.productCeo.model.dto.ProductCeoCreateRes;
import com.woof.api.productCeo.model.dto.ProductCeoCreateResult;
import com.woof.api.productCeo.model.dto.ProductCeoUpdateReq;
import com.woof.api.productCeo.service.ProductCeoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/productCeo")
@CrossOrigin("*")
public class ProductCeoController {
    ProductCeoService productCeoService;

    public ProductCeoController(ProductCeoService productCeoService) {
        this.productCeoService = productCeoService;
    }

    @ApiOperation(value="상품 정보 등록", notes="업체 회원이 상품을 정보를 등록한다.")
    @RequestMapping(method = RequestMethod.POST, value = "/createCeo")
    public ResponseEntity createCeo(@RequestPart ProductCeoCreateReq postProductReq,
                                    @RequestPart MultipartFile[] uploadFiles) {
        ProductCeo productCeo = productCeoService.createCeo(postProductReq);

        for (MultipartFile uploadFile:uploadFiles) {
            String uploadPath = productCeoService.uploadFileCeo(uploadFile);
            productCeoService.saveFileCeo(productCeo.getIdx(), uploadPath);
        }

        ProductCeoCreateRes response = ProductCeoCreateRes.builder()
                .code(1000)
                .message("요청 성공.")
                .success(true)
                .isSuccess(true)
                .result(ProductCeoCreateResult.builder().idx(productCeo.getIdx()).build())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value="상품 목록 조회", notes="회원이 전체 상품을 조회한다.")
    @RequestMapping(method = RequestMethod.GET, value = "/listCeo")
    public ResponseEntity listCeo() {

        return ResponseEntity.ok().body(productCeoService.listCeo());
    }

    @ApiOperation(value="특정 상품 조회", notes="회원이 상품 idx를 입력하여 특정 상품을 조회한다.")
    @GetMapping("/{idx}")
    public ResponseEntity readCeo(@PathVariable Long idx) {
        return ResponseEntity.ok().body(productCeoService.readCeo(idx));
    }

    @ApiOperation(value="상품 정보 수정", notes="업체회원이 상품의 정보를 수정한다.")
    @RequestMapping(method = RequestMethod.PATCH, value = "/updateCeo")
    public ResponseEntity updateCeo(@RequestBody ProductCeoUpdateReq productCeoUpdateReq) {
        productCeoService.updateCeo(productCeoUpdateReq);

        return ResponseEntity.ok().body("수정");
    }

    @ApiOperation(value="상품 정보 삭제", notes="업체회원이 상품의 정보를 삭제한다.")
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteCeo")
    public ResponseEntity deleteCeo(@RequestParam Long idx) {
        productCeoService.deleteCeo(idx);
        return ResponseEntity.ok().body("삭제");

    }
}