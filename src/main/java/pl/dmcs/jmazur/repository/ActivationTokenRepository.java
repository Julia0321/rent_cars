package pl.dmcs.jmazur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dmcs.jmazur.domain.ActivationToken;

import java.util.Optional;

@Repository
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {

    @Query("SELECT a FROM ActivationToken a JOIN FETCH a.user WHERE a.token= :token")
    Optional<ActivationToken> findByToken(@Param("token") String token);
}
