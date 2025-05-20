package comsep_23.JetEco.repository;

import comsep_23.JetEco.entity.Offer;
import comsep_23.JetEco.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByPartner(Partner partner);
    List<Offer> findByActiveTrue();

    long countByActiveTrue();
}
