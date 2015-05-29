package rental.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rental.domain.Account;
import rental.domain.Room;
import rental.service.AccountRepository;
import rental.service.RoomRepository;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
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
    public String listRooms(@RequestParam(value="searchBox") String addr,
                            @RequestParam(value="pages", required = false) Integer pageNum, Model model) {
        if (pageNum == null) {
            pageNum = 1;
        }
        Pageable pageRequest = new PageRequest(pageNum - 1, 10, Sort.Direction.DESC, "lastModified");
        Page<Room> page = roomRepository.findByAddressContaining(addr, pageRequest);
        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 5, page.getTotalPages());

        model.addAttribute("rooms", page);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        model.addAttribute("searchText", addr);
        logger.info(":::Find rooms - addr: " + addr + ", page begin: " + begin + ", end: " + end + ", current: " + current
                + ", total pages=" + page.getTotalPages() + ", total elements: " + page.getTotalElements());
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
        logger.info(":::Show room detail, roomID=" + id);
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
    public String postRoom(@Valid @ModelAttribute Room room, BindingResult result, Model model,
                           @RequestParam("photoFiles[]") MultipartFile[] files) {
        if (result.hasErrors()) {
            model.addAttribute(room);
            return "redirect:/rooms/form";
        }

        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        File userPhotoPath = new File(System.getProperty("user.home") + File.separator + "site_data"
                + File.separator + "photos" + File.separator + username);
        userPhotoPath.mkdirs();

        List<String> photoUris = new ArrayList<>(5);

        for (MultipartFile file : files) {
            File photoFile = new File(userPhotoPath, file.getOriginalFilename());
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(photoFile));
                    stream.write(bytes);
                    stream.close();
                    photoUris.add("/photos/" + username + "/" + file.getOriginalFilename());
                    logger.info("::: Post room, uploaded " + photoFile + "!");
                } catch (Exception e) {
                    logger.info("::: Post room upload error: " + photoFile + " => " + e.getMessage());
                }
            } else {
                logger.info("::: Post room upload error: file " + file.getOriginalFilename() + " was empty.");
            }
        }

        room.setPhotos(photoUris);

        Account account = accountRepository.findByUsername(username);
        logger.info(":::Post room, user=" + username);
        room.setAccountId(account.getId());

        room.setLastModified(new Date());
        roomRepository.save(room);
        return "redirect:/rooms";
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
