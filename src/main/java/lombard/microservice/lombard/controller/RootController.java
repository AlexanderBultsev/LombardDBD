package lombard.microservice.lombard.controller;

import lombard.microservice.lombard.entity.Property;
import lombard.microservice.lombard.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RootController {

    private final PropertyService propertyService;
    private static final String REDIRECT_PROPERTY = "redirect:/property/";

    @Autowired
    public RootController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/property/list")
    public String propertyList(Model model) {
        model.addAttribute("properties", propertyService.getAll());
        return "property/property_list";
    }

    @GetMapping("/property/{id}")
    public String propertyDetail(@PathVariable Long id, Model model) {
        Property property = propertyService.getById(id);
        model.addAttribute("property", property);
        model.addAttribute("loan", property.getLoan());
        model.addAttribute("appraisalRequest", property.getAppraisalRequest());
        model.addAttribute("purchaseRequest", property.getPurchaseRequest());
        model.addAttribute("purchaseOffer", property.getPurchaseOffer());
        return "property/property_detail";
    }

    @PostMapping("/property/add")
    public String propertyAdd(@RequestParam(name="name") String name,
                              @RequestParam(name="id") Long id) {
        Property property = propertyService.add(name, id);
        return REDIRECT_PROPERTY + property.getId();
    }

    @PostMapping("/property/redeem")
    public String propertyRedeem(@RequestParam(name="id") Long id) {
        Property property = propertyService.redeem(id);
        return REDIRECT_PROPERTY + property.getId();
    }

    @PostMapping("/property/offer")
    public String propertyOffer(@RequestParam(name="id") Long id) {
        Property property = propertyService.offer(id);
        return REDIRECT_PROPERTY + property.getId();
    }
}
