package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Service interface for managing users.
 */
public interface IUserService {

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to be retrieved.
     * @return the user with the specified username.
     * @throws UsernameNotFoundException if a user with the specified username is not found.
     */
    User getUserByUsername(String username) throws UsernameNotFoundException;
}
