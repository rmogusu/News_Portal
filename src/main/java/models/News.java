package models;

import java.util.Objects;

public class News {
    private String general;
    private String department;
    private int id;
    public News(String general,String department ) {
        this.general  = general ;
        this.department = department ;
    }

    public String getGeneral() {
        return general;
    }

    public String getDepartment() {
        return department;
    }

    public int getId() {
        return id;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News )) return false;
        News  news = (News ) o;
        return id == news.id &&
                Objects.equals(general , news.general ) &&
                Objects.equals(department , news .department );
    }

    @Override
    public int hashCode() {
        return Objects.hash(general  , department , id);
    }
}

