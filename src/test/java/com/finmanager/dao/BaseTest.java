package com.finmanager.dao;

import com.finmanager.config.ProfileConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ProfileConfig.class})
public class BaseTest {

    @Autowired
    protected DataSource dataSource;
    static JdbcTemplate jdbcTemplate;

    private static boolean isInitialized;

    @BeforeEach
    void setupBudgetTests() {
        if (!isInitialized) {
            setup();
            isInitialized = true;
        }
    }

    private void setup() {
        // Setting up db
        jdbcTemplate = new JdbcTemplate(dataSource);
        clearAllTables();
        setupQueries();
    }

    static void clearAllTables() {
        jdbcTemplate.batchUpdate(
                "TRUNCATE TABLE operations, transactions, categories RESTART IDENTITY");
    }

    private void setupQueries() {
        jdbcTemplate.batchUpdate("INSERT INTO operations(name, created_date, updated_date) VALUES ('vytrata','2019-05-05','2019-05-05');",
                "INSERT INTO categories(name, description, created_date, updated_date) VALUES ('food', 'dsdfg','2019-05-05','2019-05-05');",
                "INSERT into transactions(category_id, operation_id, sum, description, created_date, updated_date) values (1,1,100.2, 'dsdfg','2019-05-05','2019-05-05')");

    }

}
