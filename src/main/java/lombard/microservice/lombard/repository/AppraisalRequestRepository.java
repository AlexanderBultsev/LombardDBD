package lombard.microservice.lombard.repository;

import lombard.microservice.lombard.entity.AppraisalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface AppraisalRequestRepository extends JpaRepository<AppraisalRequest, Long> {
    @Modifying
    @Transactional
    @Query(value = "CALL evaluate_appraisal(:appraisalRequestId, :price)", nativeQuery = true)
    void evaluateAppraisal(@Param("appraisalRequestId") Long appraisalRequestId, @Param("price") BigDecimal price);
}
