package pl.dmcs.jmazur.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import pl.dmcs.jmazur.domain.Role;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class UserForAdminDto {

    private String uuid;

    @NotBlank(message = "{error.field.required}")
    @Size(min = 2, max = 30, message = "{error.size.2_30}")
    private String firstname;

    @NotBlank(message = "{error.field.required}")
    @Size(min = 2, max = 30, message = "{error.size.2_30}")
    private String surname;

    @NotBlank(message = "{error.field.required}")
    @Email(message = "{error.email.invalid}")
    private String email;

    @NotBlank(message = "{error.field.required}")
    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "{error.phone.invalid}")
    private String phoneNumber;

    @NotNull(message = "{error.field.required}")
    @Past(message = "{error.birth.past}")
    private LocalDate dateOfBirth;

    private boolean isActive;

    @NotNull(message = "{error.role.not.found}")
    private Set<Role> roles = new HashSet<>();
}
