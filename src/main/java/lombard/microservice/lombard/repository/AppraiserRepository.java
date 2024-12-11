package lombard.microservice.lombard.repository;

import lombard.microservice.lombard.entity.Appraiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppraiserRepository extends JpaRepository<Appraiser, Long> {
    @Query(value = "SELECT least_busy_appraiser()", nativeQuery = true)
    Long leastBusyAppraiser();
}
