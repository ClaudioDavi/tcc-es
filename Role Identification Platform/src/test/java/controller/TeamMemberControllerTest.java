/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import model.Issue;
import model.TeamMember;
import model.repository.TeamMemberRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author davi
 */
@Ignore
public class TeamMemberControllerTest {

    TeamMemberController tmc;
    TeamMember tm;
    TeamMember tmSecond;
    TeamMember tmThird;
    TeamMemberRepository tr;
    Issue firstIssue;
    Issue secondIssue;
    Issue thirdIssue;
    List<String> features;

    public TeamMemberControllerTest() {
    }

    @Before
    public void setUp() {

        tmc = new TeamMemberController();
         String issueOwner = "username";
        tm = new TeamMember();
        tmSecond = new TeamMember();
        tmThird = new TeamMember();
        
        tr = TeamMemberRepository.getInstance();

        tm.setUsername(issueOwner);
        tr.insert(tm);

        firstIssue = new Issue();
        secondIssue = new Issue();
        thirdIssue = new Issue();
        features = new ArrayList<>();

        String firstDescription = "Thing thing \n @dev:10 \n @test:10 \n thing another";
        String secondDescription = "@dev:10 \n @eat:4 \n coiiiisas";
        String thirdDescription = "@cry \n @update \n @git";

        features.add("@dev");
        features.add("@test");
        features.add("@eat");
        features.add("@cry");
        features.add("@update");
        features.add("@git");
        java.util.Collections.sort(features);
        
        firstIssue.setDescription(firstDescription);
        secondIssue.setDescription(secondDescription);
        thirdIssue.setDescription(thirdDescription);

        firstIssue.setAssignee(issueOwner);
        secondIssue.setAssignee(issueOwner);
        thirdIssue.setAssignee(issueOwner);

        firstIssue.setIssueNum(1);
        secondIssue.setIssueNum(2);
        thirdIssue.setIssueNum(3);
    }

    @After
    public void tearDown() {
        tmc = null;
        tm = null;
        tr.delete("username");
        tr = null;
        firstIssue = null;
        secondIssue = null;
        thirdIssue = null;
        features = null;
    }
    
    public void testActivityList() {
        System.out.println("Activity List based on issues");

        List<Issue> iList = new ArrayList<>();
        iList.add(firstIssue);
        iList.add(secondIssue);
        iList.add(thirdIssue);
        
        List<String> expected = new ArrayList<>();
        expected.add("@dev:10");
        expected.add("@test:10");
        expected.add("@dev:10");
        expected.add("@eat:4");
       
        
    //    List<String> result = tmc.setActivitiesFromIssues(iList, tm);
        
    //    Assert.assertEquals(expected, result);
    }
    
    
    public void testIssueToMe(){
        List<Issue> iList = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        
        expected.add(1);
        expected.add(2);
        expected.add(3);
        
        iList.add(firstIssue);
        iList.add(secondIssue);
        iList.add(thirdIssue);
        
    //    result = tmc.setIssuesToMeFromIssues(iList, tm);
        Assert.assertEquals(expected, result);
    }
    
    public void testIssueToMeRepeating(){
        System.out.println("Repeats the same Issue to me");
        List<Issue> iList = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        
        expected.add(1);
        expected.add(2);
        expected.add(3);
        
        iList.add(firstIssue);
        iList.add(secondIssue);
        iList.add(thirdIssue);
        iList.add(firstIssue);
        
    //    result = tmc.setIssuesToMeFromIssues(iList, tm);
        Assert.assertEquals(expected, result);
    }
    
