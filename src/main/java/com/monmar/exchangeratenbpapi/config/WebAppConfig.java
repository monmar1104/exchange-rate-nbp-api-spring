package com.monmar.exchangeratenbpapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.monmar.exchangeratenbpapi")
public class WebAppConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver viewResolver() {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){

        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

//        resolver.addStatusCode("error-page", 404);

        Properties mapping = new Properties();
        mapping.put("com.monmar.exchangeratenbpapi.exception.ExchangeRateNotFoundException" , "error-404-code");
        mapping.put("com.monmar.exchangeratenbpapi.exception.ExchangeRateBadRequestException" , "error-400-code");
        resolver.setExceptionMappings(mapping);

        return resolver;
    }


}