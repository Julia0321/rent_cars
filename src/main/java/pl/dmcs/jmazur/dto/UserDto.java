package pl.dmcs.jmazur.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class UserDto {

    private String uuid;

    @NotBlank(message="{error.field.required}")
    @Size(min=2, max=30, message="{error.size.2_30}")
    private String firstname;

    @NotBlank(message="{error.field.required}")
    @Size(min=2, max=30, message="{error.size.2_30}")
    private String surname;

    @NotBlank(message="{error.field.required}")
    @Email(message="{error.email.invalid}")
    private String email;

    @NotBlank(message="{error.field.required}")
    @Pattern(regexp="^\\+?[0-9]{9,15}$", message="{error.phone.invalid}")
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message="{error.field.required}")
    @Past(message="{error.birth.past}")
    private LocalDate dateOfBirth;

    private boolean isActive;

    @JsonIgnore
    @NotBlank(message="{error.field.required}")
    @Size(min=8, message="{error.password.min8}")
    private String password;
}
