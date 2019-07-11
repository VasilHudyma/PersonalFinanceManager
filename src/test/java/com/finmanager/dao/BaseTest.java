package com.finmanager.dao;

import com.finmanager.config.ProfileConfig;
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
    protected static JdbcTemplate jdbcTemplate;

    private static boolean isInitialized;

}
