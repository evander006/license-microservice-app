# syntax=docker/dockerfile:1
# Shared Gradle build for licensing-service, config-server, and organization.
# Compose Bake reuses this stage across those three images.

FROM eclipse-temurin:17-jdk AS gradle-builder
WORKDIR /workspace

COPY gradlew settings.gradle build.gradle gradle.properties ./
COPY gradle gradle
COPY config-server/build.gradle config-server/build.gradle
COPY organization/build.gradle organization/build.gradle

# Host checkouts on Windows can leave CRLF on the wrapper script.
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

COPY src src
COPY config-server/src config-server/src
COPY organization/src organization/src

RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon -x test \
    bootJar :config-server:bootJar :organization:bootJar

FROM eclipse-temurin:17-jre AS licensing-service
WORKDIR /application
COPY --from=gradle-builder /workspace/build/libs/microservice-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]

FROM eclipse-temurin:17-jre AS config-server
WORKDIR /application
COPY --from=gradle-builder /workspace/config-server/build/libs/config-server-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8071
ENTRYPOINT ["java", "-jar", "application.jar"]

FROM eclipse-temurin:17-jre AS organization
WORKDIR /application
COPY --from=gradle-builder /workspace/organization/build/libs/organization-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "application.jar"]
