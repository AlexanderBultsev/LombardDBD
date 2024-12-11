package lombard.microservice.lombard.service;

import lombard.microservice.lombard.entity.Client;
import lombard.microservice.lombard.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public BigDecimal getBalance(Long id) {
        return clientRepository.priceClientProperties(id);
    }
}
