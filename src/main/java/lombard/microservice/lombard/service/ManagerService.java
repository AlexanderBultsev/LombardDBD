package lombard.microservice.lombard.service;

import lombard.microservice.lombard.entity.Manager;
import lombard.microservice.lombard.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public Manager getById(Long id) {
        return managerRepository.findById(id).orElse(null);
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    public Manager getLeastBusy() {
        Long id = managerRepository.leastBusyManager();
        return getById(id);
    }
}
