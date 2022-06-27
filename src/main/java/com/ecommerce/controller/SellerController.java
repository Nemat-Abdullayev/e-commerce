package com.ecommerce.controller;


import com.ecommerce.dto.request.ProductRequestDTO;
import com.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = "endpoint for seller")
@RequestMapping("${app.root.url}/seller")
public class SellerController {

    private final ProductService productService;


    @PostMapping("/product")
    @ApiOperation("create product")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<?> create(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(productService.create(productRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/product")
    @ApiOperation("get products of seller")
    public ResponseEntity<?> findAllBySellerId() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    @ApiOperation("get products of seller")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PutMapping(("/product"))
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    @ApiOperation("update product")
    public ResponseEntity<?> update(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(productService.update(productRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    @ApiOperation("delete product of seller")
    public ResponseEntity<?> deleteProductBySeller(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productService.delete(id), HttpStatus.OK);
    }

}
