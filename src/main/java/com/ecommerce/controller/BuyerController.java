package com.ecommerce.controller;


import com.ecommerce.dto.request.BuyProductRequestDTO;
import com.ecommerce.dto.request.DepositRequestDTO;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
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
@Api(tags = "endpoint for buyer")
@PreAuthorize("hasAnyRole('ROLE_BUYER')")
@RequestMapping("${app.root.url}/buyer")
public class BuyerController {

    private final UserService userService;
    private final ProductService productService;


    @PostMapping("/deposit")
    @ApiOperation("deposit for buyer")
    public ResponseEntity<?> deposit(@Valid @RequestBody DepositRequestDTO depositRequestDTO) {
        return new ResponseEntity<>(userService.addDeposit(depositRequestDTO), HttpStatus.OK);
    }

    @PostMapping("/product/buy")
    @ApiOperation("buy product if role is seller")
    public ResponseEntity<?> buyProduct(@RequestBody BuyProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(productService.buyProduct(productRequestDTO), HttpStatus.OK);
    }

    @PatchMapping("/deposit/reset")
    @ApiOperation("buyer reset own deposit")
    public ResponseEntity<?> resetDeposit() {
        return new ResponseEntity<>(userService.resetDeposit(), HttpStatus.OK);
    }
}
