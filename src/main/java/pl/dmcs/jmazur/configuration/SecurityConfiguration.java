package pl.dmcs.jmazur.configuration;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/", "/login", "/accessDenied",
                                "/users/register", "/users/save", "/users/activate",
                                "/resources/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**", "/rent/**", "/rest/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .failureHandler((req, res, ex) -> {
                            String ctx = req.getContextPath();
                            if (ex instanceof DisabledException) {
                                res.sendRedirect(ctx + "/login?error=inactive");
                            } else {
                                res.sendRedirect(ctx + "/login?error");
                            }
                        })
                        .successHandler((req, res, auth) ->
                                res.sendRedirect(req.getContextPath() + "/user/dashboard")
                        )
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((req, res, auth) ->
                                res.sendRedirect(req.getContextPath() + "/login?logout")
                        )
                        .permitAll()
                )
                .exceptionHandling(e -> e
                        .accessDeniedPage("/accessDenied")
                );

        return http.build();
    }
}
