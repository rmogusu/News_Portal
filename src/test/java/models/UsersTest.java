package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void getNameReturnsCorrectName() throws Exception {
        Users  testUsers = setupUsers();
        assertEquals("Faith", testUsers.getName());
    }

    @Test
    public void getPositionReturnsCorrectPosition() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("IT Technician", testUsers.getPosition());
    }
    @Test
    public void getRoleReturnsCorrectRole() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("repair", testUsers.getRole());

    }
    @Test
    public void getEmailReturnsCorrectEmail() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("rmogusu123@gmail.com", testUsers.getEmail());
    }
    @Test
    public void getBadgeNoReturnsCorrectBadgeNo() throws Exception {
        Users testUsers = setupUsers();
        assertEquals(2345, testUsers.getBadgeNo());
    }
    @Test
    public void getDepartmentReturnsCorrectDepartment() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("IT", testUsers.getDepartment());
    }
    @Test
    public void setNameSetsCorrectName() throws Exception {
        Users  testUsers = setupUsers();
        testUsers.setName("Rose");
        assertNotEquals("Faith",testUsers.getName());
    }
    @Test
    public void setPositionSetsCorrectPosition() throws Exception {
        Users  testUsers = setupUsers();
        testUsers.setPosition("Accountant") ;
        assertNotEquals("IT Technician",testUsers.getPosition());
    }
    @Test
    public void setRoleSetsCorrectRole() throws Exception {
        Users  testUsers = setupUsers();
        testUsers.setRole("Financial operations") ;
        assertNotEquals("repair",testUsers.getRole());
    }
    @Test
    public void setEmailSetsCorrectEmail() throws Exception {
        Users  testUsers = setupUsers();
        testUsers.setEmail("mogusurose@gmail.com") ;
        assertNotEquals("rmogusu123@gmail.com",testUsers.getEmail());
    }
    @Test
    public void setBadgeNoSetsCorrectBadgeNo() throws Exception {
        Users  testUsers = setupUsers();
        testUsers.setBadgeNo(5432) ;
        assertNotEquals(2345,testUsers.getBadgeNo());
    }
    @Test
    public void setDepartmentSetsCorrectDepartment() throws Exception {
        Users  testUsers = setupUsers();
        testUsers.setDepartment("Accountant") ;
        assertNotEquals("IT",testUsers.getDepartment() );
    }
    @Test
    public void getDepartmentId() {
        Users  testUsers = setupUsers();
        assertEquals(1, testUsers.getDepartmentId());
    }

    @Test
    public void setDepartmentId() {
        Users  testUsers = setupUsers();
        testUsers.setDepartmentId(10);
        assertNotEquals(1, testUsers.getDepartmentId());
    }

    @Test
    public void setId() {
        Users  testUsers = setupUsers();
        testUsers.setId(5);
        assertEquals(5, testUsers .getId());
    }
    public Users  setupUsers (){
        return new Users("Faith", "IT Technician", "repair", "rmogusu123@gmail.com",2345,"IT",1) ;
    }
}