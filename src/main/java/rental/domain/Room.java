package rental.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Room extends Post implements Serializable {
    private String title;
    private String address;
    private int price;
    private Date startDate;
    private Date toDate;
    private String roomType;
    private String count;
    private String gender;
    private boolean petAllowed;
    private String lessorIdentity;
    private String term;
    private int bedroomCount;
    private int livingRoomCount;
    private int bathroomCount;
    private int totalMembers;
    private String utilities;
    private String description;
    @OneToMany(fetch= FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "room_id", referencedColumnName="id")
    private List<Photo> photos = new ArrayList<>(10);
    @OneToMany(fetch= FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "room_id", referencedColumnName="id")
    private List<RoomComment> roomComments = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<RoomComment> getRoomComments() {
        return roomComments;
    }

    public void setRoomComments(List<RoomComment> roomComments) {
        this.roomComments = roomComments;
    }
}
