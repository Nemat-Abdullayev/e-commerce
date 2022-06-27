package com.ecommerce.controller;
import com.ecommerce.dto.request.AuthRequest;
import com.ecommerce.dto.request.RefreshTokenDTO;
import com.ecommerce.service.AuthenticateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Api(tags = "endpoint for authenticate")
@RequestMapping("${app.root.url}/auth")
public class AuthenticationRestController {

    private final AuthenticateService authenticateService;

    @ApiOperation("create authenticate token for user")
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @Valid @RequestBody AuthRequest authRequest) throws Exception {
        return new ResponseEntity<>(authenticateService.generateToken(authRequest), HttpStatus.OK);
    }


    @ApiOperation("create authenticate token by refresh token")
    @PostMapping("/refresh/token")
    public ResponseEntity<?> refreshToken(
            @Valid @RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {
        return new ResponseEntity<>(authenticateService.refreshToken(refreshTokenDTO),HttpStatus.OK);
    }
}
