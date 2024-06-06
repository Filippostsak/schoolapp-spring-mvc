package gr.aueb.cf.schoolappspringbootmvc.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for getting the user's username.
 * Contains information about the user's ID and username.
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserGetUsernameDTO {

    /**
     * The ID of the user.
     */

    private Long id;

    /**
     * The username of the user.
     */

    private String username;

}
