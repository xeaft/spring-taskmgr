package dev.kvejp.taskmgr.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable=false, unique=true)
    protected String username;
    @Column(name = "PASSWORD", nullable = false)
    protected String password;
    @Column(name = "PROFILE_COLOR", nullable = false)
    protected String profileColor;
    @Column(name = "PROFILE_PICTURE")
    protected String profilePic = "";
    @Column(name = "HAS_PROFILE_PIC", nullable = false)
    protected boolean hasProfilePic;
    @Column(name = "USER_ROLE", nullable = false)
    protected String role;

    @OneToMany(mappedBy = "owner")
    public List<Setting> settings = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    public List<Task> tasks = new ArrayList<>();

    public UserDTO() {}

    public UserDTO(String username, String password, String profileColor) {
        this.username = username;
        this.password = password;
        this.profileColor = profileColor;
        this.hasProfilePic = false;
        this.profilePic = "";
        this.role = "user";
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String encode) {
        this.password = encode;
    }

    public String getProfileColor() {
        return profileColor;
    }
    public String getProfilePic() {
        return profilePic;
    }

    public boolean getHasProfilePic() {
        return hasProfilePic;
    }

    public void setHasProfilePic(boolean hasProfilePic) {
        this.hasProfilePic = hasProfilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Setting getSetting(String name) {
        for (Setting setting : settings) {
            if (setting.getName().equals(name)) {
                return setting;
            }
        }
        return null;
    }

    public List<Setting> getSettings() {
        return settings;
    }
}
