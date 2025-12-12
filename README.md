
# Hotel Booking Management – Microservices Architecture

**Updated:** December 11, 2025

This repository implements a **Hotel Booking Management** system using **Spring Boot microservices** with a small service ecosystem.  
Below you will find an accurate architecture overview, per‑service descriptions, tech stack, developer workflow, and diagrams (Mermaid) that render on GitHub.

---

## 🏗️ Overall Architecture

This project uses a **microservices** architecture with service discovery (Eureka), centralized configuration (Config Server) and an API Gateway that routes requests and serves the centralized Swagger UI.

```mermaid
flowchart LR
    subgraph Infrastructure
        E[Eureka Server\n(Service Discovery)]
        C[Config Server\n(Centralized Config)]
        G[API Gateway\n(Reverse Proxy + Swagger UI)]
    end

    subgraph Services
        B[Booking Service]
        R[Rooms Service]
        H[Host Service]
        EM[Employee Service]
    end

    %% relationships
    G -->|routes| B
    G -->|routes| R
    G -->|routes| H
    G -->|routes| EM

    B -->|service registration| E
    R -->|service registration| E
    H -->|service registration| E
    EM -->|service registration| E
    G -->|service registration| E

    B -->|reads config| C
    R -->|reads config| C
    H -->|reads config| C
    EM -->|reads config| C
    G -->|reads config| C

    %% interactions
    B -->|uses rooms API| R
    B -->|notifies host| H
```

---

## 🧩 Services & Responsibilities

All microservices are Spring Boot applications (Maven modules) inside this monorepo. The actual services found in the repository are:

- **api-gateway**  
  - Reverse proxy and single entry point for all microservices.  
  - Hosts the **centralized Swagger UI** (so API documentation for services is shown from the gateway).  
  - Responsible for routing, cross‑cutting concerns, and (optionally) rate limiting / security hooks.

- **eureka-server**  
  - Service discovery server (Eureka). All microservices register here so the gateway and other services can discover them.

- **config-server**  
  - Spring Cloud Config Server that provides centralized configuration for all services from `application-*.yml` stored in this repo (or a submodule).

- **booking-microservice**  
  - Core booking/reservation logic: create/cancel bookings, verify availability through Rooms Service, and coordinate with Host Service when needed.

- **rooms-microservice**  
  - Manages rooms, availability, room types, inventory, and room-related data.  
  - **Contains OpenAPI/Swagger annotations** (controllers and DTOs). These annotations are surfaced through the central Swagger UI at the API Gateway.

- **host-microservice**  
  - Manages hosts (hotel owners/operators), host-related metadata and notifications.

- **employee-microservice**  
  - Manages employee-related features (internal staff), used by admin flows in the system.

---

## 📘 Swagger / OpenAPI (Centralized)

- The project uses **OpenAPI / Swagger annotations** within services (the `rooms-microservice` already contains these annotations).  
- The **Swagger UI is hosted by the `api-gateway`**, which aggregates/documentation from backend services (centralized entry for API docs).  
- Planned: complete annotation coverage in other services so the gateway can display a full API catalog.

---

## 🔧 Technology Stack

**Core**
- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL (used by services via application properties)
- Spring Cloud:
  - **Eureka** (service discovery)
  - **Spring Cloud Config Server** (centralized configuration)
  - Spring Cloud Gateway (or a gateway implementation inside `api-gateway`)
- Lombok
- Flyway (DB migrations found in modules)
- Maven (multi-module build)

**Documentation**
- OpenAPI / Swagger annotations (springdoc or springfox style annotations present in `rooms-microservice`)
- Centralized Swagger UI served by `api-gateway`

**Dev & Infra**
- Docker (project includes Dockerfiles? — check each service)
- GitHub Flow + Conventional Commits
- GitHub Pull Requests for code review




---

## 📂 Repository Structure (actual)

```
hotel-microservice-exercise/
│── api-gateway/
│── booking-microservice/
│── config-server/
│── eureka-server/
│── host-microservice/
│── rooms-microservice/
│── employee-microservice/
└── pom.xml   (parent multi-module pom)
```

---

## 🧭 How to Run (local dev quickstart)

1. Build the project (from repo root)
```bash
mvn clean package -DskipTests
```

2. Start infrastructure services first (Eureka, Config, API gateway), then backend microservices:
```bash
# from project root (example using terminal panes)
cd hotel-microservice-exercise
# start eureka
cd eureka-server
mvn spring-boot:run

# in another terminal start config server
cd config-server
mvn spring-boot:run

# then start api-gateway
cd ../api-gateway
mvn spring-boot:run

# then each microservice
cd ../rooms-microservice
mvn spring-boot:run
cd ../booking-microservice
mvn spring-boot:run
# etc.
```

> Note: Each service reads config from Config Server and registers to Eureka. Check each module's `application.yml` for ports and `spring.cloud.config` settings.

---