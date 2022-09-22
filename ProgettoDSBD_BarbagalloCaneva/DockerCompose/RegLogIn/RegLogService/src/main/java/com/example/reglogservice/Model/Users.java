package com.example.reglogservice.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message="name cannot be null")
    private String name;

    @NotNull(message="surname cannot be null")
    private String surname;

    @NotNull(message="username cannot be null")
    @Column(unique=true)
    private String username;

    @NotNull(message="psw cannot be null")
    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private String pass;

    @NotNull(message="type cannot be null")
    private String type;
    //d=doctor else r=receptionist


    public Integer getId() {
        return id;
    }

    public Users setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Users setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public Users setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Users setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPass() {
        return pass;
    }

    public Users setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public String getType() {
        return type;
    }

    public Users setType(String type) {
        this.type = type;
        return this;
    }

}
