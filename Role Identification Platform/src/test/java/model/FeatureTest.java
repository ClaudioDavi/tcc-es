/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author davi
 */
@Ignore
public class FeatureTest {

    Feature ft;

    public FeatureTest() {
    }

    @Before
    public void setUp() {
        ft = new Feature();
    }

    @After
    public void tearDown() {
        ft = null;
    }

    /**
     * Test of filterFeature method, of class Feature.
     */
    @Test
    public void testFilterFeatureWOFeatures() {
        System.out.println("Test Filter Feature using description whithout features");
        String desc = "Anything could've be written here \n and even here";

        List<String> expected = new ArrayList<String>();
        List<String> result = new ArrayList<>();

        result = ft.filterFeature(desc);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testFilterFeatureTwofeature() {
        System.out.println("Test Feature filter with 2 features");
        String desc = "@dev \n@test";

        List<String> expected = new ArrayList<String>();
        List<String> result = new ArrayList<>();
        expected.add("@dev");
        expected.add("@test");

        result = ft.filterFeature(desc);

        Assert.assertEquals(expected, result);

    }

    @Test
    public void testFilterFeatureWFeatureAndText() {
        System.out.println("Test Feature filter with Features and text");
        String desc = "text text \n@lol \n text text text \n @tcc";

        List<String> expected = new ArrayList<String>();
        List<String> result = new ArrayList<>();
        expected.add("@lol");
        expected.add("@tcc");

        result = ft.filterFeature(desc);

        Assert.assertEquals(expected, result);

    }

    @Test
    public void testFilterFeatureEmpty() {
        System.out.println("Test adding empty string");
        String desc = "";
        List<String> expected = new ArrayList<String>();
        List<String> result = new ArrayList<>();

        result = ft.filterFeature(desc);
        Assert.assertEquals(expected, result);

    }

    @Test
    public void testSetFeatureFormat() {
        System.out.println("Test the format of the features going in");

        List<String> features = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<String> expected = new ArrayList<>();

        features.add("@cry");
        features.add("@test");
        expected.add("@cry");
        expected.add("@test");

        ft.setFeatures(features);
        result = ft.getFeatures();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSetFeatureFormatElementBroken() {
        System.out.println("Test the format of the features w/ broken element ");

        List<String> features = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<String> expected = new ArrayList<>();

        features.add("@not");
        features.add("@here");
        features.add("test");
        features.add("@dev");

        ft.setFeatures(features);
        result = ft.getFeatures();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSetFeatureFormatNoValidFormat() {
        System.out.println("Test the format of the features not valid");

        List<String> features = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<String> expected = new ArrayList<>();

        features.add("ot");
        features.add("here");

        ft.setFeatures(features);
        result = ft.getFeatures();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testSetFeatureFormatWithColon() {
        System.out.println("Test the format of the with colon : ");

        List<String> features = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<String> expected = new ArrayList<>();

        features.add("@dev:22");
        features.add("@test:11");

        ft.setFeatures(features);
        result = ft.getFeatures();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testTwoOfTheSameFeatureSameDescription() {
        System.out.println("Insert twice the same feature inside same description");
        List<String> features = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        List<String> result = new ArrayList<>();
        String desc = "Normal String \n@primeira \n other \n "
                + "@segunda \n coisas \n @primeira";
        List<String> descFiltered = ft.filterFeature(desc);

        expected.add("@primeira");
        expected.add("@segunda");

        ft.getFeatures().addAll(descFiltered);
        result = ft.getFeatures();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testTwoOfTheSameFeatureDiffDescription() {
        System.out.println("Insert twice the same feature inside Different Descs");
        List<String> features = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        List<String> result = new ArrayList<>();
        String desc = "Normal String \n@primeira \n other \n "
                + "@segunda \n coisas \n @primeira";
        String otherDesc = "Another description with the \n@primeira \n feature and"
                + "\n @new \n feature to be inserted";

        expected.add("@new");
        expected.add("@primeira");
        expected.add("@segunda");

        ft.getFeatures().addAll(ft.filterFeature(desc));
        ft.getFeatures().addAll(ft.filterFeature(otherDesc));

        result = ft.getFeatures();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testDescriptionContainingAlready() {
        System.out.println("Insert twice the same feature inside Different Descs"
                + " but feature vector has already some features");
        List<String> features = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        List<String> result = new ArrayList<>();
        String desc = "Normal String \n@primeira \n other \n "
                + "@segunda \n coisas \n @primeira";
        String otherDesc = "Another description with the \n@primeira \n feature and"
                + "\n @new \n feature to be inserted";

        expected.add("@already");
        expected.add("@here");
        expected.add("@new");
        expected.add("@primeira");
        expected.add("@segunda");

        ft.getFeatures().add("@already");
        ft.getFeatures().add("@here");
        ft.getFeatures().addAll(ft.filterFeature(desc));
        ft.getFeatures().addAll(ft.filterFeature(otherDesc));
        result = ft.getFeatures();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testDescriptionContainingAlreadyOneFeatureOnDesc() {
        System.out.println("Try inserting a feature already on list");
        List<String> features = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        List<String> result = new ArrayList<>();
        String desc = "Normal String \n@primeira \n other \n "
                + "@segunda \n coisas \n @primeira \n@already";
        expected.add("@already");
        expected.add("@here");
        expected.add("@primeira");
        expected.add("@segunda");

        ft.getFeatures().add("@already");
        ft.getFeatures().add("@here");

        ft.getFeatures().addAll(ft.filterFeature(desc));

        result = ft.getFeatures();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testFeatureDescriptionWithColon() {
        System.out.println("The colon on feature description should be eliminated");
        List<String> features = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        List<String> result = new ArrayList<>();
        String desc = "Normal String \n@primeira:12 \n other \n "
                + "@segunda:23 \n coisas \n @primeira";
        String otherDesc = "Another description with the \n@primeira \n feature and"
                + "\n @new:11 \n feature to be inserted";

        expected.add("@new");
        expected.add("@primeira");
        expected.add("@segunda");

        ft.getFeatures().addAll(ft.filterFeature(desc));
        ft.getFeatures().addAll(ft.filterFeature(otherDesc));

        result = ft.getFeatures();

        Assert.assertEquals(expected, result);
    }

    
    public void testDescriptionCfeatureRegionMatching() {
        System.out.println("Insert twice the same feature inside Different Descs"
                + " they are the same feature but written differently");
        List<String> features = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        List<String> result = new ArrayList<>();
        String desc = "Normal String \n@primeira \n other \n "
                + "@segunda \n coisas \n @primeira";
        String otherDesc = "Another description with the \n@prime \n feature and"
                + "\n @coco \n feature to be inserted";

        expected.add("@already");
        expected.add("@coco");
        expected.add("@here");
        expected.add("@primeira");
        expected.add("@segunda");

        ft.getFeatures().add("@already");
        ft.getFeatures().add("@here");
        ft.getFeatures().addAll(ft.filterFeature(desc));
        ft.getFeatures().addAll(ft.filterFeature(otherDesc));

        result = ft.getFeatures();

        System.out.println(result);
        Assert.assertEquals(expected, result);
    }

}
