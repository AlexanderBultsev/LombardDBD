package lombard.microservice.lombard.service;

import lombard.microservice.lombard.entity.Property;
import lombard.microservice.lombard.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Property getById(Long id) {
        return propertyRepository.findById(id).orElse(null);
    }

    public Property add(String name, Long clientId) {
        Long id = propertyRepository.createProperty(name, clientId);
        return getById(id);
    }

    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    public Property redeem(Long id) {
        propertyRepository.redeemProperty(id);
        return getById(id);
    }

    public Property offer(Long id) {
        propertyRepository.offerProperty(id);
        return getById(id);
    }
}
