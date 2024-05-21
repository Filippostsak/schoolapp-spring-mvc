package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.Mapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.AdminRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.AdminAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * Registers a new admin.
     *
     * @param dto the data transfer object containing admin registration information.
     * @return the registered admin.
     * @throws AdminAlreadyExistsException if an admin with the specified username already exists.
     */
    @Override
    public Admin registerAdmin(RegisterAdminDTO dto) throws AdminAlreadyExistsException {
        Admin admin;
        User user;
        try {
            admin = Mapper.extractAdminFromRegisterAdminDTO(dto);
            user = Mapper.extractUserFromRegisterAdminDTO(dto);
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
        return adminRepository.findAll();
    }
}
