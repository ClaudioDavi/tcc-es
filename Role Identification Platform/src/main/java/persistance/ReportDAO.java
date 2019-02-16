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
import model.Report;

/**
 *
 * @author Claudio Souza
 */
public class ReportDAO extends ConnectionManager implements DAO {

    /**
     * Select all the reports on the database.
     *
     * @param database the database to be selected from, Send always AppDb,
     * otherwise the method doesn't work. The JIRA database does not contain any
     * Report Table.
     * @param query the query to be executed
     * @return a list of reports.
     */
    @Override
    public ArrayList select(int database, String query) {
        connect(database);

        ArrayList<Report> reports = new ArrayList();

        try {
            rs = stm.executeQuery(query);

            while (rs.next()) {
                Report r = new Report();

                r.setReportName(rs.getString("filename"));
                reports.add(r);
            }
            rs.close();
            stm.close();

        } catch (SQLException ex) {
            System.err.println("REPORT SELECTION ERROR " + ex.getErrorCode());

        }
        return reports;
    }

    /**
     * Update Method not implemented (not needed)
     *
     * @deprecated
     * @param query
     * @return
     */
    @Override
    public boolean update(String query) {
        throw new UnsupportedOperationException("NOT POSSIBLE");
    }

    /**
     * Deletes a report from the database. Currently not supported.
     *
     * @param query the query to be executed
     * @return true if success, false if fails
     */
    @Override
    public boolean delete(String query) {
        connect(APPDB);
        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println("REPORT DELETE ERROR");
            return false;
        } finally {
            close();
        }
    }

    /**
     * Inserts a new report into the application database.
     *
     * @param query the query to be executed
     * @return true if success, false otherwise.
     */
    @Override
    public boolean insert(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println("REPORT INSERT ERROR");
            return false;
        } finally {
            close();
        }
    }

}
