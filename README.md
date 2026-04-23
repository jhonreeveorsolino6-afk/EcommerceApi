# EcommerceApi

A RESTful API for an e-commerce product catalog built with Spring Boot.
Data is stored in-memory using ArrayList — no database required.

## Authors
- Morillo and Orsolino

---

## How to Run

Step 1 - Clone the repository:
git clone https://github.com/YOURUSERNAME/EcommerceApi.git

Step 2 - Go into the project folder:
cd EcommerceApi

Step 3 - Run the application:

    Windows:
        gradlew.bat bootRun

    Mac or Linux:
        ./gradlew bootRun

Step 4 - Open your browser or Postman and go to:
http://localhost:8080/api/v1/products

---

## API Endpoints

Base URL: http://localhost:8080/api/v1

| Method | Path | Description |
|--------|------|-------------|
| GET | /products | Get all products |
| GET | /products/{id} | Get product by ID |
| GET | /products/filter?filterType={type}&filterValue={value} | Filter products |
| POST | /products | Create new product |
| PUT | /products/{id} | Replace product |
| PATCH | /products/{id} | Partially update product |
| DELETE | /products/{id} | Delete product |

---

## Filter Types

| filterType | Description |
|------------|-------------|
| name | Search by name |
| category | Search by category |
| price | Exact price match |
| minPrice | Price greater than or equal to value |
| maxPrice | Price less than or equal to value |

---

## HTTP Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK |
| 201 | Created |
| 204 | No Content |
| 400 | Bad Request |
| 404 | Not Found |
| 500 | Internal Server Error |

---

## Known Limitations
- Data is lost on restart — no database used
- No authentication
- No pagination