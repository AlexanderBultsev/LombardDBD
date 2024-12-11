package lombard.microservice.lombard.service;

import lombard.microservice.lombard.entity.Loan;
import lombard.microservice.lombard.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan getById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    public List<Loan> getAll() {
        return loanRepository.findAll();
    }
}
