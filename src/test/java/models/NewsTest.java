package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void getGeneral() {
        News testNews = setupNews();
        assertEquals("Economy", testNews.getGeneral());
    }
    @Test
    public void getDepartment() {
        News testNews = setupNews();
        assertEquals("Rainfall", testNews.getDepartment());
    }

    @Test
    public void setGeneral() {
        News testNews = setupNews();
        testNews.setGeneral("water") ;
        assertNotEquals("Economy", testNews.getGeneral());
    }
    @Test
    public void setDepartments() {
        News testNews = setupNews();
        testNews.setDepartment("Sewage"); ;
        assertNotEquals("Rainfall", testNews.getDepartment());
    }
    @Test
    public void setId() {
        News testNews = setupNews();
        testNews.setId(5);
        assertEquals(5, testNews.getId());
    }

    // helper
    public News  setupNews(){
        return new News("Economy","Rainfall");
    }
}