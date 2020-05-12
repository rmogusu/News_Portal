import com.google.gson.Gson;

import dao.Sql2oDepartmentsDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUsersDao;
import dao.UsersDao;
import exceptions.ApiException;
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

        //String connectionString = "jdbc:postgresql://localhost:5432/news_portal";
        //Sql2o sql2o = new Sql2o(connectionString, "rose", "wambua");
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
                newsDao.addNewsToDepartments(news ,departments);
                res.status(201);
                return gson.toJson(String.format("Departments '%s' and News '%s' have been associated" , departments.getName() ,news.getDepartment()));
            }
            else {
                throw new ApiException(404, String.format("Department or News does not exist"));
            }
        });
        get("/departments/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Departments  departmentsToFind = departmentsDao.findById(departmentId);
            if (departmentsToFind == null){
                throw new ApiException(404, String.format("No departments with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentsDao.getAllNewsByDepartments(departmentId) .size()==0){
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
            System.out.println(departmentsDao.getAll());
            if(departmentsDao.getAll().size() > 0){
                return gson.toJson(departmentsDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        get("/departments/:id", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Departments  departmentsToFind = departmentsDao.findById(departmentId);
            if (departmentsToFind == null){
                throw new ApiException(404, String.format("No departments with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(departmentsToFind);
        });
        get("/departments/:id/users/:id", "application/json", (req, res) -> {
            int usersId = Integer.parseInt(req.params("id"));
            Users  usersToFind = usersDao.findById(usersId);
            if (usersToFind == null){
                throw new ApiException(404, String.format("No users with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(usersToFind);
        });
        get("/departments/:id/users", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Departments departmentsToFind = departmentsDao.findById(departmentId);
            List<Users> allUsers;
            if (departmentsToFind == null){
                throw new ApiException(404, String.format("No departments with the id: \"%s\" exists", req.params("id")));
            }

            allUsers = usersDao.getAllUsersByDepartments(departmentId);

            return gson.toJson(allUsers);
        });

        get("/news", "application/json", (req, res) -> {
            return gson.toJson(newsDao.getAll());
        });

        //create
        post("/departments/new", "application/json", (req, res) -> {
            Departments departments = gson.fromJson(req.body(), Departments.class);
            departmentsDao.add(departments);
            res.status(201);
            return gson.toJson(departments);
        });

        get("/news/:id", "application/json", (req, res) -> {
            int newsId = Integer.parseInt(req.params("id"));
            News   newsToFind = newsDao.findById(newsId);
            if (newsToFind == null){
                throw new ApiException(404, String.format("No news with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(newsToFind);
        });

        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = (ApiException) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


        after((req, res) ->{
            res.type("application/json");
        });
    }
}
