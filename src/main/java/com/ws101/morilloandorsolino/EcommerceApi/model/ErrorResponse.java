package com.ws101.morilloandorsolino.EcommerceApi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standardized error response format returned by the API on failures.
 *
 * @author Morillo and Orsolino
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /** Timestamp of when the error occurred. */
    private LocalDateTime timestamp;

    /** HTTP status code (e.g., 404, 400, 500). */
    private int status;

    /** Short error label (e.g., "NOT_FOUND", "BAD_REQUEST"). */
    private String error;

    /** Human-readable description of the error. */
    private String message;

    /** The request URI that triggered the error. */
    private String path;
}