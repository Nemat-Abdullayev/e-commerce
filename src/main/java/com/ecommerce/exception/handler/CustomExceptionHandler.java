package com.ecommerce.exception.handler;
import com.ecommerce.exception.custom.*;
import com.ecommerce.exception.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        log.error("Entity Not Found Exception: {}", ex.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, uri);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    private ResponseEntity<?> handleConstraintViolationException(SQLIntegrityConstraintViolationException e, WebRequest request) {
        log.error("Constraint violet exception {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, uri);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<?> handleConstraintViolationException(DataIntegrityViolationException e, WebRequest request) {
        log.error("Data Integrity Violation Exception violet exception {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, uri);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<?> handleException(MethodArgumentNotValidException e,WebRequest request){
        log.error("Argument not valid exception {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, uri);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, WebRequest request) {
        log.error("Exception has throw {}", ex.getMessage());
        ex.printStackTrace();
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, uri);
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    public ResponseEntity<?> handleExpiredRefreshTokenException(ExpiredRefreshTokenException ex,
                                                                WebRequest request) {
        log.error("Expired Refresh Token Exception: {}", ex.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, uri);
        apiError.setMessage("Expired refresh token");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<?> handleInvalidRefreshTokenException(InvalidRefreshTokenException ex,
                                                                WebRequest request) {
        log.error("Invalid Refresh Token Exception: {}", ex.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, uri);
        apiError.setMessage("invalid refresh token");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<?> handleAuthenticationException(AuthenticationException e,WebRequest request){
        log.error("Authentication exception {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, uri);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<?> handleAuthenticationException(AccessDeniedException e,WebRequest request){
        log.error("Access Denied Exception : {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, uri);
        apiError.setMessage("you haven't permission for this operation");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(PasswordMatchException.class)
    private ResponseEntity<?> handlePasswordMatchException(PasswordMatchException e,WebRequest request){
        log.error("Password Match Exception : {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, uri);
        apiError.setMessage("old password is incorrect");
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e,WebRequest request){
        log.error("Username Match Exception : {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, uri);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(SystemException.class)
    private ResponseEntity<?> handleSystemException(SystemException e,WebRequest request){
        log.error("System Exception : {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, uri);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(SellerIsNotOfTheProductException.class)
    private ResponseEntity<?> handleSellerIsNotOfTheProductException(SellerIsNotOfTheProductException e,WebRequest request){
        log.error("Seller Is Not Of The Product Exception : {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, uri);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(NotEnoughDepositException.class)
    private ResponseEntity<?> handleNotEnoughDepositException(NotEnoughDepositException e,WebRequest request){
        log.error("Not Enough Deposit Exception : {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, uri);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(NotAmountAvailableException.class)
    private ResponseEntity<?> handleNotAmountAvailableException(NotAmountAvailableException e,WebRequest request){
        log.error("Not Amount Available Exception : {} ", e.getMessage());
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, uri);
        apiError.setMessage(e.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
