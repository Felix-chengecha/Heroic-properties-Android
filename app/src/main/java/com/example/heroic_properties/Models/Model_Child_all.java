package com.example.heroic_properties.Models;

public class Model_Child_all {

    String propid;
    String image;
    String name;
    String cost;
    String loc;
    String type;

    public Model_Child_all(String propid, String image, String name, String cost, String loc, String type) {
        this.propid = propid;
        this.image = image;
        this.name = name;
        this.cost = cost;
        this.loc = loc;
        this.type = type;
    }

    public String getPropid() {
        return propid;
    }

    public void setPropid(String propid) {
        this.propid = propid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

