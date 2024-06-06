package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminGetAuthenticatedAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.exception.AdminNotFoundException;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import org.springframework.stereotype.Component;

/**
 * Mapper for the Admin entity.
 * Contains methods for converting an Admin entity to a DTO.
 */

@Component
public class AdminMapper {

    /**
     * Converts an Admin entity to an AdminGetAuthenticatedAdminDTO.
     *
     * @param admin The Admin entity to convert.
     * @return The AdminGetAuthenticatedAdminDTO.
     * @throws AdminNotFoundException If the Admin entity is null.
     */

    public AdminGetAuthenticatedAdminDTO toAdminGetAuthenticatedAdminDTO(Admin admin) throws AdminNotFoundException {
        if (admin == null) {
            throw new AdminNotFoundException("Admin not found");
        }
        return new AdminGetAuthenticatedAdminDTO(
                admin.getId(),
                admin.getUser().getUsername()
        );
    }
}
