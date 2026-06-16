package pl.dmcs.jmazur.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.dmcs.jmazur.enums.ReservationStatusEnum;
import pl.dmcs.jmazur.repository.ReservationRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationCancelJob {

    private final ReservationRepository reservationRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelExpired() {
        var expired = reservationRepository
                .findByStatusAndPaymentDeadlineBefore(
                        ReservationStatusEnum.PENDING,
                        LocalDateTime.now()
                );

        for (var r : expired) {
            if (r.getPayment() == null) {
                r.setStatus(ReservationStatusEnum.CANCELLED);
            }
        }
    }
}
