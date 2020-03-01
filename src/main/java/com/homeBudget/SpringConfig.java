package com.homeBudget;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by shehab.tarek on 2/29/2020.
 */
@Configuration
@EnableWebMvc
public class SpringConfig
    implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedOrigins("https://aqueous-temple-30838.herokuapp.com");

        }
}
