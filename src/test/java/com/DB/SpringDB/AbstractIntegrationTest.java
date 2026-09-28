package com.DB.SpringDB;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

@Testcontainers
@SpringBootTest 
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {
    @Container 
    @ServiceConnection
    static MySQLContainer mysql = 
        new MySQLContainer("mysql")
                .withDatabaseName("springdb")
                .withUsername("root")
                .withPassword("admin");
}