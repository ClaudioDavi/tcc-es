/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistance;

import java.util.ArrayList;
import java.util.List;
import model.Feature;
import model.repository.FeatureRepository;
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
public class FeatureDAOTest {

    FeatureDAO ft;

    @Before
    public void setUp() {

        ft = new FeatureDAO();

        String popula = "INSERT INTO \"Features\"(id, name) "
                + "VALUES (1, '{\"@dev\",\"@Design\"}');\n"
                + " INSERT INTO \"Features\"(id, name) "
                + "VALUES (2, '{\"@dev\", \"@Test\"}');\n"
                + " INSERT INTO \"Features\"(id, name) "
                + "VALUES (3, '{\"@dev\", \"@Test\" , \"@design\", \"@eat\"}');";

        ft.insert(popula);
    }

    @After
    public void tearDown() {
        String clean = " DELETE FROM \"Features\" WHERE id=1;\n"
                + " DELETE FROM \"Features\" WHERE id=2;\n"
                + " DELETE FROM \"Features\" WHERE id=3;";
        ft.delete(clean);
        ft = null;

    }

    /**
     * Test of select method, of class FeatureDAO.
     */
    @Test
    public void testSelect() {

        System.out.println("select");
        int database = 1;
        String query = "SELECT * FROM \"Features\";";

        List<Feature> featureList = ft.select(database, query);

        assertFalse(featureList.isEmpty());
    }
    @Test
    public void testSelectConfirm() {

        System.out.println("select confirming data from object");
        int database = 1;
        String query = "SELECT * FROM \"Features\";";
        FeatureDAO instance = new FeatureDAO();

        ArrayList<Feature> featureList = instance.select(database, query);
        List<String> feats = new ArrayList<>();
        feats.add("@dev");
        feats.add("@Test");

        Feature ft = new Feature();
        ft.setId(2);
        ft.setFeatures(feats);

        if (ft.equals(featureList.get(1))) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }

    /**
     * Test of update method, of class FeatureDAO.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        String query = "UPDATE \"Features\" SET id=2, name='{\"@dev\", \"@nothing\", \"@coisas\"}'"
                + "WHERE id=2";
        FeatureDAO instance = new FeatureDAO();
        boolean expResult = true;
        boolean result = instance.update(query);
        assertEquals(expResult, result);
    }

    /**
     * Test of delete method, of class FeatureDAO.
     */
    public void testDelete() {
        System.out.println("delete");
        String query = "DELETE FROM \"Features\" where id = 1";
        FeatureDAO instance = new FeatureDAO();
        boolean expResult = true;
        boolean result = instance.delete(query);
        assertEquals(expResult, result);

    }

    /**
     * Test of insert method, of class FeatureDAO.
     */
    public void testInsert() {
        System.out.println("insert features");
        String query = "INSERT INTO \"Features\" (id, name) "
                + "VALUES "
                + "(4, '{\"@dev\",\"@test\",\"@eat\"}');";
        FeatureDAO instance = new FeatureDAO();
        boolean expResult = true;
        boolean result = instance.insert(query);
        String clean = "DELETE FROM \"Features\" WHERE id = 4;";
        instance.delete(clean);
        assertEquals(expResult, result);
    }

    public void testInsertEmptyFeatureList() {
        System.out.println("Insert Features with empty");
        int id = 7;
        String query = "INSERT INTO \"Features\" "
                + "VALUES "
                + "(" + id + ", '{}');";
        FeatureDAO instance = new FeatureDAO();
        boolean expResult = true;
        boolean result = instance.insert(query);
        String clean = "DELETE FROM \"Features\" WHERE id =" + id + ";";
        instance.delete(clean);
        assertEquals(expResult, result);
    }

    public void testInsertArray() {
        int id = 5;
        ArrayList<String> features = new ArrayList<>();
        features.add("davi");
        features.add("test");
        features.add("outro");

        FeatureRepository fr = new FeatureRepository();
        System.out.println("Insert Features with ArrayList");

        String query = "INSERT INTO \"Features\" (id, name)"
                + " VALUES  (" + id + "," + fr.convertFromArray(features) + ");";

        FeatureDAO instance = new FeatureDAO();
        boolean expResult = true;
        boolean result = instance.insert(query);

        fr.delete(id);
        assertEquals(expResult, result);
    }

}
