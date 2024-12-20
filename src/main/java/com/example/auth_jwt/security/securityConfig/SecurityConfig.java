package com.example.auth_jwt.security.securityConfig;

import com.example.auth_jwt.BaseApiConstants.BaseApiConstants;
import com.example.auth_jwt.security.jwtFilter.JwtAuthFilter;
import com.example.auth_jwt.security.services.UsersAccountsDetailsServiceImpl;
import com.example.auth_jwt.masters.usersAccount.servicesInterfaces.IUsersAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final IUsersAccountsService usersAccountsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthFilter jwtAuthFilter;

    @Autowired
    public SecurityConfig (IUsersAccountsService usersAccountsService,
                           PasswordEncoder passwordEncoder,
                           JwtAuthFilter jwtAuthFilter) {
        this.usersAccountsService = usersAccountsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UsersAccountsDetailsServiceImpl(usersAccountsService, passwordEncoder);
    }

    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(BaseApiConstants.contextAuth+"/addNewUser",
                                BaseApiConstants.contextAuth+"/welcome",
                                BaseApiConstants.contextAuth+"/generateToken")
                        .permitAll()
                        .requestMatchers(BaseApiConstants.contextAuth+BaseApiConstants.contextOwner+"/**")
                        .hasAuthority("OWNER")
                        .requestMatchers(BaseApiConstants.contextAuth+BaseApiConstants.contextAdmin+"/**")
                        .hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
//                .httpBasic(Customizer.withDefaults());
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
