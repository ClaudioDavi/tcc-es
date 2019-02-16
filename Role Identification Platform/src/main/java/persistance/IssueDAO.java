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

import java.sql.SQLException;
import java.util.ArrayList;
import model.Issue;

/**
 *
 * @author Claudio Davi
 */
public class IssueDAO extends ConnectionManager implements DAO {

    /**
     * Select all Issues from the selected database.
     *
     * @param database 1 for appdb, 0 for jiradb
     * @param query the query to be executed.
     * @return a list of issues.
     */
    @Override
    public ArrayList<Issue> select(int database, String query) {
        ArrayList<Issue> issues = new ArrayList();
        Issue is;
        try {
            connect(database);
            rs = stm.executeQuery(query);

            while (rs.next()) {
                is = new Issue();
                is.setIssueNum(rs.getInt(1));
                is.setDescription(rs.getString(2));
                is.setReporter(rs.getString(3));
                is.setAssignee(rs.getString(4));
                is.setProject(rs.getInt(5));
                is.setSummary(rs.getString(6));

                issues.add(is);
            }

        } catch (SQLException ex) {
            System.out.println("ISSUE SELECT ERROR");
        } finally {

            close();
        }

        return issues;
    }

    /**
     * Updates the Issue on the application database.
     *
     * @param query the query to be executed
     * @return true if successful, false otherwise.
     */
    @Override
    public boolean update(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.out.println("ISSUE UPDATE ERROR");
            return false;
        } finally {
            close();
        }

    }

    /**
     * Deletes an issue fro the database.
     *
     * @param query the query to be executed
     * @return true if successful, false otherwise.
     */
    @Override
    public boolean delete(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.out.println("ISSUE DELETE ERROR");
            return false;
        } finally {
            close();
        }
    }

    /**
     * Inserts an issue into the application databse.
     *
     * @param query the query to be executed
     * @return true if successful, false otherwise.
     */
    @Override
    public boolean insert(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.out.println("ISSUE INSERT ERROR");
            return false;
        } finally {
            close();
        }
    }

}
