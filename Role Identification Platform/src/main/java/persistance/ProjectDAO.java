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
import model.Project;
import static persistance.ConnectionManager.APPDB;

/**
 *
 * @author Claudio Davi
 */
public class ProjectDAO extends ConnectionManager implements DAO {

    /**
     * Select all Projects from wither database. Select 1 for Application
     * Database or 0 for JIRA database.
     *
     * @param database the database to be selected from. 0 for JIRA, 1 for App
     * @param query the query to be executed
     * @return A list of projects
     */
    @Override
    public ArrayList select(int database, String query) {
        connect(database);

        ArrayList<Project> projects = new ArrayList<>();
        Project pr;

        try {
            rs = stm.executeQuery(query);

            while (rs.next()) {
                pr = new Project();

                pr.setId(rs.getInt(1));
                pr.setName(rs.getString(2));

                projects.add(pr);
            }

        } catch (SQLException ex) {
            System.err.println("PROJECT SELECTION ERROR " + ex.getErrorCode());

        }

        return projects;
    }

    /**
     * Updates the project into the application database.
     *
     * @param query the query to be executed
     * @return true if successful, false otherwise
     */
    @Override
    public boolean update(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println("PROJECT UPDATE ERROR");
            return false;
        } finally {
            close();
        }
    }

    /**
     * Deletes the selected project from the database.
     *
     * @param query the query to be executed
     * @return true if success, false otherwise.
     */
    @Override
    public boolean delete(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println("PROJECT DELETE ERROR");
            return false;
        } finally {
            close();
        }
    }

    /**
     * Inserts a project into the database.
     *
     * @param query the query to be executed.
     * @return true if success, false otherwise.
     */
    @Override
    public boolean insert(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println("PROJECT INSERT ERROR");
            return false;
        } finally {
            close();
        }
    }

}
