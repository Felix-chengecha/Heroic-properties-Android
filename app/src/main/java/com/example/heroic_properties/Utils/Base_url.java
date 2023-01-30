package com.example.heroic_properties.Utils;

public class Base_url {
    private static String mainUrl = "https://1156-105-61-238-65.in.ngrok.io/";
//    private static String authUrl = "http://35.232.36.71:8081";



    public static String getallproperties(){
        return mainUrl + "api/properties/category";
    }

    public static String getnearbyproperty() {
        return mainUrl + "api/properties/nearby";
    }

    public static String getpropertyimages(){
        return mainUrl + "api/properties/images";
    }

    public static String getproductdetails(){
        return mainUrl + "api/properties/details";
    }

    public static String getuserdetails(){
        return mainUrl + "api/properties/userdetails";
    }

    public static String getcategory(String category) {
        return mainUrl + "api/properties/category?category" +category;
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
        return mainUrl + "api/properties/login";
    }

    public static  String registerurl(){
        return mainUrl + "api/properties/register";
    }
}
