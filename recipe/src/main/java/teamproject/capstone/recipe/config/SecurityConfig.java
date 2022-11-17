package teamproject.capstone.recipe.config;

import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import teamproject.capstone.recipe.service.login.CustomOAuthService;
import teamproject.capstone.recipe.utils.login.FirebaseTokenFilter;
import teamproject.capstone.recipe.utils.login.handler.OAuthFailHandler;
import teamproject.capstone.recipe.utils.login.handler.OAuthSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {
    private final FirebaseAuth firebaseAuth;
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationSuccessHandler editAuthenticationSuccessHandler() {
        return new OAuthSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler editAuthenticationFailureHandler() {
        return new OAuthFailHandler();
    }

    @Bean
    public SecurityFilterChain firebaseFilterChain(HttpSecurity http) throws Exception {
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
                .addFilterBefore(new FirebaseTokenFilter(userDetailsService, firebaseAuth), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

        return http.build();
    }
}
