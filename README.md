Pastebin Lite
A simple Pastebin-like application built with Spring Boot and PostgreSQL.

Users can create text pastes and share a link to view them. Pastes can optionally expire after a time limit or a number of views.

Tech Stack

1.Java 17
2.Spring Boot
3.Spring Data JPA
4.PostgreSQL
5.Thymeleaf (UI)

How to Run Locally

Prerequisites-
-Java 17+
-PostgreSQL

Steps
1.Create database:
2.Update application.properties with DB credentials.
3.Run application:
4.Open browser:

API Endpoints-

GET /api/healthz
POST /api/pastes
GET /api/pastes/{id}
GET /p/{id}

Persistence Layer-
PostgreSQL is used to persist pastes across requests and restarts.

Design Decisions
-Backend-first design
-TTL and view limits enforced at fetch time
-Deterministic expiry testing using TEST_MODE and x-test-now-ms
