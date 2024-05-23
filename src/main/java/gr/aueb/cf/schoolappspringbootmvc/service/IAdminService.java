package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.AdminAlreadyExistsException;

import java.util.List;

/**
 * Service interface for managing administrators.
 */
public interface IAdminService {

    /**
     * Registers a new administrator based on the provided data transfer object.
     *
     * @param dto the data transfer object containing the information needed to register an admin.
     * @return the registered admin.
     * @throws AdminAlreadyExistsException if an admin with the same username already exists.
     */
    Admin registerAdmin(RegisterAdminDTO dto) throws AdminAlreadyExistsException;

    /**
     * Retrieves all administrators.
     *
     * @return a list of all administrators.
     * @throws Exception if there is an error retrieving the admins.
     */
    List<Admin> findAllAdmins() throws Exception;
}
