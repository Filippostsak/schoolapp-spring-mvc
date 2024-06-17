package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminGetAuthenticatedAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.admin.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.AdminMapper;
import gr.aueb.cf.schoolappspringbootmvc.mapper.UserMapper;
import gr.aueb.cf.schoolappspringbootmvc.mapper.exception.AdminNotFoundException;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.AdminRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.AdminAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link IAdminService} interface.
 * Provides methods for registering and retrieving admins.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;

    /**
     * Registers a new admin.
     *
     * @param dto the data transfer object containing admin registration information.
     * @return the registered admin.
     * @throws AdminAlreadyExistsException if an admin with the specified username already exists.
     */
    @Override
    @Transactional
    public Admin registerAdmin(RegisterAdminDTO dto) throws AdminAlreadyExistsException {
        Admin admin;
        User user;
        try {
            admin = UserMapper.extractAdminFromRegisterAdminDTO(dto);
            user = UserMapper.extractUserFromRegisterAdminDTO(dto);
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new AdminAlreadyExistsException(user.getUsername());
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            admin.addUser(user);
            adminRepository.save(admin);

        } catch (AdminAlreadyExistsException e) {
            throw e;
        }
        return admin;
    }

    /**
     * Retrieves all admins.
     *
     * @return a list of all admins.
     * @throws Exception if an error occurs while retrieving the admins.
     */
    @Override
    public List<Admin> findAllAdmins() throws Exception {
        List<Admin> admins;
        try {
            admins = adminRepository.findAll();
            return admins;
        } catch (Exception e) {
            log.error("An error occurred while retrieving the admins: {}", e.getMessage());
            throw new Exception("An error occurred while retrieving the admins.");
        }
    }

    /**
     * Retrieves the currently authenticated admin.
     *
     * @param dto the data transfer object containing the information needed to retrieve the authenticated admin.
     * @return the authenticated admin.
     * @throws AdminNotFoundException if the authenticated admin is not found.
     */

    @Override
    public AdminGetAuthenticatedAdminDTO getAuthenticatedAdmin(AdminGetAuthenticatedAdminDTO dto) throws AdminNotFoundException {
        Admin admin;
        try {
            log.debug("Retrieving authenticated admin");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            log.debug("Authenticated admin username: {}", username);
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isEmpty()) {
                throw new AdminNotFoundException(username);
            }
            log.debug("User found with username: {}", username);
            User user = userOptional.get();
            log.debug("User found with id: {}", user.getId());
            admin = adminRepository.findByUserUsername(username).orElseThrow(() -> new AdminNotFoundException(username));
            dto = adminMapper.toAdminGetAuthenticatedAdminDTO(admin);
            dto.setUsername(username);
            return dto;
        } catch (Exception e) {
            log.error("An error occurred while retrieving the authenticated admin: {}", e.getMessage());
            throw new AdminNotFoundException("An error occurred while retrieving the authenticated admin.");
        }
    }

    /**
     * Deletes the currently authenticated admin.
     */

    @Override
    public void deleteCurrentAdmin() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Admin admin = adminRepository.findByUserUsername(username).orElseThrow(() -> new AdminNotFoundException(username));
                adminRepository.delete(admin);
                log.info("Admin with username {} deleted", username);
                userRepository.delete(user);
            }
        } catch (Exception e) {
            log.error("An error occurred while deleting the authenticated admin: {}", e.getMessage());
            throw new RuntimeException("An error occurred while deleting the authenticated admin.");
        }
    }

    /**
     * Updates the currently authenticated admin.
     *
     * @param dto the data transfer object containing the information needed to update the authenticated admin.
     */

    @Override
    public void updateAdmin(AdminUpdateDTO dto) {
        try {
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
            User currentuser = userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new AdminNotFoundException(currentUser));
            Admin currentAdmin = adminRepository.findByUserUsername(currentUser)
                    .orElseThrow(() -> new AdminNotFoundException(currentUser));
            Admin updatedAdmin = adminMapper.mapAdminDTOToAdmin(dto, currentAdmin);
            adminRepository.save(updatedAdmin);
            userRepository.save(currentuser);
            log.info("Admin with username {} updated", currentUser);
        } catch (Exception e) {
            log.error("An error occurred while updating the authenticated admin: {}", e.getMessage());
            throw new RuntimeException("An error occurred while updating the authenticated admin.");
        }
    }
}
