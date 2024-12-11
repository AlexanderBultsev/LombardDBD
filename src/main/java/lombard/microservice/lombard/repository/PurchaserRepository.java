package lombard.microservice.lombard.repository;

import lombard.microservice.lombard.entity.Purchaser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PurchaserRepository extends JpaRepository<Purchaser, Long> {
    @Query(value = "SELECT price_purchaser_properties(:purchaserId)", nativeQuery = true)
    BigDecimal pricePurchaserProperties(@Param("purchaserId") Long purchaserId);
}
