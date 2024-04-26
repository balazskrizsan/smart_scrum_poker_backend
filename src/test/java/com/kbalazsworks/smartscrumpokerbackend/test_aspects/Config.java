package com.kbalazsworks.smartscrumpokerbackend.test_aspects;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config
{
    @Bean
    public SqlPresetAspect getSqlPresetAspectBean() {
        return Aspects.aspectOf(SqlPresetAspect.class);
    }
}
