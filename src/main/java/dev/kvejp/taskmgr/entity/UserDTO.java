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

    protected String password;

//    @Column(name = "USER_ROLE", nullable = false)
    protected String role; // TODO: role class

    @OneToMany(mappedBy = "owner")
    public List<Setting> settings = new ArrayList<>();

    public List<String> extraPermissions = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    public List<Task> tasks = new ArrayList<>();

    public UserDTO() {}

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(String username) {
        this.username = username;
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
}
