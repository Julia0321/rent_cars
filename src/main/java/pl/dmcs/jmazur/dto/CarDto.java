package pl.dmcs.jmazur.dto;

import lombok.*;
import pl.dmcs.jmazur.enums.FuelEnum;
import pl.dmcs.jmazur.enums.TransmissionEnum;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class CarDto {

    private String uuid;

    private String brand;

    private String model;

    private FuelEnum fuel;

    private TransmissionEnum transmission;

    private String productionNumber;

    private String registrationNumber;

    private BigDecimal pricePerDay;
}
