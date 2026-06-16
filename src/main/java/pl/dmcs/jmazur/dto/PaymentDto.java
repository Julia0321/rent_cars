package pl.dmcs.jmazur.dto;

import lombok.*;
import pl.dmcs.jmazur.enums.PaymentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PaymentDto {

    private String uuid;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private PaymentStatusEnum status;

    private String reservationUUID;
}
