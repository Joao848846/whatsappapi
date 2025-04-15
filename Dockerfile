FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src ./src

EXPOSE 3000

RUN chmod +x mvnw

CMD ["./mvnw", "spring-boot:run"]