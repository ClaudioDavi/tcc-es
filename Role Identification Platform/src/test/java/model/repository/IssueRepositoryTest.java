/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository;

import java.util.ArrayList;
import model.Issue;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author davi
 */
@Ignore
public class IssueRepositoryTest {
    
    public IssueRepositoryTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class IssueRepository.
     */
    public void testGetInstance() {
        System.out.println("getInstance");
        IssueRepository expResult = null;
        IssueRepository result = IssueRepository.getInstance();
        assertEquals(expResult, result);
    }

    /**
     * Test of selectFromJira method, of class IssueRepository.
     */
    @Test
    public void testSelectFromJira() {
        System.out.println("selectFromJira All Issues");
        boolean expResult = false;
        IssueRepository iRep = IssueRepository.getInstance();
        
        ArrayList<Issue> issues = iRep.selectFromJira();
       
        boolean result = !issues.get(0).equals(issues.get(1));
        
        assertEquals(expResult, result);
    }

    /**
     * Test of inject method, of class IssueRepository.
     */
    public void testInject() {
        System.out.println("inject");
        IssueRepository instance = new IssueRepository();
        boolean expResult = false;
       // boolean result = instance.inject();
    //    assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of selectFromAppDb method, of class IssueRepository.
     */
    public void testSelectFromAppDb() {
        System.out.println("selectFromAppDb");
        IssueRepository instance = new IssueRepository();
        ArrayList<Issue> expResult = null;
        ArrayList<Issue> result = instance.selectFromAppDb();
        assertEquals(expResult, result);
    }

    /**
     * Test of insert method, of class IssueRepository.
     */
    public void testInsert() {
        System.out.println("insert");
        Issue i = null;
        IssueRepository instance = new IssueRepository();
        boolean expResult = false;
        boolean result = instance.insert(i);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class IssueRepository.
     */
    public void testUpdate() {
        System.out.println("update");
        Issue i = null;
        IssueRepository instance = new IssueRepository();
        boolean expResult = false;
        boolean result = instance.update(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of delete method, of class IssueRepository.
     */
    
    public void testDelete() {
        System.out.println("delete");
        int issuenum = 0;
        IssueRepository instance = new IssueRepository();
        boolean expResult = false;
        boolean result = instance.delete(issuenum);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
