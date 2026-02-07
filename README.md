# Online Course Management System

Architected a full-stack educational platform managing 100+ user profiles, facilitating seamless student registration, course enrollment, and instructor content management.

## Overview
- Full-stack Spring Boot MVC application with role-based access control
- MySQL persistence with layered architecture (controller, service, repository)
- Instructor-managed course creation and student enrollment workflow

## Tech Stack
- Java 17
- Spring Boot (Web, Thymeleaf, Security, Data JPA)
- MySQL
- Maven

## Features
- User roles: `ADMIN`, `INSTRUCTOR`, `STUDENT`
- Course creation, editing, and deletion (instructor/admin)
- Student enrollment with instructor-specific enrollment visibility
- Admin user management
- Global exception handling with dedicated error pages

## Getting Started

### Prerequisites
- Java 17+
- MySQL running locally

### Database Setup
Create the database:
```sql
CREATE DATABASE ocms;
```

Update credentials in:
`src/main/resources/application.properties`

### Run the Application
Using Maven Wrapper:
```powershell
.\mvnw.cmd spring-boot:run
```

Open:
`http://localhost:8080/login`


Seed on first run:
```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.arguments=--spring.jpa.hibernate.ddl-auto=create --app.seed.enabled=true"
```

## Deploy on Render (Easiest)
1. Push the project to GitHub.
2. Create a new Render Web Service from the repo.
3. Render detects `render.yaml` and uses the Dockerfile.
4. Set environment variables in Render:
   - `DB_URL`
   - `DB_USER`
   - `DB_PASS`
   - `SPRING_PROFILES_ACTIVE=prod`
5. Deploy.

Note: Render does not provide MySQL by default. Use an external MySQL provider or a managed MySQL instance and put its connection values in the env vars above.

## Project Structure
```
src/main/java/com/example/ocms
  |- config
  |- controller
  |- model
  |- repository
  |- service
  `- bootstrap
src/main/resources
  |- static
  `- templates
```

## Notes
- Role-based UI visibility uses `thymeleaf-extras-springsecurity6`.
- Instructors can only manage their own courses.
