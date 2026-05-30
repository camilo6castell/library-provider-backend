<div align="center">

<img src="https://img.shields.io/badge/Library_Provider-Backend-0d1117?style=for-the-badge&logo=bookstack&logoColor=white" alt="Library Provider" height="60"/>

# Library Provider — Backend

**Reactive REST API built on Hexagonal Architecture, Domain-Driven Design, and Event Sourcing.**

[![Java](https://img.shields.io/badge/Java_21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.2-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring WebFlux](https://img.shields.io/badge/Spring_WebFlux-6DB33F?style=flat-square&logo=spring&logoColor=white)](https://docs.spring.io/spring-framework/reference/web/webflux.html)
[![MongoDB](https://img.shields.io/badge/MongoDB_Atlas-47A248?style=flat-square&logo=mongodb&logoColor=white)](https://www.mongodb.com/atlas)
[![Gradle](https://img.shields.io/badge/Gradle_8-02303A?style=flat-square&logo=gradle&logoColor=white)](https://gradle.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)](https://www.docker.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](./LICENSE)

</div>

---

## Table of Contents

- [Overview](#-overview)
- [Architecture](#-architecture)
- [Domain Model](#-domain-model)
- [Event Sourcing](#-event-sourcing)
- [Quoting Engine](#-quoting-engine)
- [API Reference](#-api-reference)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Configuration](#-configuration)
- [Contributing](#-contributing)
- [License](#-license)

---

## 📡 Overview

Library Provider is a backend service for managing a library catalogue and computing text purchase quotes. It is built as a **reactive, non-blocking API** using Spring WebFlux, with an architecture that deliberately separates domain logic from infrastructure concerns through **Hexagonal Architecture** (Ports & Adapters), **Domain-Driven Design**, and a fully custom **Event Sourcing** implementation backed by MongoDB Atlas.

The system models two main domain entities — `User` and `Text` — and exposes use cases for user registration, text cataloguing, and tiered pricing with seniority and volume discounts.

---

## 🏛️ Architecture

The project is organized as a **Gradle multi-module build** with explicit layer boundaries enforced at the module-dependency level. The domain model has zero external dependencies. Use cases depend only on the domain model. Infrastructure modules implement the ports defined in the use case layer, never the other way around.

```
┌─────────────────────────────────────────────────────────────────────┐
│                          REST Client                                │
└───────────────────────────────┬─────────────────────────────────────┘
                                │ HTTP
┌───────────────────────────────▼─────────────────────────────────────┐
│            infrastructure / entry-points / reactive-web             │
│        RouterRest (functional routes) → Handler → Commands          │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
┌───────────────────────────────▼─────────────────────────────────────┐
│                    application / app-main                           │
│                  UseCaseConfig (Spring DI wiring)                   │
└──────────┬────────────────────────────────────────┬─────────────────┘
           │                                        │
┌──────────▼──────────────────┐  ┌──────────────────▼────────────────┐
│      domain / usecase       │  │  infrastructure / driven-adapters  │
│  CreateUserUseCase          │  │  MongoRepositoryAdapter            │
│  SaveAndQuoteTextUseCase    │◄─┤  implements IUserRepository        │
│  CreateTextUseCase          │  │  implements ITextRepository        │
│  QuoteTextsByBudgetUseCase  │  └──────────────┬─────────────────────┘
│  QuoteVariousTextsUseCase   │                 │ Spring Data Reactive
│  QuoteBatchQuoteUseCase     │  ┌──────────────▼─────────────────────┐
└──────────┬──────────────────┘  │          MongoDB Atlas             │
           │ depends on          │      events collection             │
┌──────────▼──────────────────┐  │   (serialized DomainEvents)        │
│      domain / model         │  └────────────────────────────────────┘
│  AggregateRoot, DomainEvent │
│  User, Text aggregates      │
│  Entities, Value Objects    │
│  Commands, Events           │
└─────────────────────────────┘
```

### Gradle modules

| Module | Layer | Responsibility |
|---|---|---|
| `:model` | Domain | Aggregates, entities, value objects, domain events, commands |
| `:usecase` | Domain | Use cases and repository port interfaces (`IUserRepository`, `ITextRepository`) |
| `:mongo-repository` | Infrastructure | MongoDB event store adapter (implements ports) |
| `:reactive-web` | Infrastructure | HTTP entry point — functional router, handler, DTOs |
| `:serializer` | Infrastructure | JSON serializer/deserializer for domain events |
| `:app-main` | Application | Spring Boot entry point + dependency injection wiring |

> Only `:app-main` carries the `org.springframework.boot` plugin and produces an executable JAR. All other modules are plain Java libraries managed by the Spring Boot BOM for consistent dependency versions.

---

## 🧩 Domain Model

The domain is designed following **DDD tactical patterns** with a custom generic framework built from scratch — no external DDD library is used.

### Generic framework (`:model` — `generic` package)

| Class | Role |
|---|---|
| `AggregateRoot<I>` | Base for all aggregate roots. Owns the `ChangeEventSubscriber` that buffers uncommitted events. |
| `Entity<I>` | Base for domain entities with a typed identity. |
| `DomainEvent` | Abstract base for all events — carries `eventId`, `aggregateRootId`, `type`, `occurredOn`, `version`. |
| `Identity` | Base for typed identifiers (wraps a `String` UUID). |
| `IValueObject<T>` | Marker interface for all value objects. |
| `Command` | Base type for input commands. |
| `ChangeEventSubscriber` | Internal pub/sub mechanism — subscribes behaviors, versions and buffers events. |
| `EventChange` | Maps a `DomainEvent` subtype to an aggregate state mutation via typed `Consumer`. |

### Aggregates

**`User`** — models a library client.

| Field | Value Object | Invariants |
|---|---|---|
| `email` | `Email` | RFC-compliant format |
| `password` | `Password` | 8–64 chars, requires uppercase, lowercase, digit, special char |
| `entryDate` | `EntryDate` | ISO date (`yyyy-MM-dd`), used for seniority discount calculation |

**`Text`** — models a catalogued item (book or novel).

| Field | Value Object | Invariants |
|---|---|---|
| `title` | `Title` | Non-blank string |
| `type` | `Type` | One of `BOOK`, `NOVEL` |
| `initialPrice` | `InitialPrice` | Positive `Float` |

Value objects enforce their invariants at construction time by throwing `IllegalArgumentException` — invalid state can never be instantiated.

### Entities

| Entity | Description |
|---|---|
| `TextQuote` | Single-item pricing quote. Calculates subtotal, discount, and total based on text type, demand multiplier, and sale mode. |
| `BatchQuote` | Multi-item quote. Supports mixed book/novel batches, 10-item wholesale threshold, seniority discount, and budget-constrained greedy selection. |

---

## 📋 Event Sourcing

The system does **not persist aggregate state directly**. Every state change is recorded as an immutable `DomainEvent` document in MongoDB. State is reconstructed on demand by replaying the full event stream for a given `aggregateRootId`.

### Event store document (`EventSaved`)

```java
@Document(collection = "events")
public class EventSaved {
    private String id;             // MongoDB ObjectId
    private String aggregateRootId; // UUID of the owning aggregate
    private String type;           // Fully-qualified event class name
    private LocalDateTime occurredOn;
    private String body;           // DomainEvent serialized as JSON
}
```

### Write path

```
Command received by Handler
  → Use case instantiates or reconstitutes aggregate from event stream
  → Aggregate calls appendEvent(domainEvent).apply()
      → ChangeEventSubscriber timestamps and versions the event
      → Event added to uncommitted changes buffer
      → State mutation dispatched to registered EventChange handlers
  → Use case iterates getUncommittedChanges()
  → JSONMapper serializes each event → saved as EventSaved document
  → markChangesAsCommitted() clears the buffer
```

### Read path (aggregate reconstitution)

```java
// 1. Retrieve ordered event stream reactively
Flux<DomainEvent> events = repository.getEventsByAggregateRootId(userId);

// 2. Replay events to restore state
User user = User.from(userId, events.collectList().block());
// Each DomainEvent is passed to applyEvent() →
// ChangeEventSubscriber dispatches to UserBehavior subscribers
```

### Domain events

| Aggregate | Event | Trigger |
|---|---|---|
| `User` | `UserCreated` | User registration |
| `User` | `TextQuoted` | Single text quote |
| `User` | `BudgetTextsQuoted` | Budget-constrained quote |
| `User` | `BatchTextsQuoted` | Batch quote (books + novels) |
| `User` | `VariousTextQuotedEvent` | Mixed-type quote |
| `User` | `TextSavedAndQuoted` | Text saved and quoted atomically |
| `Text` | `TextCreated` | Text added to catalogue |

---

## 💰 Quoting Engine

All pricing logic lives exclusively in the domain model — no pricing rules appear in use cases or infrastructure layers.

### Demand multipliers (applied to `initialPrice`)

| Text type | Multiplier |
|---|---|
| Book | × 1.33 |
| Novel | × 2.00 |

### Sale modifiers

| Sale mode | Applied to | Modifier |
|---|---|---|
| Retail (< 10 items) | Subtotal | × 1.02 |
| Wholesale (≥ 10 items) | Subtotal | × 0.9985 |

### Seniority discount (applied to totals)

| Client tenure | Multiplier |
|---|---|
| < 1 year | 1.00 (no discount) |
| 1–2 years | 0.88 (12% off) |
| > 2 years | 0.83 (17% off) |

### Budget-constrained quote (`QuoteTextsByBudget`)

The `BatchQuote` entity sorts the requested texts by price descending and applies a greedy selection algorithm: items are added one by one until the budget is exhausted. When the list contains at least one book and one novel, the cheapest of each type is discounted at the `WHOLESALE` rate. The response includes the remaining budget (`change`).

---

## 📋 API Reference

All endpoints are prefixed with `/api/v1`. Routing uses Spring WebFlux's **functional router** (`RouterFunction`) — there are no annotated controllers.

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/users` | Register a new user account |
| `POST` | `/api/v1/texts/quote` | Save a text to the catalogue and quote it for a user |
| `POST` | `/api/v1/texts/quote-by-budget` | Return the best set of texts within a given budget |

> A Thunder Client collection with sample requests is available in [`postman-thunder/`](./postman-thunder/).

---

### `POST /api/v1/users`

**Request body**

```json
{
  "email": "user@example.com",
  "password": "SecureP4ss!",
  "entryDate": "2022-03-15"
}
```

**Response — `200 OK`**

```json
{
  "success": true,
  "aggregateRootId": "550e8400-e29b-41d4-a716-446655440000"
}
```

**Response — `400 Bad Request`** (email already registered, invalid password, etc.)

```json
{
  "success": false,
  "aggregateRootId": "A user with email 'user@example.com' already exists"
}
```

---

### `POST /api/v1/texts/quote`

**Request body**

```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Clean Code",
  "textType": "BOOK",
  "initialPrice": 100.0
}
```

**Response — `200 OK`**

```json
{
  "title": "Clean Code",
  "textType": "BOOK",
  "subtotal": 135.46,
  "discount": "NONE",
  "total": 135.46
}
```

---

### `POST /api/v1/texts/quote-by-budget`

**Request body**

```json
{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "textsIndices": [0, 1, 2, 3],
  "budget": 500.0
}
```

**Response — `200 OK`**

```json
{
  "change": 42.10,
  "total": 457.90,
  "discount": "SENIORITY",
  "subtotal": 551.69,
  "texts": [
    { "title": "Clean Code", "textType": "BOOK", "subtotal": 135.46, "discount": "WHOLESALE", "total": 135.27 },
    { "title": "The Pragmatic Programmer", "textType": "BOOK", "subtotal": 119.70, "discount": "NONE", "total": 119.70 }
  ]
}
```

---

## 🛠️ Tech Stack

| Layer | Technology | Version |
|---|---|---|
| Language | Java | 21 |
| Framework | Spring Boot | 3.2.5 |
| Reactive runtime | Spring WebFlux (Project Reactor) | — |
| Database | MongoDB Atlas | — |
| Reactive Mongo driver | Spring Data MongoDB Reactive | — |
| Build tool | Gradle (multi-module) | 8.x |
| Containerization | Docker (multi-stage build) | — |
| Testing | JUnit 5 | — |

---

## 📁 Project Structure

```
.
├── settings.gradle                          # Multi-module declarations
├── Dockerfile                               # Multi-stage build (JDK 21 build → JRE 21 runtime)
├── .env.example                             # Required environment variables template
├── gradlew / gradlew.bat
└── src/
    ├── domain/
    │   ├── model/                           # :model — zero external dependencies
    │   │   └── .../com/libraryproviderbackend/
    │   │       ├── generic/                 # AggregateRoot, DomainEvent, Entity, IValueObject…
    │   │       ├── user/
    │   │       │   ├── User.java            # User aggregate root
    │   │       │   ├── UserBehavior.java    # Event → state mutation handler
    │   │       │   ├── commands/            # CreateUserCommand, SaveAndQuoteTextCommand…
    │   │       │   ├── events/              # UserCreated, TextQuoted, BudgetTextsQuoted…
    │   │       │   ├── entity/              # TextQuote, BatchQuote
    │   │       │   └── values/              # Email, Password, EntryDate, Total, Discount…
    │   │       └── text/
    │   │           ├── Text.java            # Text aggregate root
    │   │           ├── commands/            # CreateTextCommand
    │   │           ├── events/              # TextCreated
    │   │           └── values/              # Title, Type, InitialPrice, TextTypeEnum
    │   └── usecase/                         # :usecase
    │       └── .../usecase/
    │           ├── CreateUserUseCase.java
    │           ├── SaveAndQuoteTextUseCase.java
    │           ├── CreateTextUseCase.java
    │           ├── QuoteTextsByBudgetUseCase.java
    │           ├── QuoteVariousTextsUseCase.java
    │           ├── QuoteBatchQuoteUseCase.java
    │           └── generic/
    │               ├── UseCaseForCommandMono.java    # Command → Mono<DomainEvent>
    │               ├── UseCaseForCommandFlux.java    # Command → Flux<DomainEvent>
    │               └── gateway/
    │                   ├── IUserRepository.java      # Port (interface)
    │                   └── ITextRepository.java      # Port (interface)
    ├── infrastructure/
    │   ├── entry-points/
    │   │   └── reactive-web/                # :reactive-web
    │   │       └── .../
    │   │           ├── RouterRest.java      # Functional router (RouterFunction)
    │   │           ├── Handler.java         # Request → Command → use case delegation
    │   │           ├── HandlerConfig.java
    │   │           ├── CorsConfig.java
    │   │           └── Dtos/                # Response DTOs per use case
    │   ├── driven-adapters/
    │   │   └── mongo-repository/            # :mongo-repository
    │   │       └── .../
    │   │           ├── MongoRepositoryAdapter.java  # Adapter implementing both ports
    │   │           ├── data/EventSaved.java          # MongoDB event envelope document
    │   │           └── config/
    │   │               ├── IMongoRepository.java    # ReactiveMongoRepository
    │   │               └── ApplicationConfig.java
    │   └── helpers/
    │       └── serializer/                  # :serializer
    │           └── .../
    │               ├── IJSONMapper.java
    │               ├── JSONMapper.java      # DomainEvent ↔ JSON (Jackson)
    │               └── SerializationException.java
    └── application/
        └── app-main/                        # :app-main — only bootable module
            └── .../
                ├── Main.java
                └── config/UseCaseConfig.java  # Wires use cases via component scan
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- Docker (for the containerized option)
- A MongoDB Atlas cluster, or a local MongoDB 6+ instance

### 1. Clone the repository

```bash
git clone https://github.com/camilo6castell/library-provider-backend.git
cd library-provider-backend
```

### 2. Configure environment variables

```bash
cp .env.example .env
# Edit .env and set MONGODB_URI to your connection string
```

### Option A — Docker (recommended)

```bash
docker build -t library-provider-backend .

docker run -p 8080:8080 \
  -e MONGODB_URI="mongodb+srv://<user>:<pass>@cluster.mongodb.net/library-provider" \
  library-provider-backend
```

### Option B — Gradle

```bash
# Build all modules
./gradlew build

# Run the application
MONGODB_URI="mongodb+srv://<user>:<pass>@cluster.mongodb.net/library-provider" \
  ./gradlew :app-main:bootRun
```

The API will be available at `http://localhost:8080`.

---

## ⚙️ Configuration

All configuration is centralized in `src/application/app-main/src/main/resources/application.yaml`. Values are injected from environment variables with sensible local defaults.

```yaml
spring:
  data:
    mongodb:
      uri: ${MONGODB_URI:mongodb://localhost:27017/library-provider}

server:
  port: ${PORT:8080}

app:
  cors:
    allowed-origins:
      - ${CORS_ORIGIN_1:http://localhost:5173}
      - ${CORS_ORIGIN_2:http://localhost:4200}
```

### Environment variables

| Variable | Required | Default | Description |
|---|---|---|---|
| `MONGODB_URI` | Yes | `mongodb://localhost:27017/library-provider` | MongoDB connection string |
| `PORT` | No | `8080` | HTTP server port |
| `CORS_ORIGIN_1` | No | `http://localhost:5173` | First allowed CORS origin |
| `CORS_ORIGIN_2` | No | `http://localhost:4200` | Second allowed CORS origin |
| `CORS_ORIGIN_3` | No | — | Third allowed CORS origin |

> See `.env.example` for a ready-to-copy template. **Never commit `.env` or any file containing real credentials.**

---

## 🤝 Contributing

Contributions are welcome. Please open an issue before submitting a pull request to discuss the proposed change.

1. Fork the repository
2. Create a feature branch: `git checkout -b feat/your-feature`
3. Commit using [Conventional Commits](https://www.conventionalcommits.org/): `git commit -m "feat: add your feature"`
4. Push and open a pull request against `develop`

### Commit conventions

| Prefix | Use for |
|---|---|
| `feat:` | New feature |
| `fix:` | Bug fix |
| `refactor:` | Code change that is not a fix or feature |
| `docs:` | Documentation only |
| `test:` | Adding or updating tests |
| `chore:` | Build process, dependency updates |

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](./LICENSE) file for details.

Copyright © 2025 Camilo Andres Castellanos Herrera

---

<div align="center">

*Domain-first. Infrastructure-agnostic. Event-driven by design.*

</div>