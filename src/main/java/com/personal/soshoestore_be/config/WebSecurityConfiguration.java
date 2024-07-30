package com.personal.soshoestore_be.config;

import com.personal.soshoestore_be.filters.JwtTokenFilter;
import com.personal.soshoestore_be.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final JwtTokenFilter jwtTokenFilter;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        requests -> {
                            requests.requestMatchers(
                                    String.format("%s/users/login", apiPrefix),
                                            String.format("%s/users/register", apiPrefix),
                                            String.format("%s/shoes/**", apiPrefix)
                                    )
                                    .permitAll()
                                    .requestMatchers(HttpMethod.GET,
                                            String.format("%s/orders/**", apiPrefix)
                                    ).hasAnyRole(Role.ADMIN, Role.USER)
                                    .requestMatchers(HttpMethod.POST,
                                            String.format("%s/orders/**", apiPrefix)
                                    ).hasAnyRole(Role.USER)
                                    .requestMatchers(HttpMethod.PUT,
                                            String.format("%s/orders/**", apiPrefix)
                                    ).hasRole(Role.ADMIN)
                                    .requestMatchers(HttpMethod.DELETE,
                                            String.format("%s/orders/**", apiPrefix)
                                    ).hasRole(Role.ADMIN)
                                    .requestMatchers(
                                            String.format("%s/cart/**", apiPrefix)
                                    ).hasRole(Role.USER)
                                    .anyRequest().authenticated();
                        }
                )
                .build();
    }
}
