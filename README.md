# CENG301 Gym Management System Backend

This project is the backend service for the CENG301 Gym Management System. It is a robust RESTful API built with **Spring Boot 3.2.1** and **PostgreSQL**, designed to manage gym operations including members, trainers, classes, enrollments, and payments. It features explicit database integrity controls, advanced SQL-driven reporting, and global error handling.

## Key Features

*   **Member & Trainer Management:** Full CRUD capabilities for managing gym staff and clientele.
*   **Class Scheduling:** 
    *   Manages classes (table: `gym_class`) with assigned trainers.
    *   Enforces `capacity` limits.
    *   Uses strict `DATE` types (PostgreSQL) mapped to `LocalDate` (Java).
*   **Smart Enrollment System:** 
    *   Prevents overbooking.
    *   **Duplicate Prevention:** Enforces unique constraints (`uq_enrollment`) on `(member_id, class_id)`, returning `409 Conflict` with user-friendly messages.
*   **Financial Tracking:** Records payments linked to members.
*   **Advanced Reporting:** 
    *   Six dedicated endpoints powered by **NamedParameterJdbcTemplate**.
    *   Uses optimized raw SQL queries (Avoiding memory-heavy entity loading) for high performance.
*   **Robust Error Handling:** 
    *   Global Exception Handler acts as a safety anet.
    *   Maps SQL constraints to `409 Conflict`.
    *   Maps invalid HTTP methods to `405 Method Not Allowed`.

## Technology Stack

*   **Language:** Java 17
*   **Framework:** Spring Boot 3.2.1 (Web, Data JPA, Validation)
*   **Database:** PostgreSQL
*   **Build Tool:** Maven
*   **Pool:** HikariCP

## Database Architecture

The system uses a relational schema with the following key tables:
*   `membership`: Defines tiers (Gold, Silver, Platinum).
*   `member`: Gym members linked to a membership.
*   `trainer`: Staff members.
*   `gym_class`: Classes with `schedule`, `capacity`, and `trainer_id`.
*   `class_enrollment`: Links members to classes. Unique constraint ensures a member cannot enroll twice in the same class.
*   `payment`: Financial records.

## Setup & Installation

### 1. Database Setup
Ensure PostgreSQL is running and create the database:
```sql
CREATE DATABASE gym_db;
```

Navigate to `Database/sql` and execute the initialization scripts. **Crucially, seed the test data:**
```bash
psql -d gym_db -f Database/sql/seed_test_data.sql
```

### 2. Configuration
Update `src/main/resources/application.yml` with your credentials:
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
The application runs on `http://localhost:8080`.

## API Endpoints & Verification

Test payloads are available in `sample_payloads.json` in the project root.

### Core Management
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| **GET** | `/api/members` | Returns seeded members (e.g., John, Jane, Ali) |
| **GET** | `/api/classes` | Returns classes with `trainerName`, `capacity`, and ISO dates (YYYY-MM-DD) |
| **POST** | `/api/enrollments/create` | Enroll a member. **Returns 409 Conflict on duplicate.** |
| **POST** | `/api/payments/create` | Record a payment. |
| **GET** | `/api/payments` | List payments with `memberName`. |

### Analytical Reports
These endpoints execute optimized SQL directly against the database:

| Endpoint (`/api/reports/...`) | Description |
| :--- | :--- |
| `/oldest-member` | Retreives member with earliest DOB. |
| `/most-popular-class` | Class with highest enrollment count. |
| `/monthly-revenue?year=2025&month=12` | Total revenue for specific period. |
| `/members-by-membership` | Distribution of members across tiers. |
| `/trainer-workload` | Count of classes and enrollments per trainer. |
| `/member-payment-summary?memberId=1` | Total paid and last payment date for a specific member. |

## Verification Checklist

To verify the system is working correctly:
1.  **Seed Data:** Run `seed_test_data.sql` and check table row counts (approx 3 members, 2 classes).
2.  **API Check:** Call `GET /api/members` and confirm JSON data matches seed.
3.  **Constraint Check:** 
    *   Send `POST /api/enrollments/create` with `{ "memberId": 1, "classId": 1 ... }` -> **200 OK**.
    *   Send the **same** payload again -> **409 Conflict** (Verify message is "Duplicate enrollment...").
4.  **Report Check:** Call `/api/reports/member-payment-summary?memberId=1` and ensure valid JSON is returned (no 500 errors).

## Project Structure

Ceng301_Backend/
├── Database/
│ └── sql/ # SQL schema/patch/report/seed scripts
│ ├── seed_test_data.sql # Seed data for verification (Step 8)
│ ├── reports.sql # SQL queries/functions for reporting endpoints
│ ├── patch_capacity.sql # Adds capacity to gym_class
│ └── patch_schedule_date.sql # Converts schedule to DATE
│
├── src/
│ └── main/
│ ├── java/com/miraleva/ceng301/
│ │ ├── controller/ # REST controllers (Members, Classes, Enrollments, Payments, Reports)
│ │ ├── dto/ # Request/Response DTOs + ApiResponse wrapper
│ │ ├── dto/report/ # Report DTOs for /api/reports endpoints
│ │ ├── entity/ # JPA entities mapped to PostgreSQL tables
│ │ ├── repository/ # Spring Data JPA repositories
│ │ ├── service/ # Service interfaces + implementations
│ │ └── exception/ # GlobalExceptionHandler + custom errors
│ └── resources/
│ └── application.yml # DB config (url/username/password)
│
├── pom.xml # Maven dependencies
├── sample_payloads.json # Postman payload examples for endpoint testing
└── README.md

## API Response Format

All endpoints return a unified wrapper:

```json
{
  "success": true,
  "message": "Fetched successfully",
  "data": {},
  "timestamp": "2025-12-24T18:11:29.632618500Z"
}

---

## ✅ Add: Environment & Port

```md
## Environment

- Default port: **8080**
- Base URL: `http://localhost:8080`

