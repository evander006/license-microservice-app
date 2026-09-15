FROM eclipse-temurin:17-jre AS build
WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=tools -jar application.jar extract --layers --launcher --destination extracted

FROM eclipse-temurin:17-jre
WORKDIR application
COPY --from=build application/extracted/dependencies/ ./
COPY --from=build application/extracted/spring-boot-loader/ ./
COPY --from=build application/extracted/snapshot-dependencies/ ./
COPY --from=build application/extracted/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]