package comsep_23.JetEco.repository;

import comsep_23.JetEco.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByPhone(String phone);
    Optional<Client> findByEmail(String email);

    long countByActiveTrue();

    Optional<Client> findByName(String username);
}
