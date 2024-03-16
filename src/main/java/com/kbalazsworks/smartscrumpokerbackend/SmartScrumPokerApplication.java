package com.kbalazsworks.smartscrumpokerbackend;

import com.kbalazsworks.smartscrumpokerbackend.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SmartScrumPokerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SmartScrumPokerApplication.class, args);
    }

    @Bean
    public ApplicationProperties applicationProperties()
    {
        return new ApplicationProperties();
    }
}
