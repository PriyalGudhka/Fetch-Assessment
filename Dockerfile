FROM maven:3.8.8-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src /app/src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/spring-docker.jar spring-docker.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spring-docker.jar"]
