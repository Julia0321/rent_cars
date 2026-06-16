package pl.dmcs.jmazur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dmcs.jmazur.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByFirstname(String firstname);

    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findUserByUuid(String uuid);
}
