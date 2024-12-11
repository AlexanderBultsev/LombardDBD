package lombard.microservice.lombard.service;

import lombard.microservice.lombard.entity.PurchaseRequest;
import lombard.microservice.lombard.repository.PurchaseRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseRequestService {

    private final PurchaseRequestRepository purchaseRequestRepository;

    @Autowired
    public PurchaseRequestService(PurchaseRequestRepository purchaseRequestRepository) {
        this.purchaseRequestRepository = purchaseRequestRepository;
    }

    public PurchaseRequest getById(Long id) {
        return purchaseRequestRepository.findById(id).orElse(null);
    }

    public List<PurchaseRequest> getAll() {
        return purchaseRequestRepository.findAll();
    }
}
