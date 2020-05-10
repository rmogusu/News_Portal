package dao;

import models.Departments;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oNewsDao implements NewsDao {

    private final Sql2o sql2o;
    public Sql2oNewsDao(Sql2o sql2o){ this.sql2o = sql2o; }

    @Override
    public void add(News news) {
        String sql = "INSERT INTO news (general,department) VALUES (:general,:department)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            news.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<News> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM news")
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from news WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from news";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void addNewsToDepartments(News  news, Departments  departments ){
            String sql = "INSERT INTO departments_news (departmentId, newsId) VALUES (:departmentId, :newsId)";
            try (Connection con = sql2o.open()) {
                con.createQuery(sql)
                        .addParameter("departmentId", departments.getId())
                        .addParameter("newsId", news.getId())
                        .executeUpdate();
            } catch (Sql2oException ex){
                System.out.println(ex);
            }
        }


    @Override
    public List<Departments> getAllDepartmentsByNews(int newsId) {
        ArrayList<Departments> departments = new ArrayList();
            String joinQuery = "SELECT departmentId FROM departments_news WHERE newsId = :newsId";
            try (Connection con = sql2o.open()) {
                List<Integer> allDepartmentsIds = con.createQuery(joinQuery)
                        .addParameter("newsId", newsId)
                        .executeAndFetch(Integer.class);
                for (Integer departmentId : allDepartmentsIds){
                    String departmentsQuery = "SELECT * FROM departments WHERE id = :departmentId";
                    departments.add(
                            con.createQuery(departmentsQuery)
                                    .addParameter("departmentId", departmentId)
                                    .executeAndFetchFirst(Departments.class));
                }
            } catch (Sql2oException ex){
                System.out.println(ex);
            }
        return departments;

    }


}


