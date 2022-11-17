package teamproject.capstone.recipe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import teamproject.capstone.recipe.service.login.CustomOAuthService;
import teamproject.capstone.recipe.utils.login.handler.OAuthFailHandler;
import teamproject.capstone.recipe.utils.login.handler.OAuthSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuthService customOAuthService;

    @Bean
    public AuthenticationSuccessHandler editAuthenticationSuccessHandler() {
        return new OAuthSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler editAuthenticationFailureHandler() {
        return new OAuthFailHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/account/**", "/login/**").permitAll()
                .antMatchers("/cookers/info").authenticated()
                .antMatchers("/api/v1/**", "/api/v2/**").permitAll()
                .antMatchers("/api/recipe/v1/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/account")
                .and()
                .logout()
                .logoutSuccessUrl("/account")
                .clearAuthentication(true)
                .and()
                .oauth2Login()
                .successHandler(editAuthenticationSuccessHandler())
                .failureHandler(editAuthenticationFailureHandler())
                .userInfoEndpoint()
                .userService(customOAuthService);

        return http.build();
    }
}
