package lombard.microservice.lombard.controller;

import lombard.microservice.lombard.entity.Client;
import lombard.microservice.lombard.entity.Loan;
import lombard.microservice.lombard.service.ClientService;
import lombard.microservice.lombard.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LoanController {

    private final ClientService clientService;
    private final LoanService loanService;

    @Autowired
    public LoanController(ClientService clientService, LoanService loanService) {
        this.clientService = clientService;
        this.loanService = loanService;
    }

    @GetMapping("/client/list")
    public String clientList(Model model) {
        model.addAttribute("clients", clientService.getAll());
        return "client/client_list";
    }

    @GetMapping("/client/{id}")
    public String clientDetail(Model model, @PathVariable Long id) {
        Client client = clientService.getById(id);
        model.addAttribute("client", client);
        model.addAttribute("loans", client.getLoans());
        model.addAttribute("balance", clientService.getBalance(client.getId()));
        return "client/client_detail";
    }

    @GetMapping("/loan/list")
    public String loanList(Model model) {
        model.addAttribute("loans", loanService.getAll());
        return "loan/loan_list";
    }

    @GetMapping("/loan/{id}")
    public String loanDetail(Model model, @PathVariable Long id) {
        Loan loan = loanService.getById(id);
        model.addAttribute("loan", loan);
        model.addAttribute("client", loan.getClient());
        model.addAttribute("property", loan.getProperty());
        return "loan/loan_detail";
    }
}
