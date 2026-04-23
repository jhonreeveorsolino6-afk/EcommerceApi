# EcommerceApi

A RESTful API for an e-commerce product catalog built with Spring Boot.
Data is stored in-memory using ArrayList — no database required.

## Authors
- Morillo and Orsolino

---

## Prerequisites
- Java 21+
- No Gradle installation needed — Gradle Wrapper is included.

---

## How to Run

Clone the repo:

    git clone <your-repo-url>
    cd EcommerceApi

Linux or Mac:

    ./gradlew bootRun

Windows:

    gradlew.bat bootRun

Server starts at: http://localhost:8080

---

## Project Structure

    src/main/java/com/ws101/morilloandorsolino/EcommerceApi/
    ├── EcommerceApiApplication.java
    ├── controller/
    │   └── ProductController.java
    ├── service/
    │   └── ProductService.java
    ├── model/
    │   ├── Product.java
    │   └── ErrorResponse.java
    └── exception/
        ├── ProductNotFoundException.java
        ├── InvalidFilterException.java
        └── GlobalExceptionHandler.java

---

## API Endpoints

Base URL: http://localhost:8080/api/v1

| Method | Path | Description | Status Codes |
|--------|------|-------------|--------------|
| GET | /products | Get all products | 200 |
| GET | /products/{id} | Get product by ID | 200, 404 |
| GET | /products/filter?filterType={type}&filterValue={value} | Filter products | 200, 400 |
| POST | /products | Create new product | 201, 400 |
| PUT | /products/{id} | Replace product fully | 200, 404, 400 |
| PATCH | /products/{id} | Partially update product | 200, 404 |
| DELETE | /products/{id} | Delete product | 204, 404 |

---

## Filter Types

| filterType | Description |
|------------|-------------|
| name | Case-insensitive name search |
| category | Case-insensitive category search |
| price | Exact price match |
| minPrice | Price greater than or equal to value |
| maxPrice | Price less than or equal to value |

---

## Sample Requests and Responses

### POST /api/v1/products

Request Body:

    {
      "name": "Smart Watch",
      "description": "Health tracking smartwatch with GPS",
      "price": 5999.00,
      "category": "Electronics",
      "stockQuantity": 25,
      "imageUrl": "https://example.com/watch.jpg"
    }

Response 201 Created:

    {
      "id": 11,
      "name": "Smart Watch",
      "description": "Health tracking smartwatch with GPS",
      "price": 5999.00,
      "category": "Electronics",
      "stockQuantity": 25,
      "imageUrl": "https://example.com/watch.jpg"
    }

---

### GET /api/v1/products/1

Response 200 OK:

    {
      "id": 1,
      "name": "Wireless Headphones",
      "description": "Noise-cancelling Bluetooth headphones with 40hr battery",
      "price": 2999.00,
      "category": "Electronics",
      "stockQuantity": 50,
      "imageUrl": "https://example.com/images/headphones.jpg"
    }

---

### GET /api/v1/products

Response 200 OK:

    [
      {
        "id": 1,
        "name": "Wireless Headphones",
        "description": "Noise-cancelling Bluetooth headphones with 40hr battery",
        "price": 2999.00,
        "category": "Electronics",
        "stockQuantity": 50,
        "imageUrl": "https://example.com/images/headphones.jpg"
      },
      {
        "id": 2,
        "name": "Running Shoes",
        "description": "Lightweight cushioned running shoes for all terrain",
        "price": 3500.00,
        "category": "Footwear",
        "stockQuantity": 120,
        "imageUrl": "https://example.com/images/shoes.jpg"
      }
    ]

---

### PUT /api/v1/products/1

Request Body:

    {
      "name": "Updated Headphones",
      "description": "Updated noise-cancelling headphones",
      "price": 2500.00,
      "category": "Electronics",
      "stockQuantity": 45,
      "imageUrl": "https://example.com/updated.jpg"
    }

Response 200 OK:

    {
      "id": 1,
      "name": "Updated Headphones",
      "description": "Updated noise-cancelling headphones",
      "price": 2500.00,
      "category": "Electronics",
      "stockQuantity": 45,
      "imageUrl": "https://example.com/updated.jpg"
    }

---

### PATCH /api/v1/products/2

Request Body:

    {
      "price": 2999.00
    }

Response 200 OK:

    {
      "id": 2,
      "name": "Running Shoes",
      "description": "Lightweight cushioned running shoes for all terrain",
      "price": 2999.00,
      "category": "Footwear",
      "stockQuantity": 120,
      "imageUrl": "https://example.com/images/shoes.jpg"
    }

---

### DELETE /api/v1/products/3

Response 204 No Content

---

### GET /api/v1/products/filter?filterType=category&filterValue=Electronics

Response 200 OK:

    [
      {
        "id": 1,
        "name": "Wireless Headphones",
        "price": 2999.00,
        "category": "Electronics",
        "stockQuantity": 50
      },
      {
        "id": 4,
        "name": "Mechanical Keyboard",
        "price": 4500.00,
        "category": "Electronics",
        "stockQuantity": 30
      }
    ]

---

### GET /api/v1/products/999 — Error Example

Response 404 Not Found:

    {
      "timestamp": "2025-01-01T12:00:00",
      "status": 404,
      "error": "NOT_FOUND",
      "message": "Product with ID 999 was not found.",
      "path": "/api/v1/products/999"
    }

---

### GET /api/v1/products/filter?filterType=color&filterValue=red — Error Example

Response 400 Bad Request:

    {
      "timestamp": "2025-01-01T12:00:00",
      "status": 400,
      "error": "BAD_REQUEST",
      "message": "Invalid filter type: 'color'. Supported types: name, category, price, minPrice, maxPrice.",
      "path": "/api/v1/products/filter"
    }

---

## HTTP Status Codes

| Code | Meaning | When Used |
|------|---------|-----------|
| 200 | OK | Successful GET, PUT, PATCH |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Validation failure or invalid filter type |
| 404 | Not Found | Product ID does not exist |
| 500 | Internal Server Error | Unexpected server error |

---

## Validation Rules

| Field | Rule |
|-------|------|
| name | Required, 2 to 100 characters |
| description | Required |
| price | Required, must be a positive number |
| category | Required |
| stockQuantity | Required, must be 0 or greater |
| imageUrl | Optional |

---

## Known Limitations
- All data is lost on application restart — no database is used
- No authentication or authorization on any endpoint
- No pagination — all products are returned in full
- In-memory storage is intentional per lab requirements

---

## Git Workflow Used

| Branch | What Was Done |
|--------|---------------|
| main | Final merged code |
| feat/product-model | Created Product model with validation |
| feat/service-layer | Created ProductService with in-memory storage and 10 sample products |
| feat/api-endpoints | Created REST controller with all 7 endpoints |
| feat/error-handling | Created exception handlers and standardized error responses |
| feat/documentation | Created README documentation |