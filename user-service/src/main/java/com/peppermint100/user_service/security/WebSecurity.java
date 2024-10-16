package com.peppermint100.user_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> {
                request.requestMatchers(antMatcher("/user-service/**")).permitAll();
                request.requestMatchers(antMatcher("/h2-console/**")).permitAll(); // h2 콘솔 경로 허용
            })
            .headers(header -> { // h2 console은 frame을 사용하므로 frame에 대해서 시큐리티를 disable
                header.frameOptions(frameOptions -> {
                    frameOptions.disable();
                });
            });

        return http.build();
    }
}
