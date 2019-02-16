/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import java.util.ArrayList;
import model.Issue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author davi
 */
@Ignore
public class IssueDAOTest {

    public IssueDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        String populate = "INSERT INTO \"Projects\" (id, name) VALUES (1, 'name1');"
                + "INSERT INTO \"TeamMembers\" VAlUES ('claudiodavi', '{\"@dev\",\"@test\",\"@eat\", \"@dev\"}', '{1,2,3,5}', '{12, 14, 32, 2}');"
                + "INSERT INTO \"Issues\"(issuenum, description, reporter, assignee, project, summary) "
                + "VALUES "
                + "(1, '@dev @eat @idontknow atividades outras coisas', 'claudiodavi', 'claudiodavi', 1, 'title2' );\n"
                + "INSERT INTO \"Issues\"(issuenum, description, reporter, assignee, project, summary) "
                + "VALUES (2, '@dev @tet @idontknow atividades outras coisas', 'claudiodavi', 'claudiodavi', 1, 'title3' );\n"
                + "INSERT INTO \"Issues\"(issuenum, description, reporter, assignee, project, summary) "
                + "VALUES (3, '@dev @tet @design @idontknow', 'claudiodavi', 'claudiodavi', 1, 'title1' );";
         IssueDAO IDAO = new IssueDAO();
         IDAO.insert(populate);
    }

    @After
    public void tearDown() {
        String clean = "DELETE FROM \"Issues\" WHERE issuesnum=1;\n"
                + "DELETE FROM \"Issues\" WHERE issuesnum=2;\n"
                + "DELETE FROM \"Issues\" WHERE issuesnum=3;"
                + "DELETE FROM \"TeamMembers\" WHERE reporter = claudiodavi;"
                + "DELETE FROM \"Projects\" WHERE id = 1;";

           IssueDAO iDAO = new IssueDAO();
           iDAO.delete(clean);
    }

    /**
     * Test of select method, of class IssueDAO.
     */
    @Test
    public void testSelectFromAppDb() {
        System.out.println("select from application Database");
        int database = 1;

        String query = "SELECT * FROM \"Issues\";";
        IssueDAO instance = new IssueDAO();
        ArrayList<Issue> result = instance.select(database, query);

        assertFalse(result.isEmpty());
    }

    @Test
    public void testSelectFromJiraDb() {
        System.out.println("select from JIRA Database");
        int database = 0;

        String query = "SELECT \n"
                + "  jiraissue.issuenum, \n"
                + "  jiraissue.description, \n"
                + "  jiraissue.reporter, \n"
                + "  jiraissue.assignee, \n"
                + "  jiraissue.project, \n"
                + " jiraissue.summary \n"
                + "FROM \n"
                + "  public.jiraissue;";
        IssueDAO instance = new IssueDAO();
        ArrayList<Issue> result = instance.select(database, query);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testSelectComparing() {
        System.out.println("select");
        int database = 1;
        String summary = "title3";
        String query = "SELECT * FROM \"Issues\";";
        IssueDAO instance = new IssueDAO();
        ArrayList<Issue> result = instance.select(database, query);

        for (Issue i : result) {
            if (i.getSummary().equals(summary)) {
                assertTrue(true);
            } else {
                assertFalse(false);
            }
        }

    }

    /**
     * Test of update method, of class IssueDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        String query = "UPDATE \"Issues\" SET issuenum=1, description='any', "
                + "reporter='claudiodavi', assignee='', project=1, "
                + "summary='updated' WHERE issuenum = 1;";
        IssueDAO instance = new IssueDAO();
        boolean expResult = true;
        boolean result = instance.update(query);
        assertEquals(expResult, result);

    }

    /**
     * Test of delete method, of class IssueDAO.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        String query = "DELETE FROM \"Issues\" WHERE issuenum=1;";
        IssueDAO instance = new IssueDAO();
        boolean expResult = true;
        boolean result = instance.delete(query);
        assertEquals(expResult, result);
    }

    /**
     * Test of insert method, of class IssueDAO.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        String query = "INSERT INTO \"Issues\""
                + "(issuenum, description, reporter, assignee, project, summary) "
                + "VALUES (6, '@dev @eat @idontknow atividades outras coisas', "
                + "'claudiodavi', 'claudiodavi', 1, 'title2' );";
        IssueDAO instance = new IssueDAO();
        boolean expResult = true;
        boolean result = instance.insert(query);

        String clean = "DELETE FROM \"Issues\" WHERE issuenum = 6";

        instance.delete(clean);
        assertEquals(expResult, result);

    }

}
