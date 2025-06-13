#  Scalable Tracking Number Generator API

A Spring Boot REST service that generates **unique alphanumeric** (up to 16 characters) tracking numbers for parcels—built for efficiency, concurrency-safety, and horizontal scalability.

##  Features

- **GET** `/next-tracking-number` endpoint  
- Accepts these query parameters:
  - `origin_country_id` (ISO 3166‑1 α‑2)
  - `destination_country_id` (ISO 3166‑1 α‑2)
  - `weight` (in kg, up to 3 decimals)
  - `created_at` (RFC 3339 timestamp)
  - `customer_id` (UUID), `customer_name`, `customer_slug`
- Ensures tracking numbers:
  - Match regex `^[A-Z0-9]{1,16}$`
  - Are unique, random, and time-sensitive
  - Are generated efficiently and thread-safe
- Stateless design—suitable for horizontal scaling

## Prerequisites

- Java **17** or newer  
- Maven **3+**  

## Setup & Run Locally

**Clone the repository**  
   ```bash
   git clone https://github.com/ShilpaItnal23/Tracking-Number-Generator-API
   cd Tracking-Number-Generator-API

## Build the project
mvn clean package

## Run the service
mvn spring-boot:run

##Request

curl --location 'http://localhost:9191/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2025-06-11T19%3A29%3A32%2B08%3A00&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics%0A'

##Response
{
    "tracking_number": "MYID13F2370454C4",
    "created_at": "2025-06-12T15:05:58.2602048+05:30"
}

##Validation Request/Response

1.Blank origin_country_id

http://localhost:9191/next-tracking-number?origin_country_id=&destination_country_id=US&weight=2.5&created_at=2025-06-13T10:00:00+05:30&customer_id=uuid-123&customer_name=Test%20Co&customer_slug=test-co

{
    "error": "Invalid value for parameter 'created_at': '2025-06-13T10:00:00 05:30'",
    "message": "Expected format for 'created_at' is ISO 8601 (e.g. 2024-05-04T10:15:30+05:30)",
    "status": 400
}

2.Blank destination_country_id

http://localhost:9191/next-tracking-number?origin_country_id=IN&destination_country_id=&weight=2.5&created_at=2025-06-13T10:00:00+05:30&customer_id=uuid-123&customer_name=Test%20Co&customer_slug=test-co

{
    "error": "Invalid value for parameter 'created_at': '2025-06-13T10:00:00 05:30'",
    "message": "Expected format for 'created_at' is ISO 8601 (e.g. 2024-05-04T10:15:30+05:30)",
    "status": 400
}

3. Negative weight

http://localhost:9191/next-tracking-number?origin_country_id=IN&destination_country_id=US&weight=-1.0&created_at=2025-06-13T10:00:00+05:30&customer_id=uuid-123&customer_name=Test%20Co&customer_slug=test-co

{
    "error": "Invalid value for parameter 'created_at': '2025-06-13T10:00:00 05:30'",
    "message": "Expected format for 'created_at' is ISO 8601 (e.g. 2024-05-04T10:15:30+05:30)",
    "status": 400
}

4. Blank customer_id

http://localhost:9191/next-tracking-number?origin_country_id=IN&destination_country_id=US&weight=2.5&created_at=2025-06-13T10:00:00+05:30&customer_id=&customer_name=Test%20Co&customer_slug=test-co

{
    "error": "Invalid value for parameter 'created_at': '2025-06-13T10:00:00 05:30'",
    "message": "Expected format for 'created_at' is ISO 8601 (e.g. 2024-05-04T10:15:30+05:30)",
    "status": 400
}

5.Blank customer_name

http://localhost:9191/next-tracking-number?origin_country_id=IN&destination_country_id=US&weight=2.5&created_at=2025-06-13T10:00:00+05:30&customer_id=uuid-123&customer_name=Test%20Co&customer_slug=

{
    "error": "Invalid value for parameter 'created_at': '2025-06-13T10:00:00 05:30'",
    "message": "Expected format for 'created_at' is ISO 8601 (e.g. 2024-05-04T10:15:30+05:30)",
    "status": 400
}

6.Blank customer_slug

http://localhost:9191/next-tracking-number?origin_country_id=IN&destination_country_id=US&weight=2.5&created_at=2025-06-13T10:00:00+05:30&customer_id=uuid-123&customer_name=Test%20Co&customer_slug=

{
    "error": "Invalid value for parameter 'created_at': '2025-06-13T10:00:00 05:30'",
    "message": "Expected format for 'created_at' is ISO 8601 (e.g. 2024-05-04T10:15:30+05:30)",
    "status": 400
}

7.Missing created_at entirely

http://localhost:9191/next-tracking-number?origin_country_id=&destination_country_id=&weight=-5&created_at=2025-06-13T10:00:00+05:30&customer_id=&customer_name=&customer_slug=

{
    "error": "Invalid value for parameter 'created_at': '2025-06-13T10:00:00 05:30'",
    "message": "Expected format for 'created_at' is ISO 8601 (e.g. 2024-05-04T10:15:30+05:30)",
    "status": 400
}




