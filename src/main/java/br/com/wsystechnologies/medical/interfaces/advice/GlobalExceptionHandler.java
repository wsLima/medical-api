package br.com.wsystechnologies.medical.interfaces.advice;

import br.com.wsystechnologies.medical.exceptions.BusinessException;
import br.com.wsystechnologies.medical.exceptions.ForbiddenException;
import br.com.wsystechnologies.medical.exceptions.NotFoundException;
import br.com.wsystechnologies.medical.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorResponse build(HttpStatus status, String message, HttpServletRequest req) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                req.getRequestURI(),
                MDC.get("requestId")
        );
    }

    // ============================
    // 404 - Not Found
    // ============================
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        log.warn("NotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(build(HttpStatus.NOT_FOUND, ex.getMessage(), req));
    }

    // ============================
    // 400 - Business Rules
    // ============================
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex, HttpServletRequest req) {
        log.warn("BusinessException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST, ex.getMessage(), req));
    }

    // ============================
    // 401 - Unauthorized
    // ============================
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex, HttpServletRequest req) {
        log.warn("UnauthorizedException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(build(HttpStatus.UNAUTHORIZED, ex.getMessage(), req));
    }

    // ============================
    // 403 - Forbidden
    // ============================
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException ex, HttpServletRequest req) {
        log.warn("ForbiddenException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(build(HttpStatus.FORBIDDEN, ex.getMessage(), req));
    }

    // ============================
    // 400 - Validation errors
    // ============================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");

        log.warn("Validation error: {}", msg);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST, msg, req));
    }

    // ============================
    // 500 - Unexpected errors
    // ============================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest req) {
        log.error("Unexpected error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", req));
    }

    // ============================
    // 401 - Bad Credentials
    // ============================
    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            org.springframework.security.authentication.BadCredentialsException ex,
            HttpServletRequest req
    ) {
        log.warn("Bad credentials: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(build(HttpStatus.UNAUTHORIZED, "Invalid email or password", req));
    }
}
