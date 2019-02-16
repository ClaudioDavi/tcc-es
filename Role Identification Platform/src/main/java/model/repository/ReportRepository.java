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
import model.Report;
import persistance.ReportDAO;

/**
 *
 * @author Claudio Souza
 */
public class ReportRepository {

    /**
     * The instance of the class.
     */
    private static ReportRepository instance;
    private static final int APPDB = 1;
    private ReportDAO repDAO = new ReportDAO();

    /**
     * Singleton design pattern.
     *
     * @return an instance of the class ReportRepository
     */
    public static ReportRepository getInstance() {
        if (instance == null) {
            instance = new ReportRepository();
        }
        return instance;
    }

    /**
     * Select all Reports from the Application database.
     *
     * @return List of reports.
     */
    public ArrayList<Report> selectAll() {
        String query = "SELECT * FROM \"Reports\"";
        return repDAO.select(APPDB, query);
    }

    /**
     * Inserts a report into the application database.
     *
     * @param rep Report to be inserted.
     * @return true if success, false otherwise.
     */
    public boolean insert(Report rep) {
        String query;
        query = "INSERT INTO \"Reports\" VALUES ('"
                + rep.getReportName() + "')";
        return repDAO.insert(query);
    }

    /**
     * WARINING: This method uses a workaround used to clean all tables for
     * loading new reports. What it does is: Whenever the application has to
     * Load data from JIRA we all Truncate tables except report. That means that
     * we load all data again. Therefore, it permits us creating new reports
     * always on a "clean of bugs" environment
     *
     * @return true if success, false otherwise.
     */
    public boolean clean() {
        String query = "TRUNCATE TABLE \"TeamMembers\", \"Issues\", "
                + "\"Features\", \"Projects\";";
        return repDAO.clean(query);
    }

    /**
     * Deletes a Report from the database.
     *
     * @param rep to be deleted
     * @return true if success, false otherwise.
     */
    public boolean delete(String rep) {
        String query = "DELETE FROM \"Reports\" WHERE "
                + "filename = '" + rep + ".png';";
        return repDAO.delete(query);
    }

}
