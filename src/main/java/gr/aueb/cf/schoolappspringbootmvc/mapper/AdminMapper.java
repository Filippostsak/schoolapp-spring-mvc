package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminGetAuthenticatedAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.exception.AdminNotFoundException;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
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

    /**
     * Maps an AdminUpdateDTO to an existing Admin entity.
     *
     * @param dto The AdminUpdateDTO to map.
     * @param existingAdmin The existing Admin entity to update.
     * @return The updated Admin entity.
     */
    public Admin mapAdminDTOToAdmin(AdminUpdateDTO dto, Admin existingAdmin) {
        if (existingAdmin == null) {
            throw new AdminNotFoundException("Admin not found");
        }
        User user = existingAdmin.getUser();
        if (user == null) {
            throw new AdminNotFoundException("User not found");
        }

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBirthDate(dto.getBirthDate());
        user.setCountry(dto.getCountry());
        user.setCity(dto.getCity());

        existingAdmin.setFirstname(dto.getFirstname());
        existingAdmin.setLastname(dto.getLastname());
        existingAdmin.setUser(user);

        return existingAdmin;
    }
}
