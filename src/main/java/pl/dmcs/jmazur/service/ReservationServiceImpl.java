package pl.dmcs.jmazur.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dmcs.jmazur.domain.Payment;
import pl.dmcs.jmazur.domain.Reservation;
import pl.dmcs.jmazur.domain.User;
import pl.dmcs.jmazur.dto.ReservationAdminDto;
import pl.dmcs.jmazur.dto.UserReservationDto;
import pl.dmcs.jmazur.enums.PaymentStatusEnum;
import pl.dmcs.jmazur.enums.ReservationStatusEnum;
import pl.dmcs.jmazur.mapper.ReservationAdminMapper;
import pl.dmcs.jmazur.mapper.ReservationMapper;
import pl.dmcs.jmazur.repository.PaymentRepository;
import pl.dmcs.jmazur.repository.ReservationRepository;
import pl.dmcs.jmazur.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarService carService;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ReservationMapper reservationMapper;
    private final ReservationAdminMapper reservationAdminMapper;

    @Transactional
    public Reservation createPendingReservation(String carUUID, LocalDate from, LocalDate to, String userEmail) {

        var car = carService.getCarByUUID(carUUID);
        var user = userRepository.findUserByEmail(userEmail).orElseThrow();

        long days = to.toEpochDay() - from.toEpochDay();

        Reservation r = new Reservation();
        r.setCar(car);
        r.setUser(user);
        r.setFrom(from);
        r.setTo(to);
        r.setTotalPrice(car.getPricePerDay()
                .multiply(BigDecimal.valueOf(days))
                .setScale(2, BigDecimal.ROUND_HALF_UP));

        return reservationRepository.save(r);
    }

    @Override
    public List<UserReservationDto> findReservationsByUser(String name) {

        User user = userRepository.findUserByEmail(name)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Reservation> reservations = reservationRepository.findReservationByUserUUID(user.getUuid());

        return reservations.stream().map(r -> reservationMapper.mapToDto(r)).collect(Collectors.toList());
    }

    @Override
    public void cancelReservation(String uuid) {

        Reservation r = reservationRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (r.getStatus() != ReservationStatusEnum.PENDING && r.getStatus() != ReservationStatusEnum.ACCEPTED) {
            throw new RuntimeException("Cannot cancel");
        }

        r.setStatus(ReservationStatusEnum.CANCELLED);
        reservationRepository.save(r);
    }

    @Override
    public List<ReservationAdminDto> findAll() {

        return reservationRepository.findAllForAdmin()
                .stream()
                .map((r) -> reservationAdminMapper.mapToDto(r))
                .collect(Collectors.toList());
    }

    @Override
    public void acceptReservation(String uuid) {

        Reservation reservationDB = reservationRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if(reservationDB.getStatus() == ReservationStatusEnum.ACCEPTED) {
            return;
        }

        reservationDB.setStatus(ReservationStatusEnum.ACCEPTED);
        reservationRepository.save(reservationDB);
    }

    @Override
    public void rejectReservation(String uuid) {

        Reservation reservationDB = reservationRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if(reservationDB.getStatus() == ReservationStatusEnum.REJECTED) {
            return;
        }

        reservationDB.setStatus(ReservationStatusEnum.REJECTED);
        reservationRepository.save(reservationDB);
    }

    @Transactional
    @Override
    public void markAsPaid(String reservationUUID) {

        Reservation r = reservationRepository.findByUuid(reservationUUID)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        if (r.getStatus() == ReservationStatusEnum.CANCELLED) {
            throw new RuntimeException("Reservation expired");
        }
        if (r.getStatus() != ReservationStatusEnum.PENDING) {
            throw new RuntimeException("Reservation was paid");
        }

        if (r.getPaymentDeadline().isBefore(LocalDateTime.now())) {
            r.setStatus(ReservationStatusEnum.CANCELLED);
            throw new RuntimeException("Reservation expired");
        }
        Payment payment = Payment.builder()
                .reservation(r)
                .amount(r.getTotalPrice())
                .paymentDate(LocalDateTime.now())
                .status(PaymentStatusEnum.PAID).build();

        paymentRepository.save(payment);
        r.setStatus(ReservationStatusEnum.ACCEPTED);
    }

}
