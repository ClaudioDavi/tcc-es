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
package persistance;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.TeamMember;
import static persistance.ConnectionManager.APPDB;

/**
 *
 * @author Claudio Souza
 */
public class TeamMemberDAO extends ConnectionManager implements DAO {

    /**
     * Selects all the team members from the chosen database. Update: this
     * method is only valid if you are using the JIRA database. to get
     * teammembers from the application database you should use "selectApp();"
     *
     * @deprecated
     * @param database the database from the team members be selected from 1 for
     * AppDb, 0 for JIRA DB
     * @param query the query to be executed
     * @return a list of team members.
     */
    @Override
    public ArrayList select(int database, String query) {
        ArrayList<TeamMember> teammembers = new ArrayList<>();
        TeamMember tm;
        try {
            connect(database);
            rs = stm.executeQuery(query);

            while (rs.next()) {
                tm = new TeamMember();
                tm.setUsername(rs.getString("lower_user_name"));
                teammembers.add(tm);
            }

        } catch (SQLException ex) {
            System.err.println("TEAMMEMBER SELECT ERROR");
        } finally {
            close();
        }
        return teammembers;
    }

    /**
     * Select TeamMember from the application database. this method converts the
     * array of information contained on the database.
     *
     * @param database the database to be selected AppDb recommended
     * @param query the query to be executed
     * @return a list of team members
     */
    public ArrayList selectApp(int database, String query) {

        ArrayList<TeamMember> teammembers = new ArrayList<>();
        TeamMember tm;
        try {

            connect(database);
            rs = stm.executeQuery(query);

            while (rs.next()) {
                List<String> listAct = new ArrayList<>();

                Array acts = rs.getArray(2);
                for (Object obj : (Object[]) acts.getArray()) {
                    try {
                        String ar = (String) obj;
                        listAct.add(ar);

                    } catch (Exception e) {
                        System.out.println("COULD NOT CONVERT ACTIVITIES ARRAY");
                    }
                }
                Array stt = rs.getArray(4);
                List<Integer> listStt = new ArrayList<>();
                try {
                    for (Object obj : (Object[]) stt.getArray()) {

                        int st = (int) obj;
                        listStt.add(st);

                    }
                } catch (Exception e) {
                    System.out.println("COULD NOT CONVERT STATE ARRAY");
                }

                Array myIs = rs.getArray("myissues");
                List<Integer> listIsu = new ArrayList<>();
                try {
                    for (Object obj : (Object[]) myIs.getArray()) {

                        int iss = (int) obj;
                        listIsu.add(iss);
                        //         System.out.println("ISSUE: " + iss);
                    }
                } catch (Exception e) {
                    System.out.println("COULD NOT CONVERT MYISSUES ARRAY");
                }
                tm = new TeamMember();
                tm.setUsername(rs.getString(1));

                tm.setActivities(listAct);
                tm.setStateBD(listStt);
                tm.setIssuesToMe(listIsu);
                teammembers.add(tm);
            }

        } catch (SQLException ex) {
            System.err.println("TEAMMEMBER SELECT ERROR");
        } finally {

            close();
        }
        return teammembers;

    }

    /**
     * Updates a TeamMember on the application database
     *
     * @param query the query to be executed
     * @return true if success, false if not.
     */
    @Override
    public boolean update(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println("TEAMMEMBER UPDATE ERROR: " + ex.getSQLState());
            return false;
        } finally {
            close();
        }
    }

    /**
     * Deletes a team member from the application database.
     *
     * @param query the query to be executed
     * @return true if success, false if not.
     */
    @Override
    public boolean delete(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println("TEAMMEMBER DELETE ERROR");
            return false;
        } finally {
            close();
        }
    }

    /**
     * Inserts a team member into the database. Some error codes had to be
     * handled because they do not affect the processing of the data.
     *
     * @param query the query to be processed
     * @return true if success, false if not.
     */
    @Override
    public boolean insert(String query) {
        connect(APPDB);

        try {
            stm.executeQuery(query);
            return true;
        } catch (SQLException ex) {
//            System.err.println(ex.getCause());
//            System.err.println("CODE: " + ex.getErrorCode());
            if (ex.getErrorCode() == 23505 || ex.getErrorCode() == 0) {
                return true;
            } else {
                System.err.println("TEAMMEMBER INSERT ERROR");
                return false;
            }
        } finally {
            close();
        }
    }

}
