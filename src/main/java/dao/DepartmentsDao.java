package dao;

import models.Departments;
import models.News;

import java.util.List;

public interface DepartmentsDao {
    //create
    void add(Departments  departments);
     void addDepartmentsToNews(Departments departments, News news);

    //read
    List<Departments> getAll();
    Departments  findById(int id);
    List<News> getAllNewsByDepartments(int departmentId);

    //update
    void update(int id, String name, String description, int totalNumber);

    //delete
    void deleteById(int id);
    void clearAll();
}
