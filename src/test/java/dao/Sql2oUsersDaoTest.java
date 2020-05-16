package dao;

import models.Departments;
import models.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUsersDaoTest {
    private Connection conn;
    private Sql2oUsersDao usersDao;
    private Sql2oDepartmentsDao departmentsDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://ec2-34-230-149-169.compute-1.amazonaws.com:5432/dcakasea1e7mhc";
        Sql2o sql2o = new Sql2o(connectionString,"ndtkevnbixspxc", "a3d10fbb977b51b6edf5fdf9e4c7b8760c07d71c989038ac3bd6a9cf03ae994e");
        //String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        //Sql2o sql2o = new Sql2o(connectionString, "rose", "wambua");
        usersDao = new Sql2oUsersDao(sql2o);
        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    @Test
    public void addingUsersSetsId() throws Exception {
        Users  testUsers = setupUsers();
        assertEquals(1, testUsers.getId());
    }
    @Test
    public void getAll() throws Exception {
        Users  Users = setupUsers();
        Users  Users2 = setupUsers();
        assertEquals(2, usersDao.getAll().size());
    }
    @Test
    public void getAllUsersByDepartments() throws Exception {
        Departments testDepartments = setupDepartments();
        Departments  otherDepartments = setupDepartments() ;
        Users  Users = setupUsersForDepartments(testDepartments);
        Users  Users2 = setupUsersForDepartments(testDepartments);
        Users  usersForOtherDepartments = setupUsersForDepartments(otherDepartments);
        assertEquals(2, usersDao.getAllUsersByDepartments(testDepartments.getId()).size());
    }
    @Test
    public void findByIdReturnsCorrectUsers() throws Exception {
        Users  testUsers = setupUsers();
        Users  otherUsers = setupUsers();
        assertEquals(testUsers , usersDao.findById(testUsers.getId()));
    }
    @Test
    public void deleteById() throws Exception {
        Users  testUsers = setupUsers();
        Users  otherUsers = setupUsers();
        assertEquals(2, usersDao.getAll().size());
        usersDao.deleteById(testUsers.getId());
        assertEquals(1, usersDao.getAll().size());
    }
    @Test
    public void clearAll() throws Exception {
        Users  testUsers = setupUsers();
        Users  otherUsers = setupUsers();
        usersDao.clearAll();
        assertEquals(0, usersDao.getAll().size());
    }

    //helpers

    public Users  setupUsers() {
        Users users = new Users("Rose", "Senior", "Maintenance", "rmogusu123@gmail.com",234,"IT",1) ;
        usersDao.add(users);
        return users ;
    }

    public Users setupUsersForDepartments(Departments  departments) {
        Users  users = new Users("Rose", "Senior","Maintenance", "rmogusu123@gmail.com",234,"IT", departments .getId()) ;
        usersDao.add(users);
        return users;
    }

    public Departments  setupDepartments() {
        Departments  departments = new Departments("IT", "Programming", 500) ;
        departmentsDao.add(departments);
        return departments;
    }
}