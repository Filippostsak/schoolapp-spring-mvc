package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminGetAuthenticatedAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.admin.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.exception.AdminNotFoundException;
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

    /**
     * Retrieves the currently authenticated admin.
     *
     * @param dto the data transfer object containing the information needed to retrieve the authenticated admin.
     * @return the authenticated admin.
     * @throws AdminNotFoundException if the authenticated admin is not found.
     */

    AdminGetAuthenticatedAdminDTO getAuthenticatedAdmin(AdminGetAuthenticatedAdminDTO dto) throws AdminNotFoundException;

    /**
     * Deletes the currently authenticated admin.
     */

    void deleteCurrentAdmin();

    /**
     * Updates the currently authenticated admin based on the provided data transfer object.
     *
     * @param dto the data transfer object containing the information needed to update the authenticated admin.
     */
    void updateAdmin(AdminUpdateDTO dto);



}
