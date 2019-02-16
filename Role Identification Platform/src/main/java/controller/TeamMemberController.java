/*
 * The MIT License
 *
 * Copyright 2016 Claudio Souza.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Issue;
import model.TeamMember;
import model.repository.TeamMemberRepository;

/**
 *
 * @author Claudio Davi
 */
public class TeamMemberController {

    TeamMemberRepository tRep = TeamMemberRepository.getInstance();
    private ObservableList observableList = FXCollections.observableArrayList();

    /**
     * Creates the list of team members that will be sent to the interface
     * @return list of team member names
     */
    public ObservableList setTeamMemberView() {
        List<String> usernames = new ArrayList<>();

        tRep.selectFromAppDb().stream().forEach((t) -> {
            usernames.add(t.getUsername());
        });

        observableList.setAll(usernames);
        return observableList;
    }
    /**
     * Reads all team members from JIRA database and saves them on the applications
     * database.
     * 
     */
    public void loadTMData() {
        tRep.inject();
    }
    /**
     * Receive data from the database and updates every team member with their 
     * new data based on the data contained on the lists.
     * @param iList List of issues
     * @param tmList List of teamMembers
     * @param features List of Features
     * @return List of updated TeamMembers
     */
    public List<TeamMember> updateAll(List<Issue> iList, 
            List<TeamMember> tmList, List<String> features) {

        for (TeamMember tm : tmList) {
            //Issues state vector is repeating.
            for (Issue i : iList) {
                if (i.getAssignee().equals(tm.getUsername())) {
                    /* Update TeamMember from here*/

                    if (!tm.getIssuesToMe().contains(i.getIssueNum())) {
                        List<String> acts = new ArrayList<>();
                        
                        
                        for (String s : tm.filterIssueDescription(i.getDescription())) {
                            if (!s.isEmpty() && s.contains(":")) {
                                s = s.trim();
                                tm.getActivities().add(s);
                                acts.add(s);
                            }
                        }

                        tm.getIssuesToMe().add(i.getIssueNum());
                        tm.setState(features, acts);

                    }

                }
            }

        }
        tRep.updateAll(tmList);
        return tmList;
    }
}
