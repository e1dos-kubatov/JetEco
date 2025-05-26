package comsep_23.JetEco.repository;

import comsep_23.JetEco.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByPhone(String phone);

    long countByActiveTrue();

    Optional<Partner> findByName(String username);
}
