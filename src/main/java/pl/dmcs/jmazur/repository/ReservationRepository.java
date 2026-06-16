package pl.dmcs.jmazur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dmcs.jmazur.domain.Reservation;
import pl.dmcs.jmazur.enums.ReservationStatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStatusAndPaymentDeadlineBefore(
            ReservationStatusEnum status,
            LocalDateTime time
    );

    Optional<Reservation> findByUuid(String uuid);

    @Query("""
               select r from Reservation r
               join fetch r.car c
               left join fetch r.payment p
               where r.user.uuid = :uuid
               order by r.from desc
            """)
    List<Reservation> findReservationByUserUUID(@Param("uuid") String uuid);

    @Query("""
               select r from Reservation r
               join fetch r.car
               join fetch r.user
               left join fetch r.payment
               where r.uuid = :uuid
            """)
    Optional<Reservation> findReservationByUuid(@Param("uuid") String uuid);

    @Query("""
               select r from Reservation r
               join fetch r.car
               join fetch r.user
               left join fetch r.payment
            """)
    List<Reservation> findAllForAdmin();
}
