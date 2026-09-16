# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk AS builder
WORKDIR /workspace

COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

COPY src src
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /application
COPY --from=builder /workspace/target/eureka-server-0.0.1-SNAPSHOT.jar application.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar", "application.jar"]
