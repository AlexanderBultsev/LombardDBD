package lombard.microservice.lombard.service;

import lombard.microservice.lombard.entity.AppraisalRequest;
import lombard.microservice.lombard.repository.AppraisalRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AppraisalRequestService {

    private final AppraisalRequestRepository appraisalRequestRepository;

    @Autowired
    public AppraisalRequestService(AppraisalRequestRepository repository) {
        this.appraisalRequestRepository = repository;
    }

    public AppraisalRequest getById(Long id) {
        return appraisalRequestRepository.findById(id).orElse(null);
    }

    public List<AppraisalRequest> getAll() {
        return appraisalRequestRepository.findAll();
    }

    public AppraisalRequest evaluate(Long id, BigDecimal price) {
        appraisalRequestRepository.evaluateAppraisal(id, price);
        return getById(id);
    }
}
