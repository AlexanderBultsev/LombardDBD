package lombard.microservice.lombard.controller;

import lombard.microservice.lombard.entity.Manager;
import lombard.microservice.lombard.entity.PurchaseOffer;
import lombard.microservice.lombard.entity.PurchaseRequest;
import lombard.microservice.lombard.entity.Purchaser;
import lombard.microservice.lombard.service.ManagerService;
import lombard.microservice.lombard.service.PurchaseOfferService;
import lombard.microservice.lombard.service.PurchaseRequestService;
import lombard.microservice.lombard.service.PurchaserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PurchaseController {

    private final ManagerService managerService;
    private final PurchaseRequestService purchaseRequestService;
    private final PurchaseOfferService purchaseOfferService;
    private final PurchaserService purchaserService;

    @Autowired
    public PurchaseController(ManagerService managerService, PurchaseRequestService purchaseRequestService, PurchaseOfferService purchaseOfferService, PurchaserService purchaserService) {
        this.managerService = managerService;
        this.purchaseRequestService = purchaseRequestService;
        this.purchaseOfferService = purchaseOfferService;
        this.purchaserService = purchaserService;
    }

    @GetMapping("/manager/list")
    public String managerList(Model model) {
        model.addAttribute("managers", managerService.getAll());
        return "manager/manager_list";
    }

    @GetMapping("/manager/{id}")
    public String managerDetail(@PathVariable Long id, Model model) {
        Manager manager = managerService.getById(id);
        model.addAttribute("manager", manager);
        model.addAttribute("purchaseRequests", manager.getPurchaseRequests());
        return "manager/manager_detail";
    }

    @GetMapping("/purchase_request/list")
    public String purchaseRequestList(Model model) {
        model.addAttribute("purchaseRequests", purchaseRequestService.getAll());
        return "purchase_request/purchase_request_list";
    }

    @GetMapping("/purchase_request/{id}")
    public String purchaseRequestDetail(@PathVariable Long id, Model model) {
        PurchaseRequest purchaseRequest = purchaseRequestService.getById(id);
        model.addAttribute("purchaseRequest", purchaseRequest);
        model.addAttribute("manager", purchaseRequest.getManager());
        model.addAttribute("property", purchaseRequest.getProperty());
        return "purchase_request/purchase_request_detail";
    }

    @GetMapping("/purchase_offer/list")
    public String purchaseOfferList(Model model) {
        model.addAttribute("purchaseOffers", purchaseOfferService.getAll());
        return "purchase_offer/purchase_offer_list";
    }

    @GetMapping("/purchase_offer/{id}")
    public String purchaseOfferDetail(@PathVariable Long id, Model model) {
        PurchaseOffer purchaseOffer = purchaseOfferService.getById(id);
        model.addAttribute("purchaseOffer", purchaseOffer);
        model.addAttribute("purchaser", purchaseOffer.getPurchaser());
        model.addAttribute("property", purchaseOffer.getProperty());
        return "purchase_offer/purchase_offer_detail";
    }

    @GetMapping("/purchaser/list")
    public String purchaserList(Model model) {
        model.addAttribute("purchasers", purchaserService.getAll());
        return "purchaser/purchaser_list";
    }

    @GetMapping("purchaser/{id}")
    public String purchaserDetail(@PathVariable Long id, Model model) {
        Purchaser purchaser = purchaserService.getById(id);
        model.addAttribute("purchaser", purchaser);
        model.addAttribute("balance", purchaserService.getBalance(purchaser.getId()));
        model.addAttribute("purchaseOffers", purchaser.getPurchaseOffers());
        return "purchaser/purchaser_detail";
    }
}
