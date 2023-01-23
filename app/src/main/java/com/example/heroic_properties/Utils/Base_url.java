package com.example.heroic_properties.Utils;

public class Base_url {
    private static String mainUrl = "http://35.232.36.71:8081/";
    private static String authUrl = "http://35.232.36.71:8081/";



    public static String getallproperties(){
        return mainUrl + "api/properties/all";
    }

    public static String getnearbyproperty(String loc) {
        return mainUrl + "/api/properties/nearby?location" + loc;
    }

    public static String getcategory(String category) {
        return mainUrl + "api/properties/category?category" +category;
    }

    public static String getpropertyimages(String prop_id){
        return mainUrl + "api/properties/images?prop_id" +prop_id;
    }

    public static String getproductdetails(String prop_id){
        return mainUrl + "/api/properties/details?prop_id" +prop_id;
    }

    public static String getuserdetails(String email){
        return mainUrl + "/api/properties/userdetails?email" +email;
    }

    public static String getlocationnames(){
        return mainUrl + "api/properties/locations";
    }

    public static  String  getpropnames(){
        return mainUrl + "api/properties/propertynames";
    }

    public static String searchprop(){
        return  mainUrl + "api/properties/search";
    }

    public static  String getamenities(String prop_id){
        return  mainUrl + "api/properties/amenities?prop_id" +prop_id;
    }

    public static  String loginurl(){
        return authUrl + "api/properties/login";
    }

    public static  String registerurl(){
        return authUrl + "api/properties/register";
    }
}
