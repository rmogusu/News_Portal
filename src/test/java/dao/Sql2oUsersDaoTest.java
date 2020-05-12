package dao;

import models.Departments;
import models.Users;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUsersDaoTest {
    private static  Connection conn;
    private static  Sql2oUsersDao usersDao;
    private static Sql2oDepartmentsDao departmentsDao;
    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, "rose", "wambua");
        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
        conn = sql2o.open();
    }
    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentsDao.clearAll();
        usersDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }


    @Test
    public void addingUsersSetsId() throws Exception {
        Users  testUsers = setupUsers();
        usersDao.add(testUsers);
        assertNotEquals(testUsers , testUsers.getId());
    }
//    @Test
//    public void getAll() throws Exception {
//        Users  Users = setupUsers();
//        Users  Users2 = setupUsers();
//        assertEquals(2, usersDao.getAll().size());
//    }
//    @Test
//    public void getAllUsersByDepartments() throws Exception {
//        Departments testDepartments = setupDepartments();
//        Departments  otherDepartments = setupDepartments() ;
//        Users  Users = setupUsersForDepartments(testDepartments);
//        Users  Users2 = setupUsersForDepartments(testDepartments);
//        Users  usersForOtherDepartments = setupUsersForDepartments(otherDepartments);
//        assertEquals(2, usersDao.getAllUsersByDepartments(testDepartments.getId()).size());
//    }
//    @Test
//    public void findByIdReturnsCorrectUsers() throws Exception {
//        Users  testUsers = setupUsers();
//        Users  otherUsers = setupUsers();
//        assertEquals(testUsers , usersDao.findById(testUsers.getId()));
//    }
//    @Test
//    public void deleteById() throws Exception {
//        Users  testUsers = setupUsers();
//        Users  otherUsers = setupUsers();
//        assertEquals(2, usersDao.getAll().size());
//        usersDao.deleteById(testUsers.getId());
//        assertEquals(1, usersDao.getAll().size());
//    }
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