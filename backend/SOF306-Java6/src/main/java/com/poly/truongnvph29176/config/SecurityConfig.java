package com.poly.truongnvph29176.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers(
                                    String.format("%s/accounts/register", apiPrefix),
                                    String.format("%s/account/login", apiPrefix)
                            ).permitAll()

                            .requestMatchers(String.format("%s/accounts/**", apiPrefix)).permitAll()

                            .requestMatchers(String.format("%s/roles/**", apiPrefix)).permitAll()

                            .requestMatchers(String.format("%s/authorities/**", apiPrefix)).permitAll()

                            .requestMatchers(String.format("%s/categories/**", apiPrefix)).permitAll()

                            .requestMatchers(String.format("%s/products/**", apiPrefix)).permitAll()

                            .requestMatchers(String.format("%s/orders/**", apiPrefix)).permitAll()

                            .requestMatchers(String.format("%s/order-details/**", apiPrefix)).permitAll()

                            .anyRequest()
                            .authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}