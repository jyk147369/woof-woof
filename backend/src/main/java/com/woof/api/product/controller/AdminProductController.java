package com.woof.api.product.controller;

import com.woof.api.product.model.response.ProductManagerListRes;
import com.woof.api.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product")
@CrossOrigin("*")
public class AdminProductController {

    ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/check")
    public ResponseEntity managerSchool(@RequestParam Long idx) {
        productService.checkManager(idx);
        return ResponseEntity.ok().body("상품 idx" + idx + "승인 완료");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/manager/list")
    public ResponseEntity<ProductManagerListRes> adminListManager() {
        return ResponseEntity.ok().body(productService.adminListManager());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/delete")
    public ResponseEntity<String> deleteManager(@RequestParam Long idx) {
        productService.deleteManager(idx);
        return ResponseEntity.ok().body("매니저 idx" + idx + "삭제 완료");
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/school/check")
    public ResponseEntity checkSchool(@RequestParam Long idx) {
        productService.checkSchool(idx);
        return ResponseEntity.ok().body("상품 idx" + idx + "승인 완료");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/school/list")
    public ResponseEntity adminListSchool() {
        return ResponseEntity.ok().body(productService.adminListSchool());
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/school/delete")
    public ResponseEntity deleteSchool(@RequestParam Long idx) {
        productService.deleteSchool(idx);
        return ResponseEntity.ok().body("상품 idx" + idx + "삭제 완료");
    }
}

