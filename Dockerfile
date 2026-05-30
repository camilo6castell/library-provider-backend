# ─── Stage 1: Build ──────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy Gradle wrapper first so the dependency layer is cached
COPY gradlew gradlew.bat ./
COPY gradle ./gradle
RUN chmod +x gradlew

# Copy build scripts before sources (better layer caching)
COPY settings.gradle ./
COPY src/domain/model/build.gradle             src/domain/model/
COPY src/domain/usecase/build.gradle           src/domain/usecase/
COPY src/application/app-main/build.gradle     src/application/app-main/
COPY src/infrastructure/driven-adapters/mongo-repository/build.gradle  src/infrastructure/driven-adapters/mongo-repository/
COPY src/infrastructure/entry-points/reactive-web/build.gradle         src/infrastructure/entry-points/reactive-web/
COPY src/infrastructure/helpers/serializer/build.gradle                src/infrastructure/helpers/serializer/

# Pre-download dependencies
RUN ./gradlew dependencies --no-daemon || true

# Copy source code
COPY src ./src

RUN ./gradlew :app-main:bootJar --no-daemon -x test

# ─── Stage 2: Runtime ────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

# Non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

WORKDIR /app

COPY --from=build /app/src/application/app-main/build/libs/library-provider-backend.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", "app.jar"]
