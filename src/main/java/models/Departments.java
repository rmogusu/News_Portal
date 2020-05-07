package models;

public class Departments {
    private String name;
    private String description;
    private String totalNumber;
    private String email;
    private String address;
    private String website;
    private int id;
    public Departments(String name, String description , String totalNumber , String email, String address , String website ) {
        this.name = name;
        this.description  = description  ;
        this.totalNumber  =totalNumber ;
        this.email  = email;
        this.address  = address ;
        this.website = website ;
    }
}
