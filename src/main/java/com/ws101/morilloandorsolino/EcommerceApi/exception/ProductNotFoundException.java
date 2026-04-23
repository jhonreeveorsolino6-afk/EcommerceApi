package com.ws101.morilloandorsolino.EcommerceApi.exception;

/**
 * Exception thrown when a product with the requested ID
 * cannot be found in the in-memory product list.
 *
 * @author Morillo and Orsolino
 */
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructs a {@code ProductNotFoundException} with a message
     * indicating which product ID was not found.
     *
     * @param id the ID of the product that was not found
     */
    public ProductNotFoundException(Long id) {
        super("Product with ID " + id + " was not found.");
    }
}
