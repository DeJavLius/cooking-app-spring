package teamproject.capstone.recipe.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import teamproject.capstone.recipe.service.login.CustomOAuthService;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/recipes/**").permitAll()
                .antMatchers("/account", "/login/**", "/test/**").permitAll()
                .antMatchers("/cookers/**").authenticated()
                .antMatchers("/api/v1/**", "/api/v2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .clearAuthentication(true)
                .and()
                .oauth2Login()
                .successHandler(editAuthenticationSuccessHandler())
                .userInfoEndpoint()
                .userService(customOAuthService);

        return http.build();
    }
}
