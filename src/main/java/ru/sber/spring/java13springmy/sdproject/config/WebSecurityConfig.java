package ru.sber.spring.java13springmy.sdproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.sber.spring.java13springmy.sdproject.service.userdetails.CustomUserDetailsService;

import java.util.Arrays;

import static ru.sber.spring.java13springmy.sdproject.constants.PermissionConstants.*;
import static ru.sber.spring.java13springmy.sdproject.constants.UserRoleConstants.*;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    public WebSecurityConfig (BCryptPasswordEncoder bCryptPasswordEncoder,
                              CustomUserDetailsService customUserDetailsService){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }
    @Bean
    public HttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPercent(true);
        firewall.setAllowUrlEncodedSlash(true);
        firewall.setAllowSemicolon(true);
        firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        return firewall;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                //TODO Для учебных целей пока отключим
//                .csrf(csrf -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                )

                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
                                .requestMatchers(ALL_WHITE_LIST.toArray(String[]::new)).permitAll()
                                .requestMatchers(BASE_PERMISSION_LIST.toArray(String[]::new)).authenticated()
                                .requestMatchers(EXECUTOR_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(EXECUTOR, MAIN_EXECUTOR)
                                .requestMatchers(SETTINGS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, EXECUTOR, MAIN_EXECUTOR)
                                .anyRequest().authenticated()
//                                .anyRequest().permitAll()
                        )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                );
        return httpSecurity.build();
    }
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
