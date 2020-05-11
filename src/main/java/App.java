import com.google.gson.Gson;

import dao.Sql2oDepartmentsDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUsersDao;
import dao.UsersDao;
import models.Departments;
import models.News;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        Sql2oNewsDao newsDao;
        Sql2oDepartmentsDao departmentsDao;
        Sql2oUsersDao usersDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/news.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "rose", "wambua");

        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
        conn = sql2o.open();

        //CREATE
        post("/departments/:departmentId/news/:newsId", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            int newsId = Integer.parseInt(req.params("newsId"));
            Departments  departments = departmentsDao.findById(departmentId);
            News  news = newsDao.findById(newsId);

            if (departments  != null && news != null){
                //both exist and can be associated - we should probably not connect things that are not here.
                newsDao.addNewsToDepartments(news ,departments);
                res.status(201);
                return gson.toJson(String.format("Departments '%s' and News '%s' have been associated",news.getGeneral() , departments .getName()));
            }
            else {
                throw new ApiException(404, String.format("Department or News does not exist"));
            }
        });
        post("/departments/new", "application/json", (req, res) -> {
            Departments departments = gson.fromJson(req.body(), Departments.class);
            departmentsDao.add(departments);
            res.status(201);
            return gson.toJson(departments);
        });
        post("/departments/:departmentId/users/new", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("departmentId"));
            Users users = gson.fromJson(req.body(), Users.class);

            users.setDepartmentId(departmentId);
            usersDao.add(users);
            res.status(201);
            return gson.toJson(users);
        });
        post("/news/new", "application/json", (req, res) -> {
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });
        //READ
        get("/departments", "application/json", (req, res) -> {
            return gson.toJson(departmentsDao.getAll());
        });
        get("/news", "application/json", (req, res) -> {
            return gson.toJson(newsDao.getAll());
        });
        get("/departments/:id", "application/json", (req, res) -> {
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            return gson.toJson(departmentsDao.findById(departmentId));
        });
        get("/news/:id", "application/json", (req, res) -> {
            res.type("application/json");
            int newsId = Integer.parseInt(req.params("id"));
            return gson.toJson(newsDao.findById(newsId));
        });
        get("/departments/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Departments  departmentsToFind = departmentsDao.findById(departmentId);
            if (departmentsToFind == null){
                throw new ApiException(404, String.format("No departments with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentsDao.Departments(departmentId).size()==0){
                return "{\"message\":\"I'm sorry, but no News are listed for this Department.\"}";
            }
            else {
                return gson.toJson(departmentsDao.getAllNewsByDepartments(departmentId));
            }
        });

        get("/news/:id/departments", "application/json", (req, res) -> {
            int newsId = Integer.parseInt(req.params("id"));
            News  newsToFind = newsDao.findById(newsId);
            if (newsToFind == null){
                throw new ApiException(404, String.format("No news with the id: \"%s\" exists", req.params("id")));
            }
            else if (newsDao.getAllDepartmentsByNews(newsId) .size()==0){
                return "{\"message\":\"I'm sorry, but no departments are listed for this news.\"}";
            }
            else {
                return gson.toJson(newsDao.getAllDepartmentsByNews(newsId) );
            }
        });
//        get("/departments/:id/users", "application/json", (req, res) -> {
//            int departmentId = Integer.parseInt(req.params("id"));
//
//            Departments departmentsToFind = departmentsDao.findById(departmentId);
//
//            if (departmentsToFind == null){
//                throw new ApiException(404, String.format("No departments with the id: \"%s\" exists", req.params("id")));
//            }
//
//            allUsers = UsersDao.getAllUsersByDepartments(departmentId);
//
//            return gson.toJson(allUsers);
//        });
        //FILTERS
//        exception(ApiException.class, (exception, req, res) -> {
//            ApiException err = (ApiException) exception;
//            Map<String, Object> jsonMap = new HashMap<>();
//            jsonMap.put("status", err.getStatusCode());
//            jsonMap.put("errorMessage", err.getMessage());
//            res.type("application/json");
//            res.status(err.getStatusCode());
//            res.body(gson.toJson(jsonMap));
//        });


        after((req, res) ->{
            res.type("application/json");
        });
    }
}
