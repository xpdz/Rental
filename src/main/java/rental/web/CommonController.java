package rental.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

    @RequestMapping(value="/")
    public String index(Model model) {
        return "index";
    }

    @RequestMapping(value="/login")
    public String loginForm(Model model) {
        return "login";
    }

    @RequestMapping(value="/signup")
    public String signupForm(Model model) {
        return "signup";
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public String signup(Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "login";
        }

        return "index";
    }
}
