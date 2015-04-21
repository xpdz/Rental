package rental.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rental.domain.Account;
import rental.domain.Room;
import rental.service.AccountRepository;
import rental.service.RoomRepository;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value="/rooms")
public class RoomController {
    private Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String listRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "room_list";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @Secured("ROLE_USER")
    public String showRoomForm(Room room) {
        return "room_form";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String showRoom(@PathVariable Long id, Model model) {
        Room room = roomRepository.findOne(id);
        model.addAttribute("room", room);
        return "room_detail";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Secured("ROLE_USER")
    public String deleteRoom(@PathVariable Long id) {
        roomRepository.delete(id);
        return "room_list";
    }

    @RequestMapping(method = RequestMethod.POST)
    @Secured("ROLE_USER")
    public String postRoom(@Valid @ModelAttribute Room room, BindingResult result, Model model, @AuthenticationPrincipal Account principal) {
        if (result.hasErrors()) {
            return "room_form";
        }

        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Account account = accountRepository.findByUsername(username);
        logger.info("==rental== User " + username + " post a room.");
        room.setAccountId(account.getId());

        room.setLastModified(new Date());
        roomRepository.save(room);
        return "room_list";
    }

    @RequestMapping(value = "/{room}", method = RequestMethod.PUT)
    @Secured("ROLE_USER")
    public String editRoom(@Valid @ModelAttribute Room room, BindingResult result) {
        if (result.hasErrors()) {
            return "room_form";
        }

        room.setLastModified(new Date());
        roomRepository.save(room);
        return "room_list";
    }
}
