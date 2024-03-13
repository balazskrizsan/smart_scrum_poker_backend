package com.kbalazsworks.smartscrumpokerbackend.socket_api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class HttpSecurityConfig
{
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(requests -> requests.requestMatchers(new AntPathRequestMatcher("/ws")).permitAll());

        return http.build();
    }
}
