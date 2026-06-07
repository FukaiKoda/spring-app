Here is your tailored roadmap, adapted for Spring Boot, Java best practices, and enterprise-grade architecture.

---

# Project Context: Real-time Project Management Tool (Trello Clone)

## Step 0: Spring Initializr Setup

Before writing any code, bootstrap the project using [Spring Initializr](https://start.spring.io/). Use **Maven** or **Gradle** (Maven is highly standard), **Java 17 or 21**, and add the following core dependencies:

* **Spring Web:** For building RESTful applications, including Spring MVC.
* **Spring Data JPA:** To handle database interactions and ORM.
* **PostgreSQL Driver:** The JDBC driver for your database.
* **Spring Security:** For robust authentication and authorization.
* **OAuth2 Client:** For GitHub/Google social login.
* **Validation:** Jakarta Bean Validation (Hibernate Validator) to replace Zod.
* **Spring Data Redis:** For caching and session management.
* **WebSocket:** For building real-time, two-way communication.
* **Lombok:** A Java library that automatically plugs into your editor and build tools to minimize boilerplate (generates getters, setters, constructors, and builder patterns).
* **Spring Boot DevTools:** For fast application restarts and live reloads during development.

---

## Overview

This is the final, "Hard" level project. The goal is to build a production-ready, scalable application with real-time features and complex business rules, leveraging Java's strong typing and Spring's IoC (Inversion of Control) container.

## Tech Stack

* **Framework:** Spring Boot (Java)
* **Database:** PostgreSQL
* **ORM:** Spring Data JPA / Hibernate
* **Authentication:** Spring Security (Session management + OAuth2 via `spring-boot-starter-oauth2-client`)
* **Hashing:** BCrypt / Argon2 (Provided via Spring Security's `PasswordEncoder`)
* **Real-time:** Spring WebSocket with STOMP (Simple Text Oriented Messaging Protocol)
* **Caching & Sessions:** Spring Session Data Redis
* **Validation:** Jakarta Bean Validation (`@Valid`, `@NotNull`, `@Size`)
* **File Uploads:** Spring Web's `MultipartFile` interface

## Key Features & Resources

* **Resources:** Workspaces, Boards, Lists, Cards. Highly relational data structures requiring advanced JPA mappings (`@OneToMany`, `@ManyToOne`) and transaction management (`@Transactional`).
* **Real-time Activity:** Instant updates using WebSockets/STOMP message brokers when a user interacts with the board.
* **File Attachments:** Attaching files to cards via multipart uploads.
* **Transactions:** Ensuring atomic database operations using Spring's `@Transactional` annotation (e.g., rolling back if board creation fails halfway).

## Architecture: Clean / Hexagonal Architecture

We are adopting strict boundaries to isolate domain logic from the Spring framework and database context.

1. **Domain Entities:** Core pure-Java business objects. (In a strict setup, these are entirely decoupled from database annotations).
2. **JPA Entities & Repositories (Data Access Layer):** Spring Data JPA interfaces (`JpaRepository`). This layer maps your Domain Entities to Database Tables and handles queries.
3. **Services (Use Case Layer):** Annotated with `@Service`. Contains application logic, orchestrates transactions, and maps between Data Transfer Objects (DTOs), Domain Entities, and JPA Entities.
4. **Controllers (Presentation Layer):** Annotated with `@RestController`. Handles HTTP requests, unwraps payloads, and returns structured `ResponseEntity` objects.
5. **WebSockets Layer:** Controller methods annotated with `@MessageMapping` and `@SendTo` for handling STOMP messages.
6. **Config / Setup:** Classes annotated with `@Configuration` to manage security chains, CORS, WebSocket brokers, and custom Beans.

### Design Patterns to Implement

* **Builder Pattern:** Utilize Lombok's `@Builder` annotation on Entities and DTOs for clean, readable object instantiation.
* **Factory Pattern:** Use Spring's `@Component` or `@Bean` factory methods for complex object creation strategies.
* **Observer Pattern:** Use Spring's robust `ApplicationEventPublisher` and `@EventListener` to decouple domain events (e.g., firing a "CardMovedEvent" that a separate notification service listens to).
* **Dependency Injection:** Inject dependencies via constructors (Spring implicitly autowires classes with a single constructor). Avoid field injection (`@Autowired` on variables) for better testability.

## Best Practices

* **DTOs (Data Transfer Objects):** Never expose your internal JPA Entities directly to the API responses. Map them to Records or DTO classes to prevent over-posting and accidental data leaks.
* **Error Handling:** Implement a global exception handler using `@RestControllerAdvice` to intercept custom exceptions (e.g., `ResourceNotFoundException`) and return standardized JSON error responses.
* **Validation:** Validate incoming requests by applying `@Valid` on Controller parameters and defining constraints on your DTOs.
* **Security:** Configure a robust `SecurityFilterChain` bean to handle stateless/session configurations, CORS configurations, and method-level security (`@PreAuthorize`).

## Current Setup & Next Steps

* Project bootstrapped via Spring Initializr with Maven/Gradle.
* `application.yml` (or `application.properties`) configured with database credentials, server ports, and basic Redis properties.
* Core dependencies imported in `pom.xml` or `build.gradle`.

### Project Status: Backend Core Implementation

We have established the persistence layer with Spring Data JPA and implemented the core authentication flow. We are now moving towards implementing the core project management features.

**Recent Accomplishments:**

1. Created pure Java Domain models and corresponding JPA Entities (`User`, `Workspace`, `Board`, `List`, `Card`, `WorkspaceMember`) using Lombok for boilerplate reduction and strict `@Entity` mappings.
2. Configured PostgreSQL connection and Hibernate auto-ddl rules (e.g., `update` or `validate`) in `application.yml`.
3. Implemented Authentication flow (Custom `UserDetailsService`, Auth Controller) using Spring Security, backed by Redis for centralized session management.
4. Implemented Workspace Management CRUD operations (`@Service`, `@RestController`) with scoped access logic.
5. Implemented Board Management CRUD operations with method-level security (`@PreAuthorize`) enforcing strict workspace role-based authorization.
6. Implemented List & Card Management CRUD operations, including a custom JPQL query for cross-list card migration and position calculation (`+65535` gaps) to support frontend drag-and-drop.
7. Implemented robust request validation using Jakarta Validation annotations on incoming DTO Records.
8. Implemented Workspace Member Management to securely handle invitations and role updates, utilizing Spring Application Events to decouple logic.
9. Create an endpoint utilizing Spring's `@RequestParam("file") MultipartFile file` to handle parsing, validation (size/type), and local/cloud storage for card attachments.
