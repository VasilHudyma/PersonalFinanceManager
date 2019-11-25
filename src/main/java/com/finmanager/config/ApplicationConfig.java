package com.finmanager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Import({DBConfig.class, SwaggerConfig.class, WebSecurityConfig.class,
        EmailConfig.class
})
@Configuration
@ComponentScan(basePackages = "com.finmanager")
public class ApplicationConfig extends AbstractSecurityWebApplicationInitializer {

}
