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
package model.repository;

import java.util.ArrayList;
import java.util.List;
import model.TeamMember;
import persistance.TeamMemberDAO;

/**
 *
 * @author Claudio Davi
 */
public class TeamMemberRepository {

    /**
     * The instance of this class.
     */
    private static TeamMemberRepository instance;
    private static final int JIRADB = 0;
    private static final int APPDB = 1;
    private TeamMemberDAO teamDAO = new TeamMemberDAO();

    /**
     * Singleton design pattern.
     *
     * @return an instance of the TeamMemberRepository class.
     */
    public static TeamMemberRepository getInstance() {
        if (instance == null) {
            instance = new TeamMemberRepository();
        }
        return instance;
    }

    /**
     * Selects all TeamMembers names from the Jira database.
     *
     * @return list of teamMembers with their names only.
     */
    public ArrayList<TeamMember> selectFromJiraDb() {

        String query = "SELECT lower_user_name FROM app_user;";

        return teamDAO.select(JIRADB, query);
    }

    /**
     * Select all TeamMembers from the application Database.
     *
     * @return a list of team members.
     */
    public ArrayList<TeamMember> selectFromAppDb() {
        String query = "SELECT * FROM \"TeamMembers\";";

        return teamDAO.selectApp(APPDB, query);
    }

    /**
     * Select a TeamMember by its username.
     *
     * @param user the username of the teamMember
     * @return A TeamMember object.
     */
    public TeamMember selectByUsername(String user) {
        String query = "SELECT * FROM \"TeamMembers\" WHERE username = '"
                + user + "';";
        return (TeamMember) teamDAO.selectApp(APPDB, query).get(0);
    }

    /**
     * Inserts a team member into the application Database.
     *
     * @param tm the team member to be inserted.
     * @return true if success, false otherwise.
     */
    public boolean insert(TeamMember tm) {

        String query = "INSERT INTO \"TeamMembers\" VALUES"
                + " ('" + tm.getUsername() + "', '{"
                + convertFromStringArray(tm.getActivities()) + "}',"
                + convertFromIntegerArray(tm.getIssuesToMe())
                + "," + convertFromIntegerArray(tm.getState()) + ");";
        return teamDAO.insert(query);
    }

    /**
     * Updates the TeamMember on the database.
     *
     * @param tm The team member to be updated
     * @return true if successful, false otherwise.
     */
    public boolean update(TeamMember tm) {
        String query = "UPDATE \"TeamMembers\" SET "
                + "username='" + tm.getUsername() + "', "
                + "activities = '{" + convertFromStringArray(tm.getActivities()) + "}', "
                + "myIssues = " + convertFromIntegerArray(tm.getIssuesToMe()) + ", "
                + "state =" + convertFromIntegerArray(tm.getState()) + " WHERE username='"
                + tm.getUsername() + "';";

        return teamDAO.update(query);
    }

    /**
     * Deletes a team member from the application Database.
     *
     * @param username The username of the team member to be deleted.
     * @return true if success, false otherwise.
     */
    public boolean delete(String username) {
        String query = "DELETE FROM \"TeamMembers\" where username = '"
                + username + "';";

        return teamDAO.delete(query);
    }

    /**
     * Gets all the team members from the JIRA Database and saves them into the
     * application database.
     *
     * @return true if successful, false otherwise.
     */
    public boolean inject() {
        selectFromJiraDb().stream().forEach((t) -> {
            insert(t);
        });
        return true;
    }

    /**
     * Update all the teammembers on the list
     *
     * @param tList a list of team members to be updated.
     * @return true if successful, false otherwise.
     */
    public boolean updateAll(List<TeamMember> tList) {
        tList.stream().forEach((t) -> {
            update(t);
        });
        return true;
    }

    /**
     * Converts a List to an String that can be handled by the Database server.
     *
     * @param list of strings to be converted.
     * @return an string ready to be processed with SQL.
     */
    public String convertFromStringArray(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        String result = "\"";
        int lastqt;

        StringBuffer buf = new StringBuffer();
        list.stream().forEach((s) -> {
            buf.append(s).append("\",\"");
        });
        result += buf.toString();
//        for (String s : list) {
//            result = result + s + "\",\"";
//        }
        lastqt = result.length();
        //-2 because that way we can get rid of the last coma and quotation mark
        return result.substring(0, lastqt - 2);

    }

    /**
     * Converts a list of integer to an string ready to be processed by the SQL.
     *
     * @param list the list of integers to be converted.
     * @return the String ready to be processed.
     */
    public String convertFromIntegerArray(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return "'{}'";
        }

        String result = "";
        int lastqt;

        StringBuffer buf = new StringBuffer();
        list.stream().forEach((i) -> {
            buf.append(i).append(", ");
        });
        result = buf.toString();

        lastqt = result.length();

        return "'{" + result.substring(0, lastqt - 2) + "}'";
    }

    /**
     * Checks if the username passed as an argument is contained into the List
     * passsed as a parameter.
     *
     * @param tm the List of team members to be Searched into
     * @param username the username looked for.
     * @return the index of the name if found, -1 otherwise.
     */
    public int containsName(List<TeamMember> tm, String username) {
        for (int i = 0; i < tm.size(); i++) {
            TeamMember t = tm.get(i);

            if (t != null && t.getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }
}
