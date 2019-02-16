/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import java.util.ArrayList;
import model.TeamMember;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author davi
 */
@Ignore
public class TeamMemberDAOTest {
    
   TeamMember tm;
   TeamMemberDAO tDAO;
   int APPDB = 1;
   int JIRADB = 0;
    
    @Before
    public void setUp() {
        tDAO = new TeamMemberDAO();
        tm = new TeamMember();
        tDAO.insert("INSERT INTO \"TeamMembers\" VAlUES ('pedro', "
                + "'{\"@dev\",\"@test\",\"@eat\", \"@dev\"}', '{1,2,3,5}', '{12, 14, 32, 2}');");
   }
    
    @After
    public void tearDown() {
        tDAO.delete("DELETE FROM \"TeamMembers\" WHERE username = 'pedro';");
        tDAO = null;
        tm = null;
    }

    /**
     * Test of select method, of class TeamMemberDAO.
     */
    @Test
    public void testSelect() {
        System.out.println("select");
        String query = "SELECT * FROM \"TeamMembers\"";
        ArrayList<TeamMember> expResult = tDAO.selectApp(APPDB, query);
        assertTrue(!expResult.isEmpty());
    }
    
    @Test
    public void testSelectComparing() {
        System.out.println("select");
        String query = "SELECT * FROM \"TeamMembers\" WHERE username = 'usuario2';";
        ArrayList<TeamMember> expResult = tDAO.selectApp(APPDB, query);
        Assert.assertEquals("usuario2", expResult.get(0).getUsername());
    }

    /**
     * Test of update method, of class TeamMemberDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        String query = "UPDATE \"TeamMembers\" SET "
                + "username='pedro', "
                + "activities = '{\"@dev\", \"@test\", \"@eat\", \"@dev\"}', "
                + "myIssues = '{1,2,3,5,7,3}', state = '{12, 14}' "
                + "WHERE username = 'pedro';";
        boolean expResult = true;
        boolean result = tDAO.update(query);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of delete method, of class TeamMemberDAO.
     */
   
    public void testDelete() {
        System.out.println("Delete");
        String query = "DELETE FROM \"TeamMembers\" WHERE username='member';";
        TeamMemberDAO instance = new TeamMemberDAO();
        boolean expResult = true;
        boolean result = instance.delete(query);
        assertEquals(expResult, result);
    }

    /**
     * Test of insert method, of class TeamMemberDAO.
     */
    public void testInsert() {
        System.out.println("insert");
        String query = "INSERT INTO \"TeamMembers\" VAlUES ('joaoAlberto', '{\"@dev\",\"@test\",\"@eat\", \"@dev\"}', '{1,2,3,5}', '{12, 14, 32, 2}');";
        TeamMemberDAO instance = new TeamMemberDAO();
        boolean expResult = true;
        boolean result = instance.insert(query);
        assertEquals(expResult, result);
        String clean = "DELETE FROM \"TeamMembers\" WHERE username = 'joaoAlberto';";
        instance.delete(clean);
    }
    public void testInserSame(){
        System.out.println("insert");
        String query = "INSERT INTO \"TeamMembers\" VAlUES ('claudiodavi', '{\"@dev\",\"@test\",\"@eat\", \"@dev\"}', '{1,2,3,5}', '{12, 14, 32, 2}');";
        TeamMemberDAO instance = new TeamMemberDAO();
        boolean expResult = false;
        boolean result = instance.insert(query);
        assertEquals(expResult, result);
    }
    
}
