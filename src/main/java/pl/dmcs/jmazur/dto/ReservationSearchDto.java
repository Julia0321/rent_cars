package pl.dmcs.jmazur.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dmcs.jmazur.validator.ValidReservationDates;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidReservationDates
public class ReservationSearchDto {

    @Future(message = "{error.start.reservation.future}")
    @NotNull(message = "{error.field.required}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;

    @Future(message = "{error.end.reservation.future}")
    @NotNull(message = "{error.field.required}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;
}
