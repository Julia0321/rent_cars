package pl.dmcs.jmazur.mapper;

import org.springframework.stereotype.Component;
import pl.dmcs.jmazur.domain.Payment;
import pl.dmcs.jmazur.domain.Reservation;
import pl.dmcs.jmazur.dto.ReservationAdminDto;
import pl.dmcs.jmazur.enums.PaymentStatusEnum;

@Component
public class ReservationAdminMapper {

    public ReservationAdminDto mapToDto(Reservation reservation) {

        Payment payment = reservation.getPayment();

        return ReservationAdminDto.builder()
                .reservationUuid(reservation.getUuid())
                .from(reservation.getFrom())
                .to(reservation.getTo())
                .carBrand(reservation.getCar().getBrand())
                .carModel(reservation.getCar().getModel())
                .reservationStatus(reservation.getStatus())
                .paymentStatus(payment != null ? payment.getStatus() : PaymentStatusEnum.PENDING)
                .totalPrice(reservation.getTotalPrice())
                .paymentUuid(payment != null ? payment.getUuid() : null)
                .email(reservation.getUser().getEmail())
                .build();
    }
}
