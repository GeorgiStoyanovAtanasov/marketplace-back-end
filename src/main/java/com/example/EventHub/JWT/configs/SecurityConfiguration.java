package com.example.EventHub.JWT.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAnyAuthority;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/auth/register/user", "/auth/login").permitAll()
                .requestMatchers("/users/roles").permitAll()
                .requestMatchers("/users/email").permitAll()
                .requestMatchers("/users/findByEmail").permitAll()
                .requestMatchers("/registration", "/registration/submit", "/home", "/event/all", "/event/search").permitAll()
                .requestMatchers("/organisation/all").permitAll()



                .requestMatchers("/event/accept","/event/reject").hasAnyAuthority("ROLE_ADMIN", "ADMIN")
                .requestMatchers("/event/accept","/event/reject").hasAnyRole("ADMIN")

                .requestMatchers("/event/{eventName}").permitAll()

                .requestMatchers("/organisation/add").hasAnyAuthority("ROLE_MANAGER", "MANAGER")
                .requestMatchers("/organisation/submit").hasAnyAuthority("ROLE_MANAGER", "MANAGER")
                .requestMatchers("/manager/register").permitAll()
                .requestMatchers("/manager/organisation", "/manager/id").permitAll()


                //.anyRequest().hasAnyRole("USER", "ADMIN")
                .anyRequest().hasAnyAuthority("USER", "ROLE_USER", "ADMIN", "ROLE_ADMIN")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