    public void testIssueToMeEmpty(){
        System.out.println("Empty Issues");
        List<Issue> iList = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
                
     //   result = tmc.setIssuesToMeFromIssues(iList, tm);
        Assert.assertEquals(expected, result);
    }
    
    
    public void testSetActivitiesFromIssues(){
        System.out.println("Inserting activities from new issues");
        List<String> expected = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<Issue> iList = new ArrayList<>();
        
        
        iList.add(firstIssue);
        iList.add(secondIssue);
        
        
        expected.add("@dev:10");
        expected.add("@test:10");
        expected.add("@dev:10");
        expected.add("@eat:4");
        
     //   result = tmc.setActivitiesFromIssues(iList, tm);
        
        Assert.assertEquals(expected, result);
        
    }
    
    
    public void testSetActivitiesFromIssuesWrongFormat(){
        System.out.println("Inserting activities from new issues");
        List<String> expected = new ArrayList<>();
        List<String> result = new ArrayList<>();
        List<Issue> iList = new ArrayList<>();
        
        
        iList.add(firstIssue);
        iList.add(secondIssue);
        iList.add(thirdIssue);
        
        expected.add("@dev:10");
        expected.add("@test:10");
        expected.add("@dev:10");
        expected.add("@eat:4");
        
      //  result = tmc.setActivitiesFromIssues(iList, tm);
        
        Assert.assertEquals(expected, result);
        
    }
    @Test
    public void testUpdateListTeamMemberIssuesToMe(){
        System.out.println("Update every teammember from list");
        List<Issue> iList = new ArrayList<>();
        List<TeamMember> tmList = new ArrayList<>();
        List<TeamMember> finalTM = new ArrayList<>();
        firstIssue.setAssignee("username");
        secondIssue.setAssignee("other");
        thirdIssue.setAssignee("onemore");
        
        iList.add(firstIssue);
        iList.add(secondIssue);
        iList.add(thirdIssue);
        
        tm.setUsername("username");
        tmSecond.setUsername("other");
        tmThird.setUsername("onemore");
        
        
        tmList.add(tm);
        tmList.add(tmSecond);
        tmList.add(tmThird);
        
        finalTM = tmc.updateAll(iList, tmList, features);
        
        List<Integer> issuesTOMeSecond = new ArrayList<>();
        issuesTOMeSecond.add(2);
         List<Integer> issuesTOMeFirst = new ArrayList<>();
        issuesTOMeFirst.add(1);
         List<Integer> issuesTOMeThird = new ArrayList<>();
        issuesTOMeThird.add(3);
        
        Assert.assertEquals( issuesTOMeSecond, finalTM.get(1).getIssuesToMe());
        Assert.assertEquals( issuesTOMeFirst, finalTM.get(0).getIssuesToMe());
        Assert.assertEquals(issuesTOMeThird, finalTM.get(2).getIssuesToMe());
    }
    @Test
    public void testUpdateListTeamMemberActivity(){
        System.out.println("Update every teammember from list");
        List<Issue> iList = new ArrayList<>();
        List<TeamMember> tmList = new ArrayList<>();
        List<TeamMember> finalTM = new ArrayList<>();
        firstIssue.setAssignee("username");
        secondIssue.setAssignee("other");
        thirdIssue.setAssignee("onemore");
        
        iList.add(firstIssue);
        iList.add(secondIssue);
        iList.add(thirdIssue);
        
        tm.setUsername("username");
        tmSecond.setUsername("other");
        tmThird.setUsername("onemore");
        
                
        tmList.add(tm);
        tmList.add(tmSecond);
        tmList.add(tmThird);
        
        finalTM = tmc.updateAll(iList, tmList, features);
        
        List<String> firstActiv = new ArrayList<>();
        List<String> secondActiv = new ArrayList<>();
        List<String> thirdActiv = new ArrayList<>();
        
        firstActiv.add("@dev:10");
        firstActiv.add("@test:10");
    
        secondActiv.add("@dev:10");
        secondActiv.add("@eat:4");
        
        Assert.assertEquals(firstActiv, finalTM.get(0).getActivities());
        Assert.assertEquals(secondActiv,finalTM.get(1).getActivities());
        Assert.assertEquals(thirdActiv, finalTM.get(2).getActivities());
    }
    
    @Test
    public void testUpdateListTeamMemberState(){
        System.out.println("Update every teammember from list");
        List<Issue> iList = new ArrayList<>();
        List<TeamMember> tmList = new ArrayList<>();
        List<TeamMember> finalTM = new ArrayList<>();
        firstIssue.setAssignee("username");
        secondIssue.setAssignee("other");
        thirdIssue.setAssignee("onemore");
        
        iList.add(firstIssue);
        iList.add(secondIssue);
        iList.add(thirdIssue);
        
        tm.setUsername("username");
        tmSecond.setUsername("other");
        tmThird.setUsername("onemore");
        
                
        tmList.add(tm);
        tmList.add(tmSecond);
        tmList.add(tmThird);
        
        finalTM = tmc.updateAll(iList, tmList, features);
        
        List<Integer> firstStt = new ArrayList<>();
        List<Integer> secondStt = new ArrayList<>();
        List<Integer> thirdStt = new ArrayList<>();
        
        firstStt.add(0);
        firstStt.add(10);
        firstStt.add(0);
        firstStt.add(0);
        firstStt.add(10);
        firstStt.add(0);
        
        
        secondStt.add(0);
        secondStt.add(10);
        secondStt.add(4);
        secondStt.add(0);
        secondStt.add(0);
        secondStt.add(0);
        
        thirdStt.add(0);
        thirdStt.add(0);
        thirdStt.add(0);
        thirdStt.add(0);
        thirdStt.add(0);
        thirdStt.add(0);
        
        Assert.assertEquals(firstStt, finalTM.get(0).getState());
        Assert.assertEquals(secondStt, finalTM.get(1).getState());
        Assert.assertEquals(thirdStt, finalTM.get(2).getState());
    }
    
    
}
