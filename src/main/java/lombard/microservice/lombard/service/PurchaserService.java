package lombard.microservice.lombard.service;

import lombard.microservice.lombard.entity.Purchaser;
import lombard.microservice.lombard.repository.PurchaserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PurchaserService {

    private final PurchaserRepository purchaserRepository;

    @Autowired
    public PurchaserService(PurchaserRepository purchaserRepository) {
        this.purchaserRepository = purchaserRepository;
    }

    public Purchaser getById(Long id) {
        return purchaserRepository.findById(id).orElse(null);
    }

    public List<Purchaser> getAll() {
        return purchaserRepository.findAll();
    }

    public BigDecimal getBalance(Long id) {
        return purchaserRepository.pricePurchaserProperties(id);
    }
}
