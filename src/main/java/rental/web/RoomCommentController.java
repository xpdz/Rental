package rental.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/roomComments")
public class RoomCommentController {
    private Logger logger = LoggerFactory.getLogger(RoomCommentController.class);

//    @Autowired
//    private RoomCommentRepository roomCommentRepository;
}
