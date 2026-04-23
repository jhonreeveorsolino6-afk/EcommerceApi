package com.ws101.morilloandorsolino.EcommerceApi.exception;

import com.ws101.morilloandorsolino.EcommerceApi.model.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Global exception handler for the EcommerceApi application.
 *
 * <p>Intercepts exceptions thrown by any controller and converts them
 * into standardized {@link ErrorResponse} objects with the correct
 * HTTP status codes.
 *
 * @author Morillo and Orsolino
 * @see ErrorResponse
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ProductNotFoundException} when a product ID does not exist.
     *
     * @param ex      the exception thrown by the service layer
     * @param request the HTTP request that triggered the exception
     * @return {@code 404 Not Found} with a descriptive error response
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(
            ProductNotFoundException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), request);
    }

    /**
     * Handles {@link InvalidFilterException} when an unsupported filter
     * type is supplied by the client.
     *
     * @param ex      the exception thrown by the service layer
     * @param request the HTTP request that triggered the exception
     * @return {@code 400 Bad Request} with a descriptive error response
     */
    @ExceptionHandler(InvalidFilterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFilter(
            InvalidFilterException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "BAD_REQUEST", ex.getMessage(), request);
    }

    /**
     * Handles validation errors from {@code @Valid} on request bodies.
     *
     * @param ex      the validation exception with field error details
     * @param request the HTTP request that triggered the exception
     * @return {@code 400 Bad Request} listing all validation failures
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        String messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return buildError(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", messages, request);
    }

    /**
     * Handles {@link IllegalArgumentException} for invalid method arguments.
     *
     * @param ex      the exception thrown
     * @param request the HTTP request that triggered the exception
     * @return {@code 400 Bad Request} with error details
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, "BAD_REQUEST", ex.getMessage(), request);
    }

    /**
     * Fallback handler for any unhandled exception.
     *
     * @param ex      the unexpected exception
     * @param request the HTTP request that triggered it
     * @return {@code 500 Internal Server Error}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred: " + ex.getMessage(), request);
    }

    /**
     * Builds a {@link ResponseEntity} containing a populated {@link ErrorResponse}.
     *
     * @param status  the HTTP status to return
     * @param error   short error label
     * @param message descriptive error message
     * @param request the originating HTTP request
     * @return a complete error response entity
     */
    private ResponseEntity<ErrorResponse> buildError(
            HttpStatus status, String error, String message, HttpServletRequest request) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }
}