package gr.aueb.cf.schoolappspringbootmvc.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for custom authentication provider.
 */
@Configuration
public class CustomAuthProvider {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructs a CustomAuthProvider with the specified CustomUserDetailsService.
     *
     * @param customUserDetailsService the custom user details service
     */
    @Autowired
    public CustomAuthProvider(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Configures and returns an {@link AuthenticationProvider}.
     * Uses {@link DaoAuthenticationProvider} with custom user details service and password encoder.
     *
     * @return the configured authentication provider
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder()); // BCrypt
        return authProvider;
    }

    /**
     * Returns an {@link AuthenticationManager} from the specified {@link AuthenticationConfiguration}.
     *
     * @param config the authentication configuration
     * @return the authentication manager
     * @throws Exception if an error occurs while obtaining the authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configures and returns a {@link PasswordEncoder} using BCrypt hashing algorithm.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // default is 10 which corresponds to 2^10 iterations
    }
}
