package pl.dmcs.jmazur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dmcs.jmazur.domain.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("""
               select p from Payment p
               join fetch p.reservation r
               join fetch r.car
               join fetch r.user
               where p.uuid = :uuid
            """)
    Optional<Payment> findPaymentByUuid(@Param("uuid") String uuid);
}
