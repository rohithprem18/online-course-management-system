FROM eclipse-temurin:17-jre

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
COPY pom.xml pom.xml
COPY src/ src/

RUN chmod +x mvnw && ./mvnw -q -DskipTests package

EXPOSE 8080

CMD ["java", "-jar", "target/online-course-management-system-0.0.1-SNAPSHOT.jar"]
