package pl.dmcs.jmazur.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class UserEditDto {

    private String uuid;

    @NotBlank(message = "{error.field.required}")
    @Size(min = 2, max = 30, message = "{error.size.2_30}")
    private String firstname;

    @NotBlank(message = "{error.field.required}")
    @Size(min = 2, max = 30, message = "{error.size.2_30}")
    private String surname;

    @NotBlank(message = "{error.field.required}")
    @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "{error.phone.invalid}")
    private String phoneNumber;

    @Size(min = 8, message = "{error.password.min8}")
    private String password;
}
