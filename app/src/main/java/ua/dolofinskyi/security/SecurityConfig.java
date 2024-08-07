package ua.dolofinskyi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ua.dolofinskyi.features.user.User;
import ua.dolofinskyi.security.oauth2.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public CustomOAuth2UserService customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/login").permitAll()
                        .requestMatchers("/api/task/**").hasAuthority(User.Role.ROLE_USER.name())
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(endpoint -> endpoint.oidcUserService(customOAuth2UserService()))
                        .defaultSuccessUrl("/app")
                        .permitAll()
                );
        return http.build();
    }
}
