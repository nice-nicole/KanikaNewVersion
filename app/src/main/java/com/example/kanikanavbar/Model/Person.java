package com.example.kanikanavbar.Model;

public class Person {
    private String id;
    private String name;
    private String email;
    private String password;
    private String telephone;
    private String type;
    private String description;


    public Person(String id, String name, String email, String telephone, String type, String description) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.type = type;
        this.description = description;
    }

    public Person() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
