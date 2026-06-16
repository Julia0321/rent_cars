package pl.dmcs.jmazur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dmcs.jmazur.domain.Car;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByUuid(String uuid);

    @Query("""
                SELECT c
                FROM Car c
                WHERE c.carStatus <> pl.dmcs.jmazur.enums.CarStatusEnum.UNAVAILABLE
                AND NOT EXISTS (
                  SELECT r
                  FROM Reservation r
                  WHERE r.car = c
                    AND r.from < :to AND r.to > :from
                    AND r.status IN (
                      pl.dmcs.jmazur.enums.ReservationStatusEnum.PENDING,
                      pl.dmcs.jmazur.enums.ReservationStatusEnum.ACCEPTED
                    )
                )
            """)
    List<Car> findAllCarsBetween(@Param("from") LocalDate from, @Param("to") LocalDate to);

    boolean existsByRegistrationNumber(String registrationNumber);

    @Modifying
    @Query("""
            UPDATE Car c
            SET c.carStatus = pl.dmcs.jmazur.enums.CarStatusEnum.RENTED
            WHERE c.carStatus <> pl.dmcs.jmazur.enums.CarStatusEnum.UNAVAILABLE
              AND EXISTS (
                SELECT r FROM Reservation r
                WHERE r.car = c
                  AND r.status = pl.dmcs.jmazur.enums.ReservationStatusEnum.ACCEPTED
                  AND r.from <= :today
                  AND r.to   >= :today
              )
            """)
    int setRentedForToday(@Param("today") LocalDate today);

    @Modifying
    @Query("""
            UPDATE Car c
            SET c.carStatus = pl.dmcs.jmazur.enums.CarStatusEnum.AVAILABLE
            WHERE c.carStatus = pl.dmcs.jmazur.enums.CarStatusEnum.RENTED
              AND NOT EXISTS (
                SELECT r FROM Reservation r
                WHERE r.car = c
                  AND r.status = pl.dmcs.jmazur.enums.ReservationStatusEnum.ACCEPTED
                  AND r.from <= :today
                  AND r.to   >= :today
              )
            """)
    int setAvailableIfNotRentedToday(@Param("today") LocalDate today);

}
