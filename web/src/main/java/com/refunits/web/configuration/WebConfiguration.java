package com.refunits.web.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import(value = {ThymeleafConfiguration.class, InternationalizationConfiguration.class})
@ComponentScan(basePackages = {"com.refunits.web.controller"})
@EnableWebMvc
public class WebConfiguration {
}