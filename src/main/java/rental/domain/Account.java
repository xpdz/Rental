package rental.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class Account implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    @JoinColumn(name = "account_id", referencedColumnName="id")
    private List<Room> rooms = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "account_id", referencedColumnName="id")
    private List<Want> wants = new ArrayList<>();

    private String username;
    private String password;
    private boolean enabled;

    @Lob
    private byte[] avatar;

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Want> getWants() {
        return wants;
    }

    public void setWants(List<Want> wants) {
        this.wants = wants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
