package org.example.tmstrainingspring.security;

import org.example.tmstrainingspring.entities.UserModel;
import org.example.tmstrainingspring.security.filters.JwtFilter;
import org.example.tmstrainingspring.services.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    //    Config for Basic authentication
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST, "users")
//                        .permitAll()
//                        .anyRequest()
//                        .authenticated())
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }

    //        Config for Rest API JWT authentication
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("login", "signup").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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
