package rental.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rental.domain.Want;
import rental.service.WantRepository;

@Controller
@RequestMapping(value="/wants")
public class WantController {
    private Logger logger = LoggerFactory.getLogger(WantController.class);

    @Autowired
    private WantRepository wantRepository;

    @RequestMapping(value = "", method= RequestMethod.POST)
    public Want addWant() {
        return null;
    }
}
