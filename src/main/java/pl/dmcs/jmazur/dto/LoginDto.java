package pl.dmcs.jmazur.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class LoginDto {

    @NotBlank(message = "{error.field.required}")
    @Email(message = "{email.error}")
    private String email;

    @NotBlank(message = "{error.field.required}")
    @Size(min = 8, message = "{password.error}")
    private String password;
}
