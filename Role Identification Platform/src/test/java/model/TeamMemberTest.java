/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author davi
 */
public class TeamMemberTest {

    TeamMember tm;
    Issue i;

    public TeamMemberTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        tm = new TeamMember();
        i = new Issue();
    }

    @After
    public void tearDown() {
        tm = null;
        i = null;
    }

    /**
     * Test of getUsername method, of class TeamMember.
     */
    @Test
    public void testGetUsernameWrong() {
        System.out.println("username wrong");
        String username = "claudio";

        tm.setUsername("claudiodavi");

        Assert.assertFalse(username.equals(tm.getUsername()));

    }

    @Test
    public void testGetUsernameRight() {
        System.out.println("Username right");
        String username = "claudiodavi";

        tm.setUsername("claudiodavi");

        Assert.assertTrue(username.equals(tm.getUsername()));

    }

    @Test
    public void testFilterIssueDescriptionNulll() {
        System.out.println("Issue without description");
        String desc = "";

        String[] result = tm.filterIssueDescription(desc);

        Assert.assertArrayEquals(result, new String[]{""});
    }

    @Test
    public void testFilterIssueDescriptionWhitoutTags() {
        System.out.println("Test without the @ tag");
        String desc = "Long string full of things \n and more things \n and even more";

        String[] result = tm.filterIssueDescription(desc);

        Assert.assertArrayEquals(new String[]{"", "", ""}, result);
    }

    @Test
    public void testFilterIssueDescriptionWithTags() {
        System.out.println("Test with @ tag");
        String desc = "String full of useless stuff \n @dev:22 \n @test:33 \n more useless stuff";
        String[] result = tm.filterIssueDescription(desc);

        Assert.assertArrayEquals(new String[]{"", "@dev:22", "@test:33", ""}, result);
    }

    @Test
    public void testSetStateRealCase() {
        System.out.println("Test with activities and features not matching positions but with same elements");
        List<String> features = new ArrayList<>();
        features.add("@dev");
        features.add("@eat");
        features.add("@test");
        List<String> activity = new ArrayList<>();
        activity.add("@dev:22");
        activity.add("@test:33");

        List<Integer> expected = new ArrayList<>();
        expected.add(22);
        expected.add(0);
        expected.add(33);

        tm.setState(features, activity);

        List<Integer> actuals = tm.getState();

        Assert.assertEquals(expected, actuals);

    }

    @Test
    public void testSetStateSumValues() {
        System.out.println("Test summing values on state");
        List<String> features = new ArrayList<>();
        features.add("@dev");
        features.add("@eat");
        features.add("@test");
        features.add("@cry");
        java.util.Collections.sort(features);
        List<String> activity = new ArrayList<>();
        activity.add("@dev: 22");
        activity.add("@test:33");
        activity.add("@Dev:12");
        activity.add("@cry:10");

        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(34);
        expected.add(0);
        expected.add(33);

        tm.setState(features, activity);

        List<Integer> actuals = tm.getState();

        Assert.assertEquals(expected, actuals);
    }

    @Test
    public void testSetStateEmptyFeatures() {
        System.out.println("Feature vector is empty");
        List<String> features = new ArrayList<>();
        List<String> activity = new ArrayList<>();
        activity.add("@dev:22");
        activity.add("@test:33");

        List<Integer> expected = new ArrayList<>();
        tm.setState(features, activity);
        List<Integer> actuals = tm.getState();
        Assert.assertEquals(expected, actuals);

    }

    @Test
    public void testFilterDescriptionActivityWhitoutState() {
        System.out.println("Testing a stateless aactivity");
        String desc = "@dev \n @text \n lots of text here";
        String[] result = tm.filterIssueDescription(desc);
        
        Assert.assertArrayEquals(new String[]{"@dev", "@text", ""}, result);
    }
    
}
