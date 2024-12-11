package lombard.microservice.lombard.repository;

import lombard.microservice.lombard.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @Query(value = "SELECT least_busy_manager()", nativeQuery = true)
    Long leastBusyManager();
}
