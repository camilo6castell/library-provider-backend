<div align="center">

<img src="https://img.shields.io/badge/Library_Provider-Backend-0d1117?style=for-the-badge&logo=bookstack&logoColor=white" alt="Library Provider" height="60"/>

# Library Provider — Backend

**Reactive REST API built on Hexagonal Architecture, Domain-Driven Design, and Event Sourcing.**

[![Java](https://img.shields.io/badge/Java_17-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot_3.2-6DB33F?style=flat-square&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring WebFlux](https://img.shields.io/badge/Spring_WebFlux-6DB33F?style=flat-square&logo=spring&logoColor=white)](https://docs.spring.io/spring-framework/reference/web/webflux.html)
[![MongoDB](https://img.shields.io/badge/MongoDB_Atlas-47A248?style=flat-square&logo=mongodb&logoColor=white)](https://www.mongodb.com/atlas)
[![Gradle](https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white)](https://gradle.org/)
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

Library Provider is a backend service for managing a library's catalog and computing text purchase quotes. It is built as a **reactive, non-blocking API** using Spring WebFlux, with an architecture that deliberately separates domain logic from infrastructure concerns through **Hexagonal Architecture** (Ports & Adapters), **Domain-Driven Design**, and a fully custom **Event Sourcing** implementation.

The system models two main domain entities — `User` and `Text` — and exposes use cases for user registration, text cataloging, and pricing with tiered discount logic.

---

## 🏛️ Architecture

The project is organized as a **Gradle multi-module build** with explicit layer boundaries enforced by module-level dependency isolation. No infrastructure code can depend on a use case directly, and the domain model has zero infrastructure dependencies.

```
┌────────────────────────────────────────────────────────────────────┐
│                        REST Client                                 │
└──────────────────────────────┬─────────────────────────────────────┘
                               │ HTTP
┌──────────────────────────────▼─────────────────────────────────────┐
│               infrastructure/entry-points/reactive-web             │
│          RouterRest (functional routes) → Handler → Commands       │
└──────────────────────────────┬─────────────────────────────────────┘
                               │
┌──────────────────────────────▼─────────────────────────────────────┐
│                   application/app-main                             │
│               UseCaseConfig (Spring DI wiring)                     │
└──────────┬───────────────────────────────────────────┬─────────────┘
           │                                           │
┌──────────▼──────────────────┐   ┌────────────────────▼────────────┐
│    domain/usecase           │   │  infrastructure/driven-adapters  │
│  CreateUserUseCase          │   │  MongoRepositoryAdapter          │
│  SaveAndQuoteTextUseCase    │◄──┤  implements IUserRepository      │
│  CreateTextUseCase          │   │  implements ITextRepository      │
│  QuoteTextsByBudgetUseCase  │   └──────────────┬──────────────────┘
│  QuoteVariousTextsUseCase   │                  │ Reactive Mongo
│  QuoteBatchQuoteUseCase     │   ┌──────────────▼──────────────────┐
└──────────┬──────────────────┘   │       MongoDB Atlas             │
           │ depends on           │   events collection             │
┌──────────▼──────────────────┐   │   (serialized DomainEvents)     │
│      domain/model           │   └─────────────────────────────────┘
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
| `:usecase` | Domain | Use cases and repository port interfaces |
| `:mongo-repository` | Infrastructure | MongoDB event store adapter (implements ports) |
| `:reactive-web` | Infrastructure | HTTP entry point — router, handler, DTOs |
| `:serializer` | Infrastructure | JSON serializer/deserializer for domain events |
| `:app-main` | Application | Spring Boot entry point + DI wiring (`UseCaseConfig`) |

The `:model` module has **zero external dependencies**. Use cases depend only on `:model`. Infrastructure modules depend on both, but the domain never depends on infrastructure.

---

## 🧩 Domain Model

The domain is designed following **DDD tactical patterns** with a custom generic framework built from scratch.

### Generic infrastructure (`:model` — `generic` package)

| Class | Role |
|---|---|
| `AggregateRoot<I>` | Base class for all aggregate roots. Manages uncommitted `DomainEvent` list via `ChangeEventSubscriber`. |
| `Entity<I>` | Base class for domain entities with typed identity. |
| `DomainEvent` | Abstract base for all events — carries `eventId`, `aggregateRootId`, `type`, `occurredOn`, `version`. |
| `Identity` | Base for typed identifiers (wraps a `String` UUID). |
| `IValueObject<T>` | Marker interface for all value objects. |
| `Command` / `InitialCommand` | Base types for input commands. |
| `ChangeEventSubscriber` | Internal pub/sub mechanism — subscribes behaviors, applies and buffers events. |
| `EventChange` | Functional interface — maps a `DomainEvent` subtype to an aggregate state mutation. |

### Aggregates

**`User`** — aggregate root that models a library client.

```java
public class User extends AggregateRoot<UserId> {
    public Email email;          // value object with format validation
    public Password password;    // value object: 8–64 chars, upper, lower, digit, special char
    public EntryDate entryDate;  // value object: ISO date, used for seniority discount calculation

    // Reconstructed from event history:
    public static User from(String userId, List<DomainEvent> domainEvents) { ... }
}
```

**`Text`** — aggregate root that models a catalogued item (book or novel).

Value objects enforce invariants at construction time — for example, `Password` validates length, case, digit presence, and special characters before allowing instantiation.

### Entities

| Entity | Description |
|---|---|
| `TextQuote` | A single-item pricing quote. Calculates subtotal, discount, and total based on type and demand multiplier. |
| `BatchQuote` | A multi-item quote. Supports mixed book/novel batches, wholesale discount threshold, seniority discount, and budget-constrained selection. |
| `Quote` | Lightweight quote wrapper. |

---

## 📋 Event Sourcing

The system does **not use a relational table to store current state**. Every state change is recorded as an immutable `DomainEvent` document in MongoDB. State is reconstructed by replaying the event stream for a given aggregate root.

### Event store document (`EventSaved`)

```java
@Document(collection = "events")
public class EventSaved {
    private String aggregateRootId;  // UUID of the owning aggregate
    private String type;             // event class name (e.g. "UserCreated")
    private LocalDateTime occurredOn;
    private String body;             // full DomainEvent serialized as JSON
}
```

### Write path

```
Command received
  → Use Case instantiates or reconstitutes aggregate
  → Aggregate calls appendEvent(DomainEvent).apply()
  → ChangeEventSubscriber buffers the event + triggers state mutation
  → Use Case iterates getUncommittedChanges()
  → Each DomainEvent serialized by JSONMapper → saved as EventSaved in MongoDB
  → markChangesAsCommitted() clears buffer
```

### Read path (aggregate reconstitution)

```java
// MongoRepositoryAdapter — retrieves event stream reactively
Flux<DomainEvent> events = getEventsByAggregateRootId(userId);

// Use case reconstitutes aggregate by replaying events
User user = User.from(userId, events.collectList().block());
// Each DomainEvent is passed to applyEvent() → ChangeEventSubscriber dispatches to UserBehavior
```

### Domain events

| Aggregate | Event | Trigger |
|---|---|---|
| `User` | `UserCreated` | User registration |
| `User` | `TextQuoted` | Single text quote requested |
| `User` | `BatchTextsQuoted` | Batch quote (books + novels) |
| `User` | `VariousTextQuotedEvent` | Various-text quote |
| `User` | `BudgetTextsQuoted` | Budget-constrained quote |
| `User` | `TextSavedAndQuoted` | Text saved + quoted in single operation |
| `Text` | `TextCreated` | Text added to catalog |

---

## 💰 Quoting Engine

The pricing logic is fully encapsulated in the domain model, with no pricing rules in the infrastructure or use case layers.

### Demand multipliers (applied to `initialPrice`)

| Text type | Multiplier |
|---|---|
| Book | ×1.33 |
| Novel | ×2.00 |

### Sale modifiers

| Sale type | Modifier |
|---|---|
| Retail (< 10 items) | subtotal × 1.02 |
| Wholesale (≥ 10 items) | subtotal × 0.9985 |

### Seniority discount (applied to batch totals)

| Client seniority | Multiplier |
|---|---|
| < 1 year | 1.00 (no discount) |
| 1–2 years | 0.88 (12% off) |
| > 2 years | 0.83 (17% off) |

### Budget-constrained quote

When quoting within a budget, the `BatchQuote` entity sorts texts by price descending and applies a greedy selection, including mandatory wholesale discounts for mixed book/novel batches (cheapest of each type gets `WHOLESALE`).

---

## 📋 API Reference

All endpoints are prefixed with `/api/v1`. The routing uses Spring WebFlux's **functional router** (`RouterFunction`) instead of annotated controllers.

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/createUser` | Register a new user |
| `POST` | `/api/v1/saveAndQuoteText` | Save a text to the catalog and quote it for a user |

### `POST /api/v1/createUser`

```json
{
  "email": "user@example.com",
  "password": "SecureP4ss!",
  "entryDate": "2022-03-15"
}
```

Response:
```json
{
  "success": true,
  "aggregateRootId": "550e8400-e29b-41d4-a716-446655440000"
}
```

### `POST /api/v1/saveAndQuoteText`

```json
{
  "userID": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Clean Code",
  "type": "BOOK",
  "initialPrice": 100.0
}
```

Response includes the computed `TextQuote` with subtotal, discount type, and final total.

> A Postman collection with all available requests is available [here](https://drive.google.com/file/d/1-7eZIgGvCbVpv7aqJV1679djr9F5GMnn/view?usp=sharing).

---

## 🛠️ Tech Stack

| Layer | Technology | Version |
|---|---|---|
| Language | Java | 17 |
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
├── settings.gradle                         # Multi-module declarations
├── Dockerfile                              # Multi-stage build (JDK build → JRE runtime)
├── gradlew / gradlew.bat
└── src/
    ├── domain/
    │   ├── model/                          # :model — zero external dependencies
    │   │   └── .../com/libraryproviderbackend/
    │   │       ├── generic/                # AggregateRoot, DomainEvent, Entity, IValueObject...
    │   │       ├── user/
    │   │       │   ├── User.java           # User aggregate root
    │   │       │   ├── UserBehavior.java   # Event → state mutation handler
    │   │       │   ├── commands/           # CreateUserCommand, SaveAndQuoteTextCommand...
    │   │       │   ├── events/             # UserCreated, TextQuoted, BatchTextsQuoted...
    │   │       │   ├── entity/             # TextQuote, BatchQuote, Quote
    │   │       │   └── values/             # Email, Password, EntryDate, Total, Discount...
    │   │       └── text/
    │   │           ├── Text.java           # Text aggregate root
    │   │           ├── commands/           # CreateTextCommand
    │   │           ├── events/             # TextCreated
    │   │           └── values/             # Title, Type, InitialPrice, TextTypeEnum
    │   └── usecase/                        # :usecase
    │       └── .../usecase/
    │           ├── CreateUserUseCase.java
    │           ├── SaveAndQuoteTextUseCase.java
    │           ├── CreateTextUseCase.java
    │           ├── QuoteTextsByBudgetUseCase.java
    │           ├── QuoteVariousTextsUseCase.java
    │           ├── QuoteBatchQuoteUseCase.java
    │           └── generic/
    │               ├── UseCaseForCommandMono.java   # Base: Command → Mono<DomainEvent>
    │               ├── UseCaseForCommandFlux.java   # Base: Command → Flux<DomainEvent>
    │               └── gateway/
    │                   ├── IUserRepository.java     # Port interface
    │                   └── ITextRepository.java     # Port interface
    ├── infrastructure/
    │   ├── entry-points/
    │   │   └── reactive-web/               # :reactive-web
    │   │       └── .../
    │   │           ├── RouterRest.java     # Functional router (RouterFunction)
    │   │           ├── Handler.java        # Request → Command → Use case delegation
    │   │           ├── HandlerConfig.java
    │   │           ├── CorsConfig.java
    │   │           └── Dtos/               # Request/Response DTOs
    │   ├── driven-adapters/
    │   │   └── mongo-repository/           # :mongo-repository
    │   │       └── .../
    │   │           ├── MongoRepositoryAdapter.java  # Implements IUserRepository + ITextRepository
    │   │           ├── data/EventSaved.java          # MongoDB document — event envelope
    │   │           └── config/
    │   │               ├── IMongoRepository.java    # ReactiveMongoRepository
    │   │               └── ApplicationConfig.java
    │   └── helpers/
    │       └── serializer/                 # :serializer
    │           └── .../
    │               ├── IJSONMapper.java
    │               └── JSONMapper.java     # serialize/deserialize DomainEvent ↔ JSON
    └── application/
        └── app-main/                       # :app-main — Spring Boot entry point
            └── .../
                ├── Main.java
                └── config/UseCaseConfig.java  # Wires use cases with their port implementations
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Docker (optional, for containerized runs)
- A MongoDB Atlas cluster (or local MongoDB instance)

### Option 1 — Docker (recommended)

```bash
git clone https://github.com/camilo6castell/library-provider-backend.git
cd library-provider-backend

docker build -t library-provider-backend .

docker run -p 8080:8080 \
  -e SPRING_DATA_MONGODB_URI="mongodb+srv://<user>:<pass>@cluster.mongodb.net/<db>" \
  library-provider-backend
```

### Option 2 — Gradle

```bash
git clone https://github.com/camilo6castell/library-provider-backend.git
cd library-provider-backend

# Build the full project (all modules)
./gradlew build

# Run the application
./gradlew :app-main:bootRun
```

---

## ⚙️ Configuration

### MongoDB URI

Set the connection string in `src/infrastructure/driven-adapters/mongo-repository/src/main/resources/application.properties`:

```properties
spring.data.mongodb.uri=mongodb+srv://<user>:<password>@<cluster>/<database>?retryWrites=true&w=majority
```

Or override via environment variable when running with Docker:

```bash
-e SPRING_DATA_MONGODB_URI="mongodb+srv://..."
```

### Environment variables

| Variable | Description |
|---|---|
| `SPRING_DATA_MONGODB_URI` | MongoDB Atlas connection string |

---

## 🤝 Contributing

Contributions are welcome. Please open an issue before submitting a pull request to discuss the proposed change.

1. Fork the repository
2. Create a feature branch: `git checkout -b feat/your-feature`
3. Commit using [Conventional Commits](https://www.conventionalcommits.org/): `git commit -m 'feat: add your feature'`
4. Push and open a pull request

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](./LICENSE) file for details.

Copyright © 2025 Camilo Andres Castellanos Herrera

---

<div align="center">

*Domain-first. Infrastructure-agnostic. Event-driven by design.*

</div>
