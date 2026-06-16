package pl.dmcs.jmazur.mapper;

import org.springframework.stereotype.Component;
import pl.dmcs.jmazur.domain.Payment;
import pl.dmcs.jmazur.dto.PaymentDto;

@Component
public class PaymentMapper {

    public PaymentDto mapToDto(Payment payment) {
        return PaymentDto.builder()
                .reservationUUID(payment.getReservation().getUuid())
                .paymentDate(payment.getPaymentDate())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .uuid(payment.getUuid())
                .build();
    }
}
