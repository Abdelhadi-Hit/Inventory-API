
## Description:

Inventory management is essential for any sales business. In today's digital age, having a robust API-first approach ensures scalability, integration with other systems, and provides a foundation for future digital products.

## Business Requirements (done !):

1. **Supply API**:

   Created an API endpoint to handle the addition of products to the inventory.

   **Feature Details**:
  - Endpoint: `/api/supply`
  - Method: `POST`
  - Payload include product code, quantity, and optionally, an expiration date for perishable goods.
  - The maximum quantity that can be added in a single operation is 500 units.

2. **Sale API**:

   Created an API endpoint to handle the sale of products.

   **Feature Details**:
  - Endpoint: `/api/sale`
  - Method: `POST`
  - Payload include product code and quantity.
  - Ensure products are in stock before confirming the sale.

3. **Product Return API**:

   Handle the return of products.

   **Feature Details**:
  - Endpoint: `/api/return`
  - Method: `POST`
  - Payload include product code, quantity, and reason for return.
  - Returned products should be added back to the stock.

4. **Product Expiry Tracking API**:

   Track and fetch products nearing their expiration date.

   **Feature Details**:
  - Endpoint: `/api/expiry-alerts`
  - Method: `GET`
  - This endpoint return a list of products nearing their expiration date.

5. **Inventory Threshold Alerts API**:

   Set thresholds and fetch products below the threshold.

   **Feature Details**:
  - Endpoint: `/api/threshold-alerts`
  - Method: `GET`
  - This endpoint should return products below their set threshold.
  - Endpoint: `/api/set-threshold`
  - Method: `POST`
  - Payload include product code and threshold quantity.

## Achievements:

* The code has quality  (best practices, abstraction, readability...) and bugs.

* Implement all the above API endpoints.
* each API has appropriate error handling, especially for edge cases.
* unit tests for each API endpoint.
* Documented each API endpoint using tools like Swagger , detailing request/response formats, headers, and possible error messages.

* **More!!**:
  - Introduced authentication and access rights management on the API endpoints to differentiate roles (e.g., administrator, user, etc.)

## How to use:
To build the project, you will need:
* Java 17
* Maven

Build command:
```
mvn clean install
```

Run command:
```
./mvnw spring-boot:run 
## or use your preferred method (IDE, java -jar, docker...)
```

## Info !:

the system is ready for production !!ù


[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
