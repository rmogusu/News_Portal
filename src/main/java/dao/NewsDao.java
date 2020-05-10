package dao;

import models.Departments;
import models.News;

import java.util.List;

public interface NewsDao {
    //create
    void add(News news);
    void addNewsToDepartments(News news, Departments department);

    //read
    List<News> getAll();
    List<Departments> getAllDepartmentsByNews(int NewsId);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}
