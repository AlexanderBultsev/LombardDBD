package lombard.microservice.lombard.repository;

import lombard.microservice.lombard.entity.PurchaseOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PurchaseOfferRepository extends JpaRepository<PurchaseOffer, Long> {
    @Modifying
    @Transactional
    @Query(value = "CALL create_purchase_offer(:purchaserId, :purchaseRequestId, ?1)", nativeQuery = true)
    Long createPurchaseOffer(@Param("purchaserId") Long purchaserId, @Param("purchaseRequestId") Long purchaseRequestId);
}
