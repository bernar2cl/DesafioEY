package com.example.EY.Infrastructure.configuration;

import com.example.EY.Infrastructure.configuration.exception.CustomAccessDeniedHandler;
import com.example.EY.Infrastructure.security.JwtAuthenticationFilter;
import com.example.EY.application.get.GetUsuarioGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final GetUsuarioGateway getUsuarioGateway;

    public SecurityConfig(GetUsuarioGateway getUsuarioGateway) {
        this.getUsuarioGateway = getUsuarioGateway;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/auth/login","/swagger-ui/**","/swagger-ui.html", "/v2/api-docs","/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/h2-console/**")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/usuarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers("/api/usuarios/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .exceptionHandling()   // <-- Aquí agregamos la parte nueva
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(getUsuarioGateway), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
