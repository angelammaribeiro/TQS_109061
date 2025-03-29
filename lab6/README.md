# Integration Testing Tools for Spring Boot Applications

This document summarizes key tools and approaches for integration testing in Spring Boot applications.

## Table of Contents
- [TestContainers](#testcontainers)
- [Database Migrations with Flyway](#database-migrations-with-flyway)
- [REST Assured](#rest-assured)
- [Integration Testing Approaches](#integration-testing-approaches)

## TestContainers

TestContainers is a testing library that provides easy and lightweight APIs for bootstrapping integration tests with real services wrapped in Docker containers.

### Key Benefits
- Creates ephemeral Docker containers specifically for running tests
- Manages the complete lifecycle of containers (start before test, dispose after test)
- Allows testing with the same type of services used in production
- Eliminates the need for mocks or in-memory services like H2

### Setup Requirements
- Docker must be installed and running on your system
- Spring Boot integration is available but not required

### Common Use Case
Setting up a real PostgreSQL database in a container for integration tests:

```java
@SpringBootTest
@Testcontainers
public class CustomerRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test-db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private CustomerRepository repository;

    @Test
    void testSaveAndFindCustomer() {
        // Test implementation
    }
}
```

## Database Migrations with Flyway

Flyway provides database version control through migration scripts, which is particularly useful for integration tests.

### Key Benefits
- Creates consistent database schema and test data
- Uses "convention over configuration" approach
- More practical and scalable than inserting data through code

### File Structure
Flyway looks for migration scripts in specific locations:
```
[project root]/test/resources/db/migration/V001__INIT.sql
```

### Sample Migration Script
```sql
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

INSERT INTO customers (name, email) VALUES 
    ('John Doe', 'john@example.com'),
    ('Jane Smith', 'jane@example.com');
```

### Integration with Tests
When using Flyway with TestContainers, you typically:
1. Remove `spring.jpa.hibernate.ddl-auto` property (let Flyway handle schema)
2. Let Flyway automatically execute migrations on test startup

## REST Assured

REST Assured is a Java DSL for simplifying testing of REST services.

### Key Features
- Fluent API with BDD-style syntax (given/when/then)
- Support for validating response status, headers, and body content
- Compatible with popular testing frameworks
- Special support for Spring MockMvc

### Basic Example
```java
given()
    .param("param1", "value1")
.when()
    .get("/api/resource")
.then()
    .statusCode(200)
    .body("property", equalTo("expectedValue"));
```

### Spring MockMvc Integration
When testing Spring controllers:

```java
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private CarManagerService service;
    
    @Test
    void testGetAllCars() {
        // Mock service behavior
        
        RestAssuredMockMvc
            .given()
                .mockMvc(mockMvc)
            .when()
                .get("/api/cars")
            .then()
                .statusCode(200)
                .body("size()", is(3));
    }
}
```

## Integration Testing Approaches

### Testing Options
1. **MockMvc with Sliced Context**
   - Only loads controller context
   - Mock dependencies
   - Fastest but less realistic

2. **Integration Tests with In-Memory Database (H2)**
   - Faster but different from production DB
   - Potential dialect/feature differences

3. **Full Integration Tests with TestContainers**
   - Most realistic - mirrors production
   - Complete web environment + real database
   - Slower but most reliable

### Complete Integration Test Example
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CarsApiIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("cars_test")
            .withUsername("test")
            .withPassword("test");

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testCreateAndGetCar() {
        // Create a new car
        Car newCar = new Car("Toyota", "Corolla");
        
        String carId = given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(newCar)
        .when()
            .post("/api/cars")
        .then()
            .statusCode(201)
            .extract().path("id");
        
        // Verify car was created
        given()
            .port(port)
        .when()
            .get("/api/cars/{id}", carId)
        .then()
            .statusCode(200)
            .body("maker", equalTo("Toyota"))
            .body("model", equalTo("Corolla"));
    }
}
```

## Best Practices

1. **Layer Testing Appropriately**
   - Unit tests for business logic
   - Integration tests for component interaction
   - End-to-end tests for critical paths

2. **Use Ordered Tests When Necessary**
   - `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)`
   - `@Order(1)`, `@Order(2)`, etc.

3. **Balance Test Speed and Realism**
   - MockMvc for controller logic
   - TestContainers for database interactions
   - Full integration tests for key scenarios

4. **Initialize Test Data Properly**
   - Use Flyway for consistent test data
   - Consider test-specific profiles

5. **Set Appropriate Timeouts**
   - Container startup may take time
   - Avoid flaky tests with reasonable timeouts
