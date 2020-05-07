package models;

public class Users {
    private String name;
    private String position;
    private String role;
    private String email;
    private String badgeNo;
    private String department;
    private int id;

    public Users(String name, String position, String role, String email, String badgeNo, String department) {
        this.name = name;
        this.position = position;
        this.role = role;
        this.badgeNo = badgeNo;
        this.department = department;
        this.email = email;
    }
}
