package models;

import java.util.Objects;

public class Departments {
    private String name;
    private String description;
    private int totalNumber;
    private int id;

    public Departments(String name, String description , int totalNumber ) {
        this.name = name;
        this.description  = description  ;
        this.totalNumber  =totalNumber ;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTotalNumber() {
        return totalNumber;
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

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users )) return false;
        Departments  departments = (Departments  ) o;
        return id == departments .id &&
                Objects.equals(name, departments.name) &&
                Objects.equals(description  , departments .description ) &&
                totalNumber  == departments .totalNumber;

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description , totalNumber , id);
    }
}
