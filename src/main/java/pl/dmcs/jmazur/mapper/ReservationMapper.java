package pl.dmcs.jmazur.mapper;

import org.springframework.stereotype.Component;
import pl.dmcs.jmazur.domain.Payment;
import pl.dmcs.jmazur.domain.Reservation;
import pl.dmcs.jmazur.dto.UserReservationDto;

@Component
public class ReservationMapper {

    public UserReservationDto mapToDto(Reservation reservation) {

        Payment p = reservation.getPayment();

        return UserReservationDto.builder()
                .reservationUuid(reservation.getUuid())
                .from(reservation.getFrom())
                .to(reservation.getTo())
                .carBrand(reservation.getCar().getBrand())
                .carModel(reservation.getCar().getModel())
                .status(reservation.getStatus())
                .totalPrice(reservation.getTotalPrice())
                .paymentUuid(p != null ? p.getUuid() : null)
                .build();
    }
}
