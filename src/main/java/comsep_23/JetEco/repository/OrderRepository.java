package comsep_23.JetEco.repository;

import comsep_23.JetEco.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByOfferId(Long offerId);

}
