package com.refunits.service.configuration;

import com.refunits.database.configuration.DatabaseConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DatabaseConfiguration.class, CachingConfiguration.class})
@ComponentScan(basePackages = {"com.refunits.service"})
@EnableAspectJAutoProxy
public class ServiceConfiguration {
}

