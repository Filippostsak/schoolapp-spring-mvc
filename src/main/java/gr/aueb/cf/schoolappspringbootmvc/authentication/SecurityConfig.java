package gr.aueb.cf.schoolappspringbootmvc.authentication;

import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * SecurityConfig configures the security settings for the application.
 * It includes configurations for CORS, CSRF, authentication, and authorization.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    /**
     * Constructs a SecurityConfig with the specified AuthenticationSuccessHandler.
     *
     * @param customAuthenticationSuccessHandler the custom authentication success handler
     */
    public SecurityConfig(AuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    /**
     * Configures the CORS settings for the application.
     *
     * @return the CORS configuration source
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("https://codingfactory.aueb.gr", "http://localhost:4200"));
        corsConfiguration.setAllowedMethods(List.of("*")); // GET, POST, PUT, DELETE, PATCH
        corsConfiguration.setAllowedHeaders(List.of("*")); // Authorization, Content-Type
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * Configures the security filter chain for the application.
     *
     * @param http the HttpSecurity to modify
     * @return the security filter chain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/styles/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/features").permitAll()
                        .requestMatchers("/pricing").permitAll()
                        .requestMatchers("/register/role").permitAll()
                        .requestMatchers("/register/teacher").permitAll()
                        .requestMatchers("/register/student").permitAll()
                        .requestMatchers("/register/admin").permitAll()
                        .requestMatchers("/students/**").hasAnyAuthority(Role.STUDENT.name())
                        .requestMatchers("/teachers/**").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/admins/**").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/rest/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)  // Set the custom success handler
                )
                .httpBasic(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                        .alwaysRemember(true)
                        .rememberMeParameter("rememberMe")
                        .rememberMeCookieName("remember-me")
                        .tokenValiditySeconds(24 * 60 * 60)
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(new AccessDeniedHandlerImpl() {
                            {
                                setErrorPage("/error-403");
                            }
                        })
                );
        return http.build();
    }
}
