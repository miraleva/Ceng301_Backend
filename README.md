# CENG301 Gym Management System Backend

This project is the backend service for the CENG301 Gym Management System. It is a robust RESTful API built with Spring Boot 3.2.1 and PostgreSQL, designed to manage gym operations including members, trainers, classes, enrollments, and payments.

The system emphasizes database integrity, type safety, optimized SQL reporting, and predictable API behavior through global error handling.

## Key Features

### Member & Trainer Management
*   Full CRUD operations for gym members and trainers
*   Referential integrity enforced at the database level

### Class Scheduling
*   Manages classes via the `gym_class` table
*   Assigns trainers to classes
*   Enforces class capacity
*   Uses strict DATE types (PostgreSQL) mapped to LocalDate (Java)

### Smart Enrollment System
*   Prevents overbooking
*   **Duplicate Prevention**:
    *   Enforces a unique constraint (`uq_enrollment`) on (`member_id`, `class_id`)
    *   Duplicate enrollments return `409 Conflict`
*   User-friendly error messages

### Financial Tracking
*   Records member payments
*   Links payments directly to members

### Advanced Reporting
*   6 analytical endpoints under `/api/reports`
*   Powered by `NamedParameterJdbcTemplate`
*   Uses optimized raw SQL (no unnecessary entity loading)
*   High performance and database-driven aggregation

### Robust Error Handling
*   Centralized `GlobalExceptionHandler`
*   Maps:
    *   SQL constraint violations → `409 Conflict`
    *   Invalid HTTP methods → `405 Method Not Allowed`
    *   Validation errors → `400 Bad Request`

## Technology Stack
*   **Language**: Java 17
*   **Framework**: Spring Boot 3.2.1 (Web, Data JPA, Validation)
*   **Database**: PostgreSQL
*   **Build Tool**: Maven
*   **Connection Pool**: HikariCP

## Database Architecture
Relational schema with the following core tables:
*   `membership` — Membership catalog (Gold, Silver, Platinum)
*   `member` — Gym members linked to a membership
*   `trainer` — Trainers and staff
*   `gym_class` — Classes with schedule, capacity, and trainer_id
*   `class_enrollment` — Member ↔ Class relationship (Unique constraint prevents duplicate enrollment)
*   `payment` — Member payment records

## Setup & Installation

### 1. Database Setup
Ensure PostgreSQL is running and create the database:
```sql
CREATE DATABASE gym_db;
```
Navigate to `Database/sql` and execute the initialization scripts.
**Important**: Seed the database with test data:
```bash
psql -d gym_db -f Database/sql/seed_test_data.sql
```

### 2. Configuration
Update `src/main/resources/application.yml` with your database credentials:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gym_db
    username: postgres
    password: Password_123
```

### 3. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```
The application starts at: `http://localhost:8080`

## API Endpoints & Verification
Postman payload examples are available in `sample_payloads.json`.

### Core Management Endpoints
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/api/members` | Returns seeded members (John, Jane, Ali) |
| GET | `/api/classes` | Returns classes with trainerName, capacity, ISO dates |
| POST | `/api/enrollments/create` | Enroll member (409 on duplicate) |
| POST | `/api/payments/create` | Record a payment |
| GET | `/api/payments` | List payments with memberName |

### Analytical Reports (/api/reports)
These endpoints execute optimized SQL directly against PostgreSQL:

| Endpoint | Description |
| :--- | :--- |
| `/oldest-member` | Member with earliest date of birth |
| `/most-popular-class` | Class with highest enrollment |
| `/monthly-revenue?year=2025&month=12` | Revenue for a given month |
| `/members-by-membership` | Distribution by membership tier |
| `/trainer-workload` | Classes and enrollments per trainer |
| `/member-payment-summary?memberId=1` | Total paid + last payment date |

## Verification Checklist
Use this checklist to confirm system correctness:

1.  **Seed Data**
    *   Run `seed_test_data.sql`
    *   Verify row counts (≈ 3 members, 2 classes)
2.  **API Check**
    *   Call `GET /api/members`
    *   Confirm seeded data is returned
3.  **Constraint Check**
    *   `POST /api/enrollments/create` → `200 OK`
    *   Repeat same payload → `409 Conflict`
4.  **Report Check**
    *   Call `/api/reports/member-payment-summary?memberId=1`
    *   Confirm valid JSON (no 500 errors)

## Project Structure
```text
Ceng301_Backend/
├── Database/
│   └── sql/
│       ├── seed_test_data.sql        # Seed data for verification
│       ├── reports.sql               # SQL functions/queries for reports
│       ├── patch_capacity.sql        # Adds capacity to gym_class
│       └── patch_schedule_date.sql   # Converts schedule to DATE
│
├── src/
│   └── main/
│       ├── java/com/miraleva/ceng301/
│       │   ├── controller/            # REST controllers
│       │   ├── dto/                   # Request/Response DTOs
│       │   ├── dto/report/            # Report-specific DTOs
│       │   ├── entity/                # JPA entities
│       │   ├── repository/            # Spring Data repositories
│       │   ├── service/               # Services + implementations
│       │   └── exception/             # GlobalExceptionHandler
│       └── resources/
│           └── application.yml
│
├── pom.xml
├── sample_payloads.json
└── README.md
```

## API Response Format
All endpoints return a unified response wrapper:
```json
{
  "success": true,
  "message": "Fetched successfully",
  "data": {},
  "timestamp": "2025-12-24T18:11:29.632618500Z"
}
```

## Environment & Port
*   **Default Port**: 8080
*   **Base URL**: `http://localhost:8080`

## How to run backend

1. Create PostgreSQL database (or use Neon)
2. Run SQL files in this order:
   - Schema.sql
   - routines.sql
   - indexes.sql
   - reports.sql
   - seed_test_data.sql

3. Set DB connection in:
src/main/resources/application.yml

4. Run:
mvn spring-boot:run

Backend runs at:
http://localhost:8080

## Frontend (Web UI)

The project includes a full web-based frontend built with Express + EJS.

To run the UI:

```bash
cd frontend
npm install
node server.js
Then open:
http://localhost:3000

The frontend communicates directly with the Spring Boot backend at:
http://localhost:8080

