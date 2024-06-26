package com.woof.api.product.controller;

import com.woof.api.common.BaseResponse;
import com.woof.api.product.model.response.ProductManagerListRes;
import com.woof.api.product.model.response.ProductManagerReadRes;
import com.woof.api.product.model.response.ProductSchoolReadRes;
import com.woof.api.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
@CrossOrigin("*")
public class AdminProductController {

    ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/check")
    public ResponseEntity<BaseResponse<Void>> managerSchool(@RequestParam Long idx) {
        BaseResponse<Void> response = productService.checkManager(idx);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/manager/list")
    public ResponseEntity<BaseResponse<List<ProductManagerReadRes>>> adminListManager() {
        BaseResponse<List<ProductManagerReadRes>> response = productService.adminListManager();
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/manager/delete")
    public ResponseEntity<BaseResponse<Void>> deleteManager(@RequestParam Long idx) {
        BaseResponse<Void> response = productService.deleteManager(idx);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/school/check")
    public ResponseEntity<BaseResponse<Void>> checkSchool(@RequestParam Long idx) {
        BaseResponse<Void> response = productService.checkSchool(idx);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/school/list")
    public ResponseEntity<BaseResponse<List<ProductSchoolReadRes>>> adminListSchool() {
        BaseResponse<List<ProductSchoolReadRes>> response = productService.adminListSchool();
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/school/delete")
    public ResponseEntity<BaseResponse<Void>> deleteSchool(@RequestParam Long idx) {
        BaseResponse<Void> response = productService.deleteSchool(idx);
        return ResponseEntity.ok().body(response);
    }
}

