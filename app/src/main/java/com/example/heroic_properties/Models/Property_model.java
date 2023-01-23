package com.example.heroic_properties.Models;

public class Property_model {

    private String propid;
    private String pname;
    private String location;
    private String image;
    private String cost;
    private  String type;

    public Property_model(String propid, String pname, String location, String image, String cost, String type) {
        this.propid = propid;
        this.pname = pname;
        this.location = location;
        this.image = image;
        this.cost = cost;
        this.type = type;
    }

    public String getPropid() {
        return propid;
    }

    public void setPropid(String propid) {
        this.propid = propid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}