package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void getNameReturnsCorrectName() throws Exception {
        Departments  testDepartments = setupDepartments();
        assertEquals("IT", testDepartments.getName());
    }
    @Test
    public void getDescriptionReturnsCorrectDescription() throws Exception {
        Departments  testDepartments = setupDepartments();
        assertEquals("Technician support", testDepartments.getDescription());
    }
    @Test
    public void getTotalNumberReturnsCorrectTotalNumber() throws Exception {
        Departments  testDepartments = setupDepartments();
        assertEquals(250, testDepartments.getTotalNumber());
    }
    @Test
    public void setNameSetsCorrectName() throws Exception {
        Departments  testDepartments = setupDepartments();
        testDepartments.setName("Accounts") ;
        assertNotEquals("IT",testDepartments.getName() );
    }
    @Test
    public void setDescriptionSetsCorrectDescription() throws Exception {
        Departments  testDepartments = setupDepartments();
        testDepartments.setDescription("Financial operations") ;
        assertNotEquals("Technician support",testDepartments.getDescription());
    }
    @Test
    public void setTotalNumberSetsCorrectTotalNumber() throws Exception {
        Departments  testDepartments = setupDepartments();
        testDepartments.setTotalNumber(500) ;
        assertNotEquals(250,testDepartments.getTotalNumber());
    }
    public Departments  setupDepartments (){
        return new Departments("IT", "Technician support", 250) ;
    }
}