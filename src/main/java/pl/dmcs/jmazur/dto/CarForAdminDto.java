package pl.dmcs.jmazur.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.dmcs.jmazur.enums.CarStatusEnum;
import pl.dmcs.jmazur.enums.FuelEnum;
import pl.dmcs.jmazur.enums.TransmissionEnum;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class CarForAdminDto {

    private String uuid;

    @NotBlank(message = "{error.field.required}")
    private String brand;

    @NotBlank(message = "{error.field.required}")
    private String model;

    @NotNull(message = "{error.field.required}")
    private FuelEnum fuel;

    @NotNull(message = "{error.field.required}")
    private TransmissionEnum transmission;

    @NotBlank(message = "{error.field.required}")
    private String productionNumber;

    @NotBlank(message = "{error.field.required}")
    private String registrationNumber;

    @NotNull(message = "{error.field.required}")
    private BigDecimal pricePerDay;

    @NotNull(message = "{error.field.required}")
    private CarStatusEnum carStatus;
}
