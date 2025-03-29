package org.example.lab6_1;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.example.lab6_1.repositories.CustomerRepository;
import org.example.lab6_1.entities.Customer;

import java.util.Optional;

@Testcontainers
@Import(TestcontainersConfiguration.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Enables ordered tests
class Lab61ApplicationTests {

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
            .withUsername("myuser")
            .withPassword("mypassword")
            .withDatabaseName("mydatabase");

    @Autowired
    private CustomerRepository customerRepository;

    // Dynamic properties for Testcontainers
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.jpa.database-platform", () -> "true");
    }

    // Test data
    private static final String TEST_FIRST_NAME = "Angela";
    private static final String TEST_LAST_NAME = "Ribeiro";
    private static final String TEST_EMAIL = "angelamribeiro@ua.pt";
    private static final String UPDATED_FIRST_NAME = "UpdatedAngela";

    private static Long savedCustomerId; // To store the ID of the saved customer

    @Test
    @Order(1) // First test to run
    void testInsertCustomer() {
        // Create a new customer
        Customer customer = new Customer(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_EMAIL);

        // Save the customer
        Customer savedCustomer = customerRepository.save(customer);

        // Assert that the customer was saved successfully
        Assertions.assertNotNull(savedCustomer.getId());
        savedCustomerId = savedCustomer.getId(); // Store the ID for later tests

        System.out.println("Customer inserted with ID: " + savedCustomerId);
    }

    @Test
    @Order(2) // Second test to run
    void testRetrieveCustomer() {
        // Retrieve the customer by ID
        Optional<Customer> retrievedCustomer = customerRepository.findById(savedCustomerId);

        // Assert that the customer was retrieved successfully
        Assertions.assertTrue(retrievedCustomer.isPresent());
        Assertions.assertEquals(TEST_FIRST_NAME, retrievedCustomer.get().getFirstName());
        Assertions.assertEquals(TEST_LAST_NAME, retrievedCustomer.get().getLastName());
        Assertions.assertEquals(TEST_EMAIL, retrievedCustomer.get().getEmail());

        System.out.println("Customer retrieved: " + retrievedCustomer.get());
    }

    @Test
    @Order(3) // Third test to run
    void testUpdateCustomer() {
        // Retrieve the customer by ID
        Optional<Customer> customerToUpdate = customerRepository.findById(savedCustomerId);
        Assertions.assertTrue(customerToUpdate.isPresent());

        // Update the customer's first name
        Customer customer = customerToUpdate.get();
        customer.setFirstName(UPDATED_FIRST_NAME);
        customerRepository.save(customer);

        System.out.println("Customer updated with new first name: " + UPDATED_FIRST_NAME);
    }

    @Test
    @Order(4) // Fourth test to run
    void testRetrieveUpdatedCustomer() {
        // Retrieve the updated customer by ID
        Optional<Customer> updatedCustomer = customerRepository.findById(savedCustomerId);

        // Assert that the customer was updated successfully
        Assertions.assertTrue(updatedCustomer.isPresent());
        Assertions.assertEquals(UPDATED_FIRST_NAME, updatedCustomer.get().getFirstName());
        Assertions.assertEquals(TEST_LAST_NAME, updatedCustomer.get().getLastName());
        Assertions.assertEquals(TEST_EMAIL, updatedCustomer.get().getEmail());

        System.out.println("Updated customer retrieved: " + updatedCustomer.get());
    }

    @Test
    @Order(5) // Fifth test to run
    void testDeleteCustomer() {
        // Delete the customer by ID
        customerRepository.deleteById(savedCustomerId);

        // Verify that the customer was deleted
        Optional<Customer> deletedCustomer = customerRepository.findById(savedCustomerId);
        Assertions.assertFalse(deletedCustomer.isPresent());

        System.out.println("Customer deleted with ID: " + savedCustomerId);
    }
}