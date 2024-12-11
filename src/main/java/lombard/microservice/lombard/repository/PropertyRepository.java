package lombard.microservice.lombard.repository;

import lombard.microservice.lombard.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Procedure(procedureName = "create_property", outputParameterName = "property_id")
    Long createProperty(@Param("name") String name, @Param("client_id") Long clientId);

    @Procedure(procedureName = "offer_property")
    void offerProperty(@Param("property_id") Long propertyId);

    @Procedure(procedureName = "redeem_property")
    void redeemProperty(@Param("property_id") Long propertyId);
}
