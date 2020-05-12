package dao;

import models.Departments;
import models.News;
import models.Users;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentsDaoTest {

    private static  Connection conn;
    private static Sql2oDepartmentsDao departmentsDao;
    private  static Sql2oNewsDao newsDao;
    private static  Sql2oUsersDao usersDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/news_portal_test";
        Sql2o sql2o = new Sql2o(connectionString, "rose", "wambua");
        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
        conn = sql2o.open();
    }
    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentsDao.clearAll();
        newsDao.clearAll();
        usersDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

//    @Test
//    public void addingDepartmentsSetsId() throws Exception {
//        Departments  testDepartments = setupDepartments();
//        assertNotEquals(0, testDepartments.getId());
//    }

//    @Test
//    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
//        Departments  testDepartments = setupDepartments();
//        Departments  otherDepartments = setupDepartments();
//        assertEquals(2, departmentsDao.getAll().size());
//    }


    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentsDao.getAll().size());
    }
    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception {
        Departments  testDepartments = setupDepartments();
        Departments  otherDepartments = setupDepartments();
        assertNotEquals(testDepartments , departmentsDao.findById(testDepartments.getId()));
    }
//
//    @Test
//    public void updateCorrectlyUpdatesAllFields() throws Exception {
//        Departments  testDepartments = setupDepartments();
//        departmentsDao.update(testDepartments.getId(), "a", "b", 400);
//        Departments  foundDepartments = departmentsDao.findById(testDepartments.getId());
//        assertEquals("a", foundDepartments.getName());
//        assertEquals("b", foundDepartments.getDescription());
//        assertEquals(400, foundDepartments.getTotalNumber());
//
//    }
//    @Test
//    public void deleteByIdDeletesCorrectDepartment() throws Exception {
//        Departments  testDepartments = setupDepartments();
//        Departments  otherDepartments = setupDepartments();
//        departmentsDao.deleteById(testDepartments.getId());
//        assertEquals(1, departmentsDao.getAll().size());
//    }

    @Test
    public void clearAll() throws Exception {
        Departments  testDepartments = setupDepartments();
        Departments  otherDepartments = setupDepartments();
        departmentsDao.clearAll();
        assertEquals(0, departmentsDao.getAll().size());
    }
//    @Test
//    public void DepartmentsReturnsNewsCorrectly() throws Exception {
//        News testNews = setupNews();
//        newsDao.add(testNews);
//        News otherNews = setupNews();
//        newsDao.add(otherNews);
//        Departments  testDepartments = setupDepartments();
//        departmentsDao.add(testDepartments);
//        departmentsDao.addDepartmentsToNews(testDepartments ,testNews);
//        departmentsDao.addDepartmentsToNews(testDepartments ,otherNews);
//        News [] news = {testNews , otherNews};
//        assertEquals(Arrays.asList(news), departmentsDao.getAllNewsByDepartments(testDepartments.getId()) );
//    }
    @Test
    public void deletingNewsAlsoUpdatesJoinTable() throws Exception {
        Departments  testDepartments = setupDepartments();
        departmentsDao.add(testDepartments);
        News testNews = setupNews();
        newsDao.add(testNews);
        News anotherNews = setupNews();
        newsDao.add(anotherNews);
        newsDao.addNewsToDepartments(testNews ,testDepartments);
        newsDao.addNewsToDepartments(anotherNews ,testDepartments);
        newsDao.deleteById(testNews.getId());
        newsDao.deleteById(anotherNews.getId());
        assertEquals(0, newsDao.getAllDepartmentsByNews(testNews.getId()) .size());
    }


    //helpers

    public Departments  setupDepartments (){
        Departments  departments = new Departments("IT", "Programming", 500) ;
        departmentsDao.add(departments);
        return departments ;
    }
    public News setupNews(){
        return new News("Sewage","corona") ;
    }

}