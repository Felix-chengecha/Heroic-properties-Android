package com.example.heroic_properties.Models;

public class Details_model {


    String id;
    String name;
    String category;
    String type;
    String description ;
    String contact ;
    String cost;

    public Details_model(String id, String name, String category, String type, String description, String contact, String cost) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.description = description;
        this.contact = contact;
        this.cost = cost;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
