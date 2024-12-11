package lombard.microservice.lombard.service;

import lombard.microservice.lombard.entity.Appraiser;
import lombard.microservice.lombard.repository.AppraiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppraiserService {

    private final AppraiserRepository appraiserRepository;

    @Autowired
    public AppraiserService(AppraiserRepository appraiserRepository) {
        this.appraiserRepository = appraiserRepository;
    }

    public Appraiser getById(Long id) {
        return appraiserRepository.findById(id).orElse(null);
    }

    public List<Appraiser> getAll() {
        return appraiserRepository.findAll();
    }

    public Appraiser getLeastBusy() {
        Long id = appraiserRepository.leastBusyAppraiser();
        return getById(id);
    }
}
