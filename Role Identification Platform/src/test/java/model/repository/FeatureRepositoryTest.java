/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import model.Feature;
import model.Issue;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author davi
 */
@Ignore
public class FeatureRepositoryTest {

    Feature ft;
    List<Issue> ir;
    FeatureRepository fr;

    public FeatureRepositoryTest() {
    }

    @Before
    public void setUp() {
        ft = new Feature();
        fr = FeatureRepository.getInstance();
        ir = new ArrayList<>();

        Issue i1 = new Issue();
        i1.setDescription("description with nothing");
        i1.setProject(0);

        Issue i2 = new Issue();
        i2.setDescription("description \n@feature \n @dev");
        i2.setProject(0);

        Issue i3 = new Issue();
        i3.setDescription("another with features \n @log");
        i3.setProject(0);

        ir.add(i1);
        ir.add(i2);
        ir.add(i3);
    }

    @After
    public void tearDown() {
        ft = null;
        fr = null;
        ir = null;

    }

    @Test
    public void testLoadFromIssueRealCase() {
        System.out.println("Check list of issues for new features");
        List<String> feat = new ArrayList<>();

        feat.add("@dev");
        feat.add("@feature");
        feat.add("@log");

        Feature result = fr.loadFromIssues(ir);
        result.setId(1);

        Feature expected = new Feature();

        expected.setFeatures(feat);

        Assert.assertEquals(expected.getFeatures(), result.getFeatures());
    }

    @Test
    public void testLoadFromIssueRepeatingFeature() {
        System.out.println("Check list of Issues for new features and repeats features");
        List<String> feat = new ArrayList<>();
        Issue i = new Issue();
        i.setDescription("@dev\n again");

        ir.add(i);

        feat.add("@dev");
        feat.add("@feature");
        feat.add("@log");

        Feature result = fr.loadFromIssues(ir);
        result.setId(1);

        Feature expected = new Feature();

        expected.setFeatures(feat);

        Assert.assertEquals(expected.getFeatures(), result.getFeatures());
    }

    @Test
    public void testConvertFromArray() {
        List<String> strings = new ArrayList<>();

        strings.add("dev");
        strings.add("test");

        Assert.assertEquals("'{\"dev\",\"test\"}'", fr.convertFromArray(strings));
    }

    @Test
    public void testConvertFromArrayEmpty() {
        List<String> strings = new ArrayList<>();

        Assert.assertEquals("'{\"\"}'", fr.convertFromArray(strings));
    }
    @Test
    public void testSelectFromId(){
        Feature result = new Feature();
        result.setId(12);
        result.setFeatures(result.filterFeature(ir.get(0).getDescription()));
        fr.insert(result);
        
        ft = fr.selectById(12);
        
        Assert.assertEquals(result.getFeatures(), fr.selectById(12).getFeatures());
        
        
    }

}
