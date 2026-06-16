package pl.dmcs.jmazur.service;

import pl.dmcs.jmazur.domain.Reservation;
import pl.dmcs.jmazur.dto.ReservationAdminDto;
import pl.dmcs.jmazur.dto.UserReservationDto;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    void markAsPaid(String reservationUUID);

    Reservation createPendingReservation(String carUUID, LocalDate from, LocalDate to, String name);

    List<UserReservationDto> findReservationsByUser(String name);

    void cancelReservation(String uuid);

    List<ReservationAdminDto> findAll();

    void acceptReservation(String uuid);

    void rejectReservation(String uuid);
}
