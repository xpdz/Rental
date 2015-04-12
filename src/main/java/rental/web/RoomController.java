package rental.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rental.domain.Room;
import rental.service.RoomRepository;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value="/rooms")
public class RoomController {
    private Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String listRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "room_list";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    @Secured("ROLE_USER")
    public String showRoomsForm(Room room) {
        return "room_from";
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
    public String postRoom(@ModelAttribute Room room, BindingResult result) {
        if (result.hasErrors()) {
            return "room_form";
        }

        roomRepository.save(room);
        return "room_list";
    }

    @RequestMapping(value = "/{room}", method = RequestMethod.PUT)
    @Secured("ROLE_USER")
    public String editRoom(@Valid Room room, BindingResult result) {
        if (result.hasErrors()) {
            return "room_form";
        }

        roomRepository.save(room);
        return "room_list";
    }
}
