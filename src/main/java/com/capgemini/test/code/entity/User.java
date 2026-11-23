package com.capgemini.test.code.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    private String email;

    @Setter
    private String dni;

    @Setter
    private String phone;

    @Setter
    @JsonProperty("rol")
    private String role;

    @Setter
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDni() {
        return dni;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public Room getRoom() {
        return room;
    }

}