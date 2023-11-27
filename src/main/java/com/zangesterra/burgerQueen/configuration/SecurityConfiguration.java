package com.zangesterra.burgerQueen.configuration;

import com.zangesterra.burgerQueen.filter.CustomAuthenticationFilter;
import com.zangesterra.burgerQueen.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration {

    private UserDetailService userDetailService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfiguration(UserDetailService userDetailService, PasswordEncoder passwordEncoder) {
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Configuration
    @Order(1)
    public class JWTSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        DaoAuthenticationProvider provider;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(provider);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatchers(matchers -> matchers
                            .antMatchers("/user/**") // apply JWTSecurityConfig to requests matching "/api/**"

                    )
                    .authorizeRequests(authz -> authz
                            .antMatchers(HttpMethod.GET).hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));


//                    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//            .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests().anyRequest().permitAll()
//                .and()
//                .addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        }
    }

    @Configuration
    public class FormLoginConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        DaoAuthenticationProvider provider;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(provider);

        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authz -> authz
                            .antMatchers("/","/register", "/css/**","/js/**","/img/**").permitAll()
                            .antMatchers(HttpMethod.POST,"/admin").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin()
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/")
                    .failureUrl("/login-error")
                    .and()
                    .logout()
                    .logoutUrl("/logout").permitAll()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
        }
    }
}

