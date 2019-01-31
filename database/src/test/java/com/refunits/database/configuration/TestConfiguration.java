package com.refunits.database.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.refunits.database.util")
@Import(DatabaseConfiguration.class)
public class TestConfiguration {
}