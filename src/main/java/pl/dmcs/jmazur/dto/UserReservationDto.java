package pl.dmcs.jmazur.dto;

import lombok.*;
import pl.dmcs.jmazur.enums.ReservationStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReservationDto {

    private String reservationUuid;

    private LocalDate from;
    private LocalDate to;

    private String carBrand;
    private String carModel;

    private ReservationStatusEnum status;

    private BigDecimal totalPrice;

    private String paymentUuid;
}
