package rental.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rental.service.WantCommentRepository;

@Controller
@RequestMapping(value="/wantComments")
public class WantCommentController {
    private Logger logger = LoggerFactory.getLogger(WantCommentController.class);

    @Autowired
    private WantCommentRepository wantCommentRepository;
}
