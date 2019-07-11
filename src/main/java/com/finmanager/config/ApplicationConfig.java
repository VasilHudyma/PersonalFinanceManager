package com.finmanager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(DBConfig.class)
@Configuration
@ComponentScan(basePackages = "com.finmanager")
public class ApplicationConfig {

}
