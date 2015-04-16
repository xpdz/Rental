package rental.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rental.domain.Account;
import rental.domain.Role;
import rental.service.AccountRepository;
import rental.service.RoleRepository;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value="/login")
    public String loginForm(Model model) {
        return "login";
    }

    @RequestMapping(value="/signup", method = RequestMethod.GET)
    public String signupForm(Model model) {
        model.addAttribute("loginOrSingup", "signup");
        return "login";
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String signup(@Valid @ModelAttribute Account account, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login";
        }

        account.setEnabled(true);
        account.setPassword(new BCryptPasswordEncoder(8).encode(account.getPassword()));
        accountRepository.save(account);

        Role role = new Role();
        role.setUsername(account.getUsername());
        role.setAuthority("ROLE_USER");
        roleRepository.save(role);

        return "index";
    }
}
