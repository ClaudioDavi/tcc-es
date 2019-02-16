/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import java.util.ArrayList;
import model.Project;
import model.repository.ProjectRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author davi
 */
@Ignore
public class ProjectDAOTest {

    ProjectDAO prDAO;

   
    @Before
    public void setUp() {
        prDAO = new ProjectDAO();
        prDAO.insert("INSERT INTO \"Projects\" (id, name) VALUES "
                + "(3, 'teste')");
    }

    @After
    public void tearDown() {
        prDAO.delete("DELETE FROM \"Projects\" WHERE id = 3;");
        prDAO = null;
    }

    /**
     * Test of select method, of class ProjectDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        String query = "INSERT INTO \"Projects\" (id, name) VALUES "
                + "(1, 'projeto')";
        ProjectDAO instance = new ProjectDAO();
        boolean expResult = true;
        boolean result = instance.insert(query);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class ProjectDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        String query = "UPDATE \"Projects\" SET "
                + "id=1, name='updated' WHERE"
                + " id=1;";
        ProjectDAO instance = new ProjectDAO();
        boolean expResult = true;
        boolean result = instance.update(query);
        ProjectRepository pr = ProjectRepository.getInstance();
        pr.delete(1);
        assertEquals(expResult, result);

    }

    @Test
    public void testSelect() {
        System.out.println("select");
        int database = 1;
        String query = "SELECT * FROM \"Projects\";";
        ProjectDAO instance = new ProjectDAO();
        ArrayList result = instance.select(database, query);
        System.out.println("SIZE: " + result.size());
        assertTrue(!result.isEmpty());
    }

    public void testSelectJira() {
        System.out.println("SELECT project.id, "
                + "project.pname "
                + "FROM public.project;");
        int db = 0;
        String query = "";
        ProjectDAO instance = new ProjectDAO();
        ArrayList<Project> expResult = instance.select(db, query);
        assertTrue(!expResult.isEmpty());
    }

    /**
     * Test of delete method, of class ProjectDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String query = "DELETE FROM \"Projects\" WHERE id = 1;";
        ProjectDAO instance = new ProjectDAO();
        boolean expResult = true;
        boolean result = instance.delete(query);
        assertEquals(expResult, result);
    }

}
