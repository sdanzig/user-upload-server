# User Upload Server Application

## Description

This Spring Boot application allows for the upload of CSV files containing user data. The uploaded data is saved to an H2 in-memory database. For verification, the entire content of the database table is written to the logs after each upload.

## Requirements

- Java 17
- Gradle

### Dependencies

Refer to the `build.gradle` for dependencies. The main dependencies include:

- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- H2 Database (runtime only)
- Spring Boot Starter Test (for testing)

## Running the Application

1. Clone the repository.
2. Navigate to the project directory.
3. Run the application:

    ```bash
    ./gradlew bootRun
    ```

## Server Details

- **Port**: The application runs on port `8080`.
- **CORS**: The endpoint is CORS-protected and only accessible from a UI running at `http://localhost:4200`.

## Endpoints

- **Upload CSV**: The application has one main endpoint for uploading CSV files.

## Database

The application uses an H2 in-memory database. Note that the data will be reset upon application restart.

## CSV File Format

The first line in the uploaded CSV file is expected to be a header and will be ignored. The expected format is:

```
first name, last name, email, phone
```

The fields are trimmed of white space before being stored

## Testing

To run the unit tests, execute:

```bash
./gradlew test
```

The tests are configured to use the JUnit Platform.

## Design Pattern Usage and Justification

MVC (Model-View-Controller): I'm using MVC where User is the Model, UserController is the Controller, and the REST API client acts as the View. This makes my code organized and easier to update later.

Repository Pattern: I've implemented UserRepository to manage data access. It serves as a middleman between the business logic and the database, which simplifies running queries or tests.

Dependency Injection: I'm using Spring's @Autowired to inject UserService into UserController. This makes the code more modular and testing easier.

Singleton Pattern: By using Spring's @Service and @RestController, I ensure there’s only a single instance of each component. This is a resource-efficient way to have centralized control.

Factory Pattern: I use LoggerFactory.getLogger() to manage logging. It centralizes the creation of loggers, improving maintainability.

Builder Pattern: This made an appearance when building API responses, which helps with readability and is less error prone.

Inversion of Control (IoC): Spring Framework is taking care of a lot of object creation and configuration, speeding up development.