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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTotalNumber() {
        return totalNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTotalNumber(String totalNumber) {
        this.totalNumber = totalNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setId(int id) {
        this.id = id;
    }
}
