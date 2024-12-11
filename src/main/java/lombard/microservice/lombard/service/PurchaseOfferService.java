package lombard.microservice.lombard.service;

import lombard.microservice.lombard.entity.PurchaseOffer;
import lombard.microservice.lombard.repository.PurchaseOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOfferService {

    private final PurchaseOfferRepository purchaseOfferRepository;

    @Autowired
    public PurchaseOfferService(PurchaseOfferRepository purchaseOfferRepository) {
        this.purchaseOfferRepository = purchaseOfferRepository;
    }

    public PurchaseOffer getById(Long id) {
        return purchaseOfferRepository.findById(id).orElse(null);
    }

    public PurchaseOffer add(Long purchaserId, Long purchaseRequestId) {
        Long id = purchaseOfferRepository.createPurchaseOffer(purchaserId, purchaseRequestId);
        return getById(id);
    }

    public List<PurchaseOffer> getAll() {
        return purchaseOfferRepository.findAll();
    }
}
