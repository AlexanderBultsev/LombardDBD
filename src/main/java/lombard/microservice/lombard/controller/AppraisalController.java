package lombard.microservice.lombard.controller;

import lombard.microservice.lombard.entity.AppraisalRequest;
import lombard.microservice.lombard.entity.Appraiser;
import lombard.microservice.lombard.service.AppraisalRequestService;
import lombard.microservice.lombard.service.AppraiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class AppraisalController {

    private final AppraiserService appraiserService;
    private final AppraisalRequestService appraisalRequestService;

    @Autowired
    public AppraisalController(AppraiserService appraiserService, AppraisalRequestService appraisalRequestService) {
        this.appraiserService = appraiserService;
        this.appraisalRequestService = appraisalRequestService;
    }

    @GetMapping("/appraiser/list")
    public String appraiserList(Model model) {
        model.addAttribute("appraisers", appraiserService.getAll());
        return "appraiser/appraiser_list";
    }

    @GetMapping("/appraiser/{id}")
    public String appraiserDetail(Model model, @PathVariable Long id) {
        Appraiser appraiser = appraiserService.getById(id);
        model.addAttribute("appraiser", appraiser);
        model.addAttribute("appraisalRequests", appraiser.getAppraisalRequests());
        return "appraiser/appraiser_detail";
    }

    @GetMapping("/appraisal_request/list")
    public String appraisalRequestList(Model model) {
        model.addAttribute("appraisalRequests", appraisalRequestService.getAll());
        return "appraisal_request/appraisal_request_list";
    }

    @GetMapping("/appraisal_request/{id}")
    public String appraisalRequestDetail(Model model, @PathVariable Long id) {
        AppraisalRequest appraisalRequest = appraisalRequestService.getById(id);
        model.addAttribute("appraisalRequest", appraisalRequest);
        model.addAttribute("appraiser", appraisalRequest.getAppraiser());
        model.addAttribute("property", appraisalRequest.getProperty());
        return "appraisal_request/appraisal_request_detail";
    }

    @PostMapping("/appraisal_request/evaluate")
    public String evaluateAppraisalRequest(@RequestParam(name="price") BigDecimal price,
                                           @RequestParam(name="id") Long id) {
        AppraisalRequest appraisalRequest = appraisalRequestService.evaluate(id, price);
        return "redirect:/appraisal_request/" + appraisalRequest.getId();
    }
}
