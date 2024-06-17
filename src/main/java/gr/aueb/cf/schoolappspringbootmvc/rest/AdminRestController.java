package gr.aueb.cf.schoolappspringbootmvc.rest;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserCreateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.user.UserUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.enums.Role;
import gr.aueb.cf.schoolappspringbootmvc.enums.Status;
import gr.aueb.cf.schoolappspringbootmvc.mapper.UserMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.service.AdminServiceImpl;
import gr.aueb.cf.schoolappspringbootmvc.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing administrators.
 */
@RestController
@RequestMapping("/admin")
@Slf4j
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminServiceImpl adminService;
    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Deletes the authenticated admin.
     *
     * @return a response entity indicating the outcome of the operation
     */
    @Operation(summary = "Delete the authenticated admin", description = "Deletes the currently authenticated admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted admin", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/delete/me")
    public ResponseEntity<Void> deleteAuthenticatedAdmin() {
        try {
            adminService.deleteCurrentAdmin();
            log.info("Authenticated admin deleted");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting authenticated admin", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates the authenticated admin.
     *
     * @param dto the admin update DTO
     * @return a response entity indicating the outcome of the operation
     */
    @Operation(summary = "Update the authenticated admin", description = "Updates the currently authenticated admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated admin", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/update/me")
    public ResponseEntity<Void> updateCurrentAdmin(@RequestBody AdminUpdateDTO dto) {
        try {
            adminService.updateAdmin(dto);
            log.info("Authenticated admin updated");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error updating authenticated admin", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Finds a user by username.
     *
     * @param username the username
     * @return a response entity with the user information
     */
    @Operation(summary = "Find user by username", description = "Retrieves a user by their username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get-user/{username}")
    public ResponseEntity<Optional<UserDTO>> findUserByUsername(@PathVariable String username) {
        try {
            Optional<UserDTO> user = userService.findUserByUsername(username);
            if (user.isPresent()) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error finding user by username", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Finds a user by email.
     *
     * @param email the email
     * @return a response entity with the user information
     */
    @Operation(summary = "Find user by email", description = "Retrieves a user by their email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get-by-email/{email}")
    public ResponseEntity<Optional<UserDTO>> findUserByEmail(@PathVariable String email) {
        try {
            Optional<UserDTO> user = userService.findUserByEmail(email);
            if (user.isPresent()) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error finding user by email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Finds a user by ID.
     *
     * @param id the user ID
     * @return a response entity with the user information
     */
    @Operation(summary = "Find user by ID", description = "Retrieves a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get/id/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        try {
            UserDTO user = userService.findUserById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error finding user by id", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Finds users by role.
     *
     * @param role the role
     * @return a response entity with the list of users
     */
    @Operation(summary = "Find users by role", description = "Retrieves users by their role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get/role/{role}")
    public ResponseEntity<List<UserDTO>> findUserByRole(@PathVariable Role role) {
        try {
            List<UserDTO> users = userService.findUserByRole(role);
            if (users != null) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error finding user by role", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Finds all users.
     *
     * @return a response entity with the list of users
     */
    @Operation(summary = "Find all users", description = "Retrieves all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })

    @GetMapping("/get-all")
    public ResponseEntity<Page<UserDTO>> findAllUsers(@PageableDefault(size = 16) Pageable pageable) {
        try {
            Page<UserDTO> users = userService.findAllUsers(pageable);
            if (!users.isEmpty()) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error finding all users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Finds users by status.
     *
     * @param status the status
     * @return a response entity with the list of users
     */
    @Operation(summary = "Find users by status", description = "Retrieves users by their status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get/{status}")
    public ResponseEntity<List<UserDTO>> findUserByStatus(@PathVariable Status status) {
        try {
            List<UserDTO> users = userService.findUserByStatus(status);
            if (users != null) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("Error finding user by status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the user ID
     * @return a response entity indicating the outcome of the operation
     */
    @Operation(summary = "Delete user by ID", description = "Deletes a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted user", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error deleting user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates a user by ID.
     *
     * @param id  the user ID
     * @param dto the user update DTO
     * @return a response entity with the updated user information
     */
    @Operation(summary = "Update user by ID", description = "Updates a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserUpdateDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PatchMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<UserUpdateDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        try {
            dto.setId(id);
            Optional<User> user = userService.updateUser(dto);
            if (user.isPresent()) {
                UserUpdateDTO updatedUserDTO = userMapper.toUserUpdateDTO(user.get());
                return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            log.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Creates a new user.
     *
     * @param dto the user create DTO
     * @return a response entity indicating the outcome of the operation
     */
    @Operation(summary = "Create a new user", description = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created user", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createNewUser(@RequestBody UserCreateDTO dto) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.createUser(dto);
            response.put("message", "User created successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("error", "Error creating user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Searches for users by keyword.
     *
     * @param keyword  the keyword
     * @param pageable the pageable object
     * @return a response entity with the list of users
     */

    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUsers(@RequestParam(required = false) String keyword, Pageable pageable) {
        try{
            Page<UserDTO> users = userService.searchUsers(keyword, pageable);
            log.info("Users found: {}", users);
            return ResponseEntity.ok(users);
        }catch (Exception e){
            log.error("Error searching users", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
