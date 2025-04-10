package org.example.tmstrainingspring.security;

import jakarta.servlet.http.HttpServletResponse;
import org.example.tmstrainingspring.entities.UserModel;
import org.example.tmstrainingspring.enums.Role;
import org.example.tmstrainingspring.security.filters.JwtFilter;
import org.example.tmstrainingspring.services.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    //        Config for Basic authentication
//    @Bean
//    SecurityFilterChain securityFilterChainBasic(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest()
//                        .authenticated())
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }

    //        Config for Rest API JWT authentication
    @Bean
    SecurityFilterChain securityFilterChainJWT(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("login", "signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "users").hasAuthority(Role.ROLE_ADMIN.name())
                        .anyRequest().hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name())
                )
                .exceptionHandling(ex -> {
                    ex.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType("application/json");
                        response.getWriter().write("You don't have permission to access this resource");

                    });

                    ex.authenticationEntryPoint((request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("UNAUTHORIZED");
                    });
                });


        return http.build();
    }


    @Bean
    ApplicationRunner runner(UserService userService) {
        return args -> {
            UserModel adminUser = userService.findByUsername("admin");
            if (adminUser != null) {
                return;
            }

            try {
                UserModel user = new UserModel();
                user.setUsername("admin");
                user.setPassword("admin");
                user.setRole(Role.ROLE_ADMIN);
                user.setAge(200);

                userService.add(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
