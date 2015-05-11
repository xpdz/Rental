package rental.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import rental.domain.Account;
import rental.domain.Room;
import rental.service.AccountRepository;
import rental.service.RoomRepository;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
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
    public String listRooms(Model model) {
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        logger.info(":::Find all rooms, size="+rooms.size());
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
        logger.info(":::Show room detail, roomID="+id);
        return "room_detail";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Secured("ROLE_USER")
    public String deleteRoom(@PathVariable Long id) {
        roomRepository.delete(id);
        return "room_list";
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "multipart/form-data")
    @Secured("ROLE_USER")
    public String postRoom(@Valid @ModelAttribute Room room, BindingResult result,
                           Model model, @RequestParam("file") MultipartFile[] files) {
        if (result.hasErrors()) {
            return "room_form";
        }

        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        File userPhotoPath = new File(System.getProperty("user.home") + File.separator + "site_data"
                + File.separator + "photos" + File.separator + username);
        userPhotoPath.mkdirs();

        List<String> photoUris = new ArrayList<>(5);

        for (int i = 0; i < files.length; i++) {
            File photoFile = new File(userPhotoPath, files[i].getOriginalFilename());
            if (!files[i].isEmpty()) {
                try {
                    byte[] bytes = files[i].getBytes();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(photoFile));
                    stream.write(bytes);
                    stream.close();
                    photoUris.add("/photos/" + username + "/" + files[i].getOriginalFilename());
                    logger.info("::: Post room, uploaded " + photoFile + "!");
                } catch (Exception e) {
                    logger.info("::: Post room upload error: " + photoFile + " => " + e.getMessage());
                }
            } else {
                logger.info("::: Post room upload error: file " + files[i].getOriginalFilename() + " was empty.");
            }
        }

        room.setPhotos(photoUris);

        Account account = accountRepository.findByUsername(username);
        logger.info(":::Post room, user=" + username);
        room.setAccountId(account.getId());

//        resolveGeocoding(room);

        room.setLastModified(new Date());
        roomRepository.save(room);
        return "room_list";
    }

    private void resolveGeocoding(Room room) {
        String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + room.getAddress() + "&sensor=false";
        ResponseEntity<String> entity;
        try {
            entity = new RestTemplate().getForEntity(url, String.class);
        } catch (Exception e) {
            logger.info(":::Get Google api failed, retry with proxy, exption: " + e);
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            Proxy proxy= new Proxy(Proxy.Type.HTTP, new InetSocketAddress("3.87.248.1", 88));
            requestFactory.setProxy(proxy);
            entity = new RestTemplate(requestFactory).getForEntity(url, String.class);
        }
        HttpStatus status = entity.getStatusCode();
        if (status.is2xxSuccessful()) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode;
            try {
                jsonNode = mapper.readTree(entity.getBody());
            } catch (IOException e) {
                logger.info(":::Parse json failed, exption: " + e);
                return;
            }
            String googleStatus = jsonNode.get("status").asText();
            if ("OK".equals(googleStatus)) {
                JsonNode latlng = jsonNode.get("results").get(0).get("geometry").get("location");
                double lat = latlng.get("lat").asDouble();
                room.setLat(lat);
                double lng = latlng.get("lng").asDouble();
                room.setLat(lng);
                logger.info(":::Got Geocoding from Google, Lat: " + lat + ", Lng: " + lng);
            } else {
                logger.error(":::Google Geocoding return status other than OK, the status is: " + googleStatus);
            }
        } else {
            logger.error(":::Error while parse address to geocoding, the status is: " + status);
        }
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

    @RequestMapping(value="/photo/upload", method=RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                File photoFile = new File(name);
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(photoFile));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
}
