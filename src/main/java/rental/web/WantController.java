package rental.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rental.domain.Account;
import rental.domain.Want;
import rental.service.AccountRepository;
import rental.service.WantRepository;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value="/wants")
public class WantController {
    private Logger logger = LoggerFactory.getLogger(WantController.class);

    @Autowired
    private WantRepository wantRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String listWants(Model model) {
        List<Want> wants = wantRepository.findAll();
        model.addAttribute("wants", wants);
        return "want_list";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @Secured("ROLE_USER")
    public String showWantForm(Want want) {
        return "want_form";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showWant(@PathVariable Long id, Model model) {
        Want want = wantRepository.findOne(id);
        model.addAttribute("want", want);
        return "want_detail";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Secured("ROLE_USER")
    public String deleteWant(@PathVariable Long id) {
        wantRepository.delete(id);
        return "want_list";
    }

    @RequestMapping(method = RequestMethod.POST)
    @Secured("ROLE_USER")
    public String postWant(@Valid @ModelAttribute Want want, BindingResult result, Model model, @AuthenticationPrincipal Account principal) {
        if (result.hasErrors()) {
            return "want_form";
        }

//        Account account = accountRepository.findByUsername(principal.getUsername());
//        want.setAccountId(account.getId());
        want.setLastModified(new Date());
        wantRepository.save(want);
        return "want_list";
    }

    @RequestMapping(value = "/{want}", method = RequestMethod.PUT)
    @Secured("ROLE_USER")
    public String editWant(@Valid @ModelAttribute Want want, BindingResult result) {
        if (result.hasErrors()) {
            return "want_form";
        }

        want.setLastModified(new Date());
        wantRepository.save(want);
        return "want_list";
    }
}
