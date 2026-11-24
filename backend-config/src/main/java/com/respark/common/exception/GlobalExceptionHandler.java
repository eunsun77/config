package com.respark.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException ex) {
        return build(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(CustomSecurityException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomSecurityException(CustomSecurityException ex) {
        return build(ex.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getDefaultMessage() == null ? ErrorCode.INVALID_REQUEST.getMessage() : err.getDefaultMessage())
                .orElse(ErrorCode.INVALID_REQUEST.getMessage());
        return build(ErrorCode.INVALID_REQUEST, message);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingPathVariable(MissingPathVariableException ex) {
        log.warn("missing path variable: {}", ex.getVariableName());
        return build(ErrorCode.MISSING_REQUIRED_FIELD);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDTO> handleJwtException(JwtException ex) {
        ErrorCode errorCode;
        if (ex instanceof ExpiredJwtException) {
            errorCode = ErrorCode.EXPIRED_JWT;
        } else if (ex instanceof MalformedJwtException || ex instanceof SignatureException) {
            errorCode = ErrorCode.INVALID_JWT;
        } else {
            errorCode = ErrorCode.INVALID_JWT;
        }
        return build(errorCode);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpClientError(HttpClientErrorException ex) {
        log.warn("external client error: {}", ex.getMessage());
        return build(ErrorCode.EXTERNAL_API_ERROR);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponseDTO> handleHttpServerError(HttpServerErrorException ex) {
        log.error("external server error: {}", ex.getMessage());
        return build(ErrorCode.EXTERNAL_API_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleUnexpected(Exception ex) {
        log.error("unexpected error", ex);
        return build(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDTO> build(ErrorCode errorCode) {
        return build(errorCode, errorCode.getMessage());
    }

    private ResponseEntity<ErrorResponseDTO> build(ErrorCode errorCode, String message) {
        ErrorResponseDTO body = new ErrorResponseDTO(
                LocalDateTime.now(),
                errorCode.getHttpStatus(),
                errorCode.getCode(),
                message
        );
        return ResponseEntity.status(errorCode.getHttpStatus()).body(body);
    }
}
