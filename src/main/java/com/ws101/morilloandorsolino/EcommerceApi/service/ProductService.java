package com.ws101.morilloandorsolino.EcommerceApi.service;

import com.ws101.morilloandorsolino.EcommerceApi.exception.InvalidFilterException;
import com.ws101.morilloandorsolino.EcommerceApi.exception.ProductNotFoundException;
import com.ws101.morilloandorsolino.EcommerceApi.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service class for product-related operations.
 *
 * <p>Provides business logic for filtering, searching, and managing products.
 * This class acts as an intermediary between the API controller and the
 * data access layer.
 *
 * <p>All data is stored in-memory using a {@code List<Product>} backed by
 * an {@code ArrayList}. This means all data is lost when the application
 * stops. This is a known limitation of the current implementation.
 *
 * @author Morillo and Orsolino
 * @see Product
 */
@Service
public class ProductService {

    /**
     * In-memory storage for all products.
     * Acts as the data source for all CRUD and filter operations.
     * Pre-loaded with 10 sample products on application startup.
     */
    private final List<Product> productList = new ArrayList<>();

    /**
     * Thread-safe counter used to generate unique product IDs.
     * Starts at 11 since sample data occupies IDs 1 through 10.
     */
    private final AtomicLong idCounter = new AtomicLong(11);

    /**
     * Constructs the service and populates the in-memory list
     * with 10 sample products covering various categories.
     */
    public ProductService() {
        productList.add(new Product(1L,  "Wireless Headphones",          "Noise-cancelling Bluetooth headphones with 40hr battery",  2999.00, "Electronics", 50,  "https://example.com/images/headphones.jpg"));
        productList.add(new Product(2L,  "Running Shoes",                "Lightweight cushioned running shoes for all terrain",      3500.00, "Footwear",    120, "https://example.com/images/shoes.jpg"));
        productList.add(new Product(3L,  "Stainless Steel Water Bottle", "1L insulated bottle, keeps drinks cold for 24 hours",     650.00,  "Kitchen",     200, "https://example.com/images/bottle.jpg"));
        productList.add(new Product(4L,  "Mechanical Keyboard",          "TKL keyboard with blue switches and RGB backlighting",    4500.00, "Electronics", 30,  "https://example.com/images/keyboard.jpg"));
        productList.add(new Product(5L,  "Cotton T-Shirt",               "Premium 100% cotton crew-neck t-shirt in 10 colors",     399.00,  "Clothing",    500, "https://example.com/images/tshirt.jpg"));
        productList.add(new Product(6L,  "Yoga Mat",                     "6mm thick non-slip eco-friendly yoga mat",               890.00,  "Sports",      80,  "https://example.com/images/yoga.jpg"));
        productList.add(new Product(7L,  "Gaming Mouse",                 "High-precision optical gaming mouse, 16000 DPI",         1800.00, "Electronics", 60,  "https://example.com/images/mouse.jpg"));
        productList.add(new Product(8L,  "Novel: The Midnight Library",  "Bestselling fiction novel by Matt Haig",                 450.00,  "Books",       150, "https://example.com/images/book.jpg"));
        productList.add(new Product(9L,  "Electric Kettle",              "1.7L fast-boil kettle with auto shut-off",              1200.00, "Kitchen",     75,  "https://example.com/images/kettle.jpg"));
        productList.add(new Product(10L, "Denim Jeans",                  "Classic slim-fit denim jeans, stretchable fabric",       1350.00, "Clothing",    300, "https://example.com/images/jeans.jpg"));
    }

