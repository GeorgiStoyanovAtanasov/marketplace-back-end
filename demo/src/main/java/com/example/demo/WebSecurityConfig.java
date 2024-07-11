package com.example.EventHub;

import com.example.EventHub.User.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests((requests) -> requests
                            .requestMatchers("/registration","registration/submit", "/home", "/event/all", "/event/{}", "/event/search").permitAll()
                            .requestMatchers("/event/add").hasAnyAuthority("Admin","Manager")
                            .requestMatchers("/event/apply", "/user/**").hasAuthority("User")
                            .requestMatchers("/event/**", "/organisation/**", "/event-type/**").hasAuthority("Admin")
                            .anyRequest().authenticated()
                    )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .successForwardUrl("/home")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }
}
