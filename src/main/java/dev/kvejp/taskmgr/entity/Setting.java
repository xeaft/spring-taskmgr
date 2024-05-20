package dev.kvejp.taskmgr.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SETTINGS")
public class Setting {
    // TODO: use enums, find out how to use enums in spring

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID", nullable = false)
    protected UserDTO owner;

    @Column(name = "SETTING_NAME", nullable = false)
    protected String name;

    @Column(name = "SETTING_VALUE", nullable = false)
    protected String value;

    public Setting() {};
    public Setting(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
