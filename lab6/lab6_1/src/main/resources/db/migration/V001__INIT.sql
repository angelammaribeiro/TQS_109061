-- V001__INIT.sql

-- Create the Customer table
CREATE TABLE customer (
                          id SERIAL PRIMARY KEY,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) UNIQUE NOT NULL
);

-- Insert sample data
INSERT INTO customer (first_name, last_name, email) VALUES
                                                        ('John', 'Doe', 'john.doe@example.com'),
                                                        ('Jane', 'Smith', 'jane.smith@example.com'),
                                                        ('Alice', 'Johnson', 'alice.johnson@example.com'),
                                                        ('Angela', 'Ribeiro', 'angelammaribeiro@ua.pt'),
                                                        ('Rita', 'Silva', 'aritasilva@ua.pt'),
                                                        ('Hugo','Castro','hugocastro@ua.pt');
