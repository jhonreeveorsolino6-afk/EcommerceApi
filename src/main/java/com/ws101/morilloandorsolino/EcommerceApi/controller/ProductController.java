package com.ws101.morilloandorsolino.EcommerceApi.controller;

public class ProductController package com.ws101.morilloandorsolino.EcommerceApi.controller;

import com.ws101.morilloandorsolino.EcommerceApi.model.Product;
import com.ws101.morilloandorsolino.EcommerceApi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing products in the e-commerce catalog.
 *
 * <p>All endpoints are mapped under the base path {@code /api/v1/products}.
 * This layer handles only HTTP concerns: routing, input validation, and
 * returning proper status codes. All business logic is delegated to
 * {@link ProductService}.
 *
 * @author Morillo and Orsolino
 * @see ProductService
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructs the controller and injects the product service.
     *
     * @param productService the service handling all product business logic
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves all products in the catalog.
     * HTTP Method: GET /api/v1/products
     *
     * @return {@code 200 OK} with a JSON array of all products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Retrieves a single product by its unique ID.
     * HTTP Method: GET /api/v1/products/{id}
     *
     * @param id the product ID extracted from the URL path
     * @return {@code 200 OK} with the product JSON;
     *         {@code 404 Not Found} if the ID does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Filters products by a given type and value.
     * HTTP Method: GET /api/v1/products/filter?filterType={type}&filterValue={value}
     *
     * @param filterType  the attribute to filter by
     * @param filterValue the value to match against
     * @return {@code 200 OK} with matching products;
     *         {@code 400 Bad Request} if the filter type is unsupported
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam String filterType,
            @RequestParam String filterValue) {
        return ResponseEntity.ok(productService.filterProducts(filterType, filterValue));
    }

    /**
     * Creates a new product and adds it to the catalog.
     * HTTP Method: POST /api/v1/products
     *
     * @param product the product data from the JSON request body
     * @return {@code 201 Created} with the new product including its generated ID;
     *         {@code 400 Bad Request} if validation fails
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(product));
    }

    /**
     * Fully replaces an existing product by ID.
     * HTTP Method: PUT /api/v1/products/{id}
     *
     * @param id      the ID of the product to replace
     * @param product the complete new product data
     * @return {@code 200 OK} with the updated product;
     *         {@code 404 Not Found} if the ID does not exist;
     *         {@code 400 Bad Request} if validation fails
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.replaceProduct(id, product));
    }

    /**
     * Partially updates an existing product by ID.
     * HTTP Method: PATCH /api/v1/products/{id}
     *
     * @param id             the ID of the product to update
     * @param partialProduct the fields to update; all others remain unchanged
     * @return {@code 200 OK} with the patched product;
     *         {@code 404 Not Found} if the ID does not exist
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(
            @PathVariable Long id,
            @RequestBody Product partialProduct) {
        return ResponseEntity.ok(productService.patchProduct(id, partialProduct));
    }

    /**
     * Deletes a product by its ID.
     * HTTP Method: DELETE /api/v1/products/{id}
     *
     * @param id the ID of the product to delete
     * @return {@code 204 No Content} on successful deletion;
     *         {@code 404 Not Found} if the ID does not exist
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
