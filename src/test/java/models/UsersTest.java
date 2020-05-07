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
    public void getPositionReturnsCorrectAddress() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("IT Technician", testUsers.getPosition());
    }
    @Test
    public void getRoleReturnsCorrectAddress() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("repair", testUsers.getRole());

    }
    @Test
    public void getEmailReturnsCorrectAddress() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("rmogusu123@gmail.com", testUsers.getEmail());
    }
    public Users  setupUsers (){
        return new Users("Faith", "IT Technician", "repair", "rmogusu123@gmail.com","2345","IT") ;
    }
}