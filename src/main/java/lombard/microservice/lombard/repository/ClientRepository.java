package lombard.microservice.lombard.repository;

import lombard.microservice.lombard.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "SELECT price_client_properties(:clientId)", nativeQuery = true)
    BigDecimal priceClientProperties(@Param("clientId") Long clientId);
}
