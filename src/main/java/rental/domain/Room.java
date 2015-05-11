package rental.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room extends Post implements Serializable {
    private String title;
    private String count;
    private boolean petAllowed;
    private String lessorIdentity;
    private String term;
    private int bedroomCount;
    private int livingRoomCount;
    private int bathroomCount;
    private int totalMembers;
    private String utilities;

    @ElementCollection
    private List<String> photos = new ArrayList<>(10);

    @OneToMany(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "room_id", referencedColumnName="id")
    private List<RoomComment> roomComments = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isPetAllowed() {
        return petAllowed;
    }

    public void setPetAllowed(boolean petAllowed) {
        this.petAllowed = petAllowed;
    }

    public String getLessorIdentity() {
        return lessorIdentity;
    }

    public void setLessorIdentity(String lessorIdentity) {
        this.lessorIdentity = lessorIdentity;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getBedroomCount() {
        return bedroomCount;
    }

    public void setBedroomCount(int bedroomCount) {
        this.bedroomCount = bedroomCount;
    }

    public int getLivingRoomCount() {
        return livingRoomCount;
    }

    public void setLivingRoomCount(int livingRoomCount) {
        this.livingRoomCount = livingRoomCount;
    }

    public int getBathroomCount() {
        return bathroomCount;
    }

    public void setBathroomCount(int bathroomCount) {
        this.bathroomCount = bathroomCount;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }

    public String getUtilities() {
        return utilities;
    }

    public void setUtilities(String utilities) {
        this.utilities = utilities;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<RoomComment> getRoomComments() {
        return roomComments;
    }

    public void setRoomComments(List<RoomComment> roomComments) {
        this.roomComments = roomComments;
    }
}
