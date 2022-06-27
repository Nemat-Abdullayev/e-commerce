package com.ecommerce.service;
import com.ecommerce.dto.request.AuthRequest;
import com.ecommerce.dto.request.RefreshTokenDTO;
import org.springframework.http.ResponseEntity;

public interface AuthenticateService {

    ResponseEntity<?> generateToken(AuthRequest authRequest)
            throws Exception;

    ResponseEntity<?> refreshToken(RefreshTokenDTO refreshTokenDTO);
}
