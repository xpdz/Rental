package rental.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
    @RequestMapping(value="/")
    public String index() {
        return "index";
    }

    @RequestMapping(value="/error")
    public String error() {
        return "error";
    }
}
