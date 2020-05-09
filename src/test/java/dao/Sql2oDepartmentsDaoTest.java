package dao;

import models.Departments;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDepartmentsDaoTest {
    private Connection conn;
    private Sql2oDepartmentsDao departmentsDao;
    private Sql2oNewsDao newsDao;
    private Sql2oUsersDao usersDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "rose", "wambua");
        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    @Test
    public void addingNewsSetsId() throws Exception {
        Departments  testDepartments = setupDepartments();
        assertNotEquals(0, testDepartments.getId());
    }

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
        Departments  testDepartments = setupDepartments();
        assertEquals(1, departmentsDao.getAll().size());
    }

    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentsDao.getAll().size());
    }
    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception {
        Departments  testDepartments = setupDepartments();
        Departments  otherDepartments = setupDepartments();
        assertEquals(testDepartments , departmentsDao.findById(testDepartments.getId()));
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        Departments  testDepartments = setupDepartments();
        departmentsDao.update(testDepartments.getId(), "a", "b", 400);
        Departments  foundDepartments = departmentsDao.findById(testDepartments.getId());
        assertEquals("a", foundDepartments.getName());
        assertEquals("b", foundDepartments.getDescription());
        assertEquals(400, foundDepartments.getTotalNumber());

    }
    @Test
    public void deleteByIdDeletesCorrectDepartment() throws Exception {
        Departments  testDepartments = setupDepartments();
        Departments  otherDepartments = setupDepartments();
        departmentsDao.deleteById(testDepartments.getId());
        assertEquals(1, departmentsDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Departments  testDepartments = setupDepartments();
        Departments  otherDepartments = setupDepartments();
        departmentsDao.clearAll();
        assertEquals(0, departmentsDao.getAll().size());
    }

    //helpers

    public Departments  setupDepartments (){
        Departments  departments = new Departments("IT", "Programming", 500) ;
        departmentsDao.add(departments);
        return departments ;
    }

}