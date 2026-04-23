package com.ws101.morilloandorsolino.EcommerceApi.exception;

/**
 * Exception thrown when an unsupported or invalid filter type
 * is provided in a filter request.
 *
 * @author Morillo and Orsolino
 */
public class InvalidFilterException extends RuntimeException {

    /**
     * Constructs an {@code InvalidFilterException} with a message
     * indicating the unsupported filter type.
     *
     * @param filterType the unsupported filter type string from the client
     */
    public InvalidFilterException(String filterType) {
        super("Invalid filter type: '" + filterType
                + "'. Supported types: name, category, price, minPrice, maxPrice.");
    }
}