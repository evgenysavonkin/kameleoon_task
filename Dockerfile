FROM maven:3-amazoncorretto-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-al2-full as final
WORKDIR /app
COPY --from=builder /app/target/kameleoon-0.0.1-SNAPSHOT.jar api.jar
COPY src/main/resources/application.properties /app/application.properties

CMD ["java", "-jar", "api.jar"]
