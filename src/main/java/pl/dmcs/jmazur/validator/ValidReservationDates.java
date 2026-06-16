package pl.dmcs.jmazur.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservationValidator.class)
public @interface ValidReservationDates {

    String message() default "{error.reservation.date.order}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
