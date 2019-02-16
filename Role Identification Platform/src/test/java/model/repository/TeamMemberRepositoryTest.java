/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import model.Issue;
import model.TeamMember;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author davi
 */
@Ignore
public class TeamMemberRepositoryTest {

    TeamMember tm;
    TeamMemberRepository tr;
    Issue firstIssue;
    Issue secondIssue;
    Issue thirdIssue;

    public TeamMemberRepositoryTest() {
    }

    @Before
    public void setUp() {
        String issueOwner = "username";
        tm = new TeamMember();
        tr = TeamMemberRepository.getInstance();

        tm.setUsername(issueOwner);
        tr.insert(tm);

        firstIssue = new Issue();
        secondIssue = new Issue();
        thirdIssue = new Issue();

        String firstDescription = "Thing thing \n @dev:10 \n @test:10 \n thing another";
        String secondDescription = "@dev:10 \n @eat:4 \n coiiiisas";
        String thirdDescription = "@cry \n @update \n @git";

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
        tm = null;
        tr.delete("username");
        tr = null;
        firstIssue = null;
        secondIssue = null;
        thirdIssue = null;

    }
    @Test
    public void testSelectFromJira() {
        System.out.println("Reads Jira User data and creates member based on its username");
        List<TeamMember> teamList = new ArrayList<TeamMember>();
        List<String> expected = new ArrayList<String>();
        List<String> teamNames = new ArrayList<>();

        expected.add("claudiodavi");
        expected.add("usuario2");
        expected.add("usuario3");
        expected.add("usuario4");
        expected.add("usuario5");
        expected.add("usuario6");

        for (TeamMember m : tr.selectFromJiraDb()) {
            teamNames.add(m.getUsername());
        }

        Assert.assertEquals(expected, teamNames);
    }

    @Test
    public void testInsertOnlyNames() {
        System.out.println("Receives TeamMembers w/ only names and test Inserting them");
        tm.setUsername("username");

        Assert.assertEquals(true, tr.insert(tm));
    }

}
