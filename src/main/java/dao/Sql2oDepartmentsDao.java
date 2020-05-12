package dao;

import models.Departments;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentsDao implements DepartmentsDao  {

    private final Sql2o sql2o;
    public Sql2oDepartmentsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Departments   departments ) {
        String sql = "INSERT INTO departments (name, description, totalNumber) VALUES (:name, :description, :totalNumber)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(departments)
                    .executeUpdate()
                    .getKey();
            departments .setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Departments> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments ORDER BY name ASC;")
                    .executeAndFetch(Departments.class);
        }
    }
    @Override
    public Departments  findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Departments.class);
        }
    }

    @Override
    public void update(int id, String newName, String newDescription, int newTotalNumber) {
        String sql = "UPDATE departments SET (name,description, totalNumber) = (:name, :description, :totalNumber) WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("description", newDescription)
                    .addParameter("totalNumber", newTotalNumber)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void deleteById(int id) {
        String sql = "DELETE from departments WHERE id = :id";
        String deleteJoin = "DELETE from departments_news WHERE departmentId = :departmentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("departmentId", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void addDepartmentsToNews(Departments  departments , News news ){
            String sql = "INSERT INTO departments_news (departmentId, newsId) VALUES (:departmentId, :newsId)";
            try (Connection con = sql2o.open()) {
                con.createQuery(sql)
                        .addParameter("departmentId", departments .getId())
                        .addParameter("newsId", news.getId())
                        .executeUpdate();
            } catch (Sql2oException ex){
                System.out.println(ex);
            }
        }


    @Override
    public List<News> getAllNewsByDepartments(int departmentId){
        ArrayList<News> news = new ArrayList();
        String joinQuery = "SELECT newsId FROM departments_news WHERE departmentId = :departmentId";
            try (Connection con = sql2o.open()) {
                List<Integer> allNewsIds = con.createQuery(joinQuery)
                        .addParameter("departmentId", departmentId)
                        .executeAndFetch(Integer.class);
                for (Integer newsId : allNewsIds){
                    String NewsQuery = "SELECT * FROM news WHERE id = :newsId";
                    news.add(
                            con.createQuery(NewsQuery)
                                    .addParameter("newsId", newsId)
                                    .executeAndFetchFirst(News.class));
                }
            } catch (Sql2oException ex){
                System.out.println(ex);
            }
        return news;
        }

}


