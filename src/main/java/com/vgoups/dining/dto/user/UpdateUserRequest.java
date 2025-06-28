package com.vgoups.dining.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRequest {
    @NotBlank(message = "Name field is required")
    @Size(max = 50, message = "Name must be at most 50 characters")
    private String name;


    @Email(message = "Invalid format")
    @NotEmpty(message = "Email must be specified")
    @NotNull
    private String email;

    @NotNull(message = "Roles must be provided")
    @NotEmpty(message = "At least one role ID must be specified")
    private String password;

    @NotNull(message = "Status must be specified")
    private Boolean status;

    @NotNull(message = "Roles must be provided")
    @NotEmpty(message = "At least one role ID must be specified")
    private Set<Long> rolesId;

}
