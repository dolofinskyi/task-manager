package ua.dolofinskyi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.dolofinskyi.security.jwt.JwtAuthSuccessHandler;
import ua.dolofinskyi.security.jwt.JwtBeforeAuthFilter;
import ua.dolofinskyi.security.oauth2.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private JwtBeforeAuthFilter jwtBeforeAuthFilter;
    @Autowired
    private JwtAuthSuccessHandler jwtAuthSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(endpoint -> endpoint.oidcUserService(customOAuth2UserService))
                        .successHandler(jwtAuthSuccessHandler)
                        .permitAll()
                )
                .addFilterBefore(jwtBeforeAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