    /**
     * Retrieves all products currently stored in the in-memory list.
     *
     * @return a new {@code List<Product>} containing all products; never {@code null}
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(productList);
    }

    /**
     * Retrieves a single product by its unique ID.
     *
     * @param id the unique identifier of the product to retrieve
     * @return the {@code Product} with the matching ID
     * @throws ProductNotFoundException if no product with the given ID exists
     */
    public Product getProductById(Long id) {
        return productList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Filters products using a generic filter type and value.
     *
     * <p>Supported {@code filterType} values:
     * <ul>
     *   <li>{@code name} — case-insensitive substring match on product name</li>
     *   <li>{@code category} — case-insensitive substring match on category</li>
     *   <li>{@code price} — exact price match</li>
     *   <li>{@code minPrice} — products with price greater than or equal to value</li>
     *   <li>{@code maxPrice} — products with price less than or equal to value</li>
     * </ul>
     *
     * @param filterType  the type of filter to apply
     * @param filterValue the value to filter against
     * @return a {@code List<Product>} matching the filter; may be empty
     * @throws InvalidFilterException   if {@code filterType} is not supported
     * @throws IllegalArgumentException if a price value cannot be parsed
     */
    public List<Product> filterProducts(String filterType, String filterValue) {
        return switch (filterType.toLowerCase()) {
            case "name"     -> filterProductsByName(filterValue);
            case "category" -> filterProductsByCategory(filterValue);
            case "price"    -> filterProductsByExactPrice(filterValue);
            case "minprice" -> filterProductsByMinPrice(filterValue);
            case "maxprice" -> filterProductsByMaxPrice(filterValue);
            default -> throw new InvalidFilterException(filterType);
        };
    }

    /**
     * Filters products whose name contains the given keyword (case-insensitive).
     *
     * @param name the keyword to search for in product names
     * @return a {@code List<Product>} whose names contain the keyword
     */
    private List<Product> filterProductsByName(String name) {
        return productList.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    /**
     * Filters products whose category contains the keyword (case-insensitive).
     *
     * @param category the category keyword to filter by
     * @return a {@code List<Product>} matching the given category
     */
    private List<Product> filterProductsByCategory(String category) {
        return productList.stream()
                .filter(p -> p.getCategory().toLowerCase().contains(category.toLowerCase()))
                .toList();
    }

    /**
     * Filters products by exact price match.
     *
     * @param priceStr the price string to parse and match exactly
     * @return a {@code List<Product>} with the exact price
     * @throws IllegalArgumentException if {@code priceStr} is not a valid number
     */
    private List<Product> filterProductsByExactPrice(String priceStr) {
        double price = parsePriceOrThrow(priceStr);
        return productList.stream()
                .filter(p -> p.getPrice().equals(price))
                .toList();
    }

    /**
     * Filters products with price greater than or equal to the given minimum.
     *
     * @param priceStr the minimum price string
     * @return a {@code List<Product>} with price greater than or equal to minPrice
     * @throws IllegalArgumentException if the value is invalid or negative
     */
    private List<Product> filterProductsByMinPrice(String priceStr) {
        double minPrice = parsePriceOrThrow(priceStr);
        if (minPrice < 0) throw new IllegalArgumentException("Minimum price cannot be negative.");
        return productList.stream()
                .filter(p -> p.getPrice() >= minPrice)
                .toList();
    }

    /**
     * Filters products with price less than or equal to the given maximum.
     *
     * @param priceStr the maximum price string
     * @return a {@code List<Product>} with price less than or equal to maxPrice
     * @throws IllegalArgumentException if the value is invalid or negative
     */
    private List<Product> filterProductsByMaxPrice(String priceStr) {
        double maxPrice = parsePriceOrThrow(priceStr);
        if (maxPrice < 0) throw new IllegalArgumentException("Maximum price cannot be negative.");
        return productList.stream()
                .filter(p -> p.getPrice() <= maxPrice)
                .toList();
    }

    /**
     * Filters products by price range, inclusive of both boundaries.
     *
     * @param minPrice the minimum price threshold (inclusive).
     *                 Must be non-negative and less than or equal to maxPrice.
     * @param maxPrice the maximum price threshold (inclusive).
     *                 Must be non-negative and greater than or equal to minPrice.
     * @return a {@code List<Product>} containing all products with price within
     *         [minPrice, maxPrice]. Returns an empty list if no products match.
     * @throws IllegalArgumentException if minPrice is negative, maxPrice is
     *                                  negative, or minPrice is greater than maxPrice
     * @see #filterProductsByMinPrice(String)
     */
    public List<Product> filterProductWithPrice(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0)
            throw new IllegalArgumentException("Price values must be non-negative.");
        if (minPrice > maxPrice)
            throw new IllegalArgumentException("minPrice must not be greater than maxPrice.");
        return productList.stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .toList();
    }

    /**
     * Filters products by exact category match (case-insensitive).
     *
     * @param category the category string to match
     * @return a {@code List<Product>} whose category matches the given value
     * @see #filterProductsByCategory(String)
     */
    public List<Product> filterProductWithCategory(String category) {
        return productList.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    /**
     * Creates and stores a new product in the in-memory list.
     *
     * @param product the product data from the client; the ID field is overwritten
     * @return the newly created {@code Product} with its generated ID assigned
     */
    public Product createProduct(Product product) {
        product.setId(idCounter.getAndIncrement());
        productList.add(product);
        return product;
    }

    /**
     * Fully replaces an existing product (PUT semantics).
     *
     * @param id             the ID of the product to replace
     * @param updatedProduct the new product data
     * @return the updated {@code Product} after replacement
     * @throws ProductNotFoundException if no product with the given ID exists
     */
    public Product replaceProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setCategory(updatedProduct.getCategory());
        existing.setStockQuantity(updatedProduct.getStockQuantity());
        existing.setImageUrl(updatedProduct.getImageUrl());
        return existing;
    }

    /**
     * Partially updates an existing product (PATCH semantics).
     *
     * @param id             the ID of the product to partially update
     * @param partialProduct an object containing only the fields to be updated
     * @return the updated {@code Product} after partial update
     * @throws ProductNotFoundException if no product with the given ID exists
     */
    public Product patchProduct(Long id, Product partialProduct) {
        Product existing = getProductById(id);
        if (partialProduct.getName() != null)         existing.setName(partialProduct.getName());
        if (partialProduct.getDescription() != null)  existing.setDescription(partialProduct.getDescription());
        if (partialProduct.getPrice() != null)         existing.setPrice(partialProduct.getPrice());
        if (partialProduct.getCategory() != null)      existing.setCategory(partialProduct.getCategory());
        if (partialProduct.getStockQuantity() != null) existing.setStockQuantity(partialProduct.getStockQuantity());
        if (partialProduct.getImageUrl() != null)      existing.setImageUrl(partialProduct.getImageUrl());
        return existing;
    }

    /**
     * Removes a product from the in-memory list by its ID.
     *
     * @param id the unique identifier of the product to delete
     * @throws ProductNotFoundException if no product with the given ID exists
     */
    public void deleteProduct(Long id) {
        productList.remove(getProductById(id));
    }

    /**
     * Parses a price string into a {@code double} value.
     *
     * @param priceStr the string to parse
     * @return the parsed price as a {@code double}
     * @throws IllegalArgumentException if the string is not a valid number
     */
    private double parsePriceOrThrow(String priceStr) {
        try {
            return Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid price value: '" + priceStr + "'. Must be a numeric value.");
        }
    }
}