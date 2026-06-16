package pl.dmcs.jmazur.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.dmcs.jmazur.dto.ReservationSearchDto;

public class ReservationValidator implements ConstraintValidator<ValidReservationDates, ReservationSearchDto> {

    @Override
    public boolean isValid(ReservationSearchDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }
        if (dto.getFrom() == null || dto.getTo() == null) {
            return true;
        }

        if (dto.getFrom().isAfter(dto.getTo())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{error.reservation.date.order}")
                    .addPropertyNode("to")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
