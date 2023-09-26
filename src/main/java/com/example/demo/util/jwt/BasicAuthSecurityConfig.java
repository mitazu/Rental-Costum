package com.example.demo.util.jwt;

import com.example.demo.configuration.MaintenaceRequestMacher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class BasicAuthSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    private final AccessDeniedPage accessDeniedPage;

    private final org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    private final JwtAuthFilter jwtAuthFilter;

    private final MaintenaceRequestMacher maintenaceRequestMacher;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] pathArray = new String[]{
                "/login",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/logout",
                "/register",
                "/tugas-akhir/**"
        };

        http.csrf().disable().cors()
                .and()
                .authorizeRequests().antMatchers(pathArray).permitAll()
                .requestMatchers(maintenaceRequestMacher).denyAll()
                .anyRequest().permitAll()
                .and().exceptionHandling().accessDeniedHandler(accessDeniedPage)
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}