import com.google.gson.Gson;
import dao.Sql2oDepartmentsDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUsersDao;
import models.Departments;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
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

        post("/departments/new", "application/json", (req, res) -> {
            Departments departments = gson.fromJson(req.body(), Departments.class);
            departmentsDao.add(departments);
            res.status(201);
            res.type("application/json");
            return gson.toJson(departments);
        });
        get("/departments", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(departmentsDao.getAll());
        });

        get("/departments/:id", "application/json", (req, res) -> {
            res.type("application/json");
            int departmentId = Integer.parseInt(req.params("id"));
            res.type("application/json");
            return gson.toJson(departmentsDao.findById(departmentId));
        });
    }
}
