package com.ws101.morilloandorsolino.EcommerceApi.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Repr;esents a product in the e-commerce catalog.
 *
 * <p>Contains all relevant product information including pricing,
 * inventory tracking, and categorization. Validation annotations
 * enforce data integrity on incoming requests.
 *
 * @author Morillo and Orsolino
 * @see com.ws101.morilloandorsolino.EcommerceApi.service.ProductService
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    /**
     * Unique identifier for the product.
     * Automatically assigned by the service layer using an atomic counter.
     * This field is ignored on POST requests.
     */
    private Long id;

    /**
     * Name of the product.
     * Required. Must be between 2 and 100 characters.
     */
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    /**
     * Detailed description of the product.
     * Required. Provides context about the product features.
     */
    @NotBlank(message = "Description is required")
    private String description;

    /**
     * Selling price of the product in Philippine Peso (PHP).
     * Required. Must be a positive number greater than zero.
     */
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;

    /**
     * Category the product belongs to (e.g., Electronics, Clothing, Kitchen).
     * Required. Used for filtering and organization.
     */
    @NotBlank(message = "Category is required")
    private String category;

    /**
     * Number of units currently available in stock.
     * Required. Must be zero or a positive integer.
     */
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity must be non-negative")
    private Integer stockQuantity;

    /**
     * Optional URL pointing to an image of the product.
     * May be {@code null} if no image is available.
     */
    private String imageUrl;
}