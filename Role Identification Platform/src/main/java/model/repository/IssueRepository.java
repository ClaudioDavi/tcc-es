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
import model.Issue;
import persistance.IssueDAO;

/**
 * Class contains method that recovers all issues available on JIRA DataBase And
 * Methods to include and update and delete on RoleApp Database
 *
 * @author Claudio Davi
 */
public class IssueRepository {

    private static final int JIRADB = 0;
    private static final int APPDB = 1;
    /**
     * instance of the class.
     */
    private static IssueRepository instance;
    private IssueDAO issueDAO = new IssueDAO();

    /**
     * Singleton design pattern
     *
     * @return an instance of the class
     */
    public static IssueRepository getInstance() {
        if (instance == null) {
            instance = new IssueRepository();
        }
        return instance;
    }

    /**
     * Select All Issues available on Jira Database
     *
     * @return
     */
    public ArrayList<Issue> selectFromJira() {

        String query = "SELECT jiraissue.issuenum, "
                + "jiraissue.description, "
                + "jiraissue.reporter, "
                + "jiraissue.assignee, "
                + "jiraissue.project, "
                + "jiraissue.summary \n"
                + "FROM public.jiraissue \n"
                + " WHERE jiraissue.resolutiondate IS NOT NULL; ";

        return issueDAO.select(JIRADB, query);

    }

    /**
     * Loads all issues from JIRA database and inserts them into the Application
     * databse.
     *
     * @return true if successful, false otherwise.
     */
    public boolean inject() {

        selectFromJira().stream().forEach((i) -> {
            insert(i);
        });
        return true;
    }

    /**
     * Select All issues available on AppDB
     *
     * @return ArrayList containing the issues on the ApplicationDB
     */
    public ArrayList<Issue> selectFromAppDb() {

        String query = "SELECT issuenum, description, "
                + "reporter, assignee, project, summary\n"
                + "  FROM \"Issues\";";

        return issueDAO.select(APPDB, query);

    }

    /**
     * Select Issues from Project
     *
     * @return ArrayList containing issues from project id
     * @param projectId from project
     */
    public ArrayList<Issue> selectFromAppDbByProject(int projectId) {

        String query = "SELECT * FROM \"Issues\" WHERE project= '" + projectId + "';";

        return issueDAO.select(APPDB, query);

    }

    /**
     * insert a new issue into Application database
     *
     * @param i an Issue to be selected
     * @return True if the insertion is successful. False otherwise
     */
    public boolean insert(Issue i) {

        String query = "INSERT INTO \"Issues\"(\n"
                + "issuenum, description, reporter, assignee, project, summary)\n"
                + "    VALUES "
                + "('" + i.getIssueNum()
                + "','" + i.getDescription()
                + "','" + i.getReporter()
                + "','" + i.getAssignee()
                + "','" + i.getProject()
                + "','" + i.getSummary() + "');";
        // System.out.println(query);
        return issueDAO.insert(query);

    }

    /**
     * Updates the selected issue based on its ID on Application Database
     *
     * @param i an Issue to be updated
     * @return True if the update is successful. False otherwise
     */
    public boolean update(Issue i) {
        String query = "UPDATE \"Issues\"\n"
                + "   SET issuenum="
                + i.getIssueNum() + ", "
                + "description= ' "
                + i.getDescription() + " ' , "
                + "reporter= ' "
                + i.getReporter() + " ' , "
                + "assignee= ' "
                + i.getAssignee() + " ', "
                + "project="
                + i.getProject() + "\n"
                + " WHERE "
                + "issuenum =" + i.getIssueNum() + ";";

        return issueDAO.insert(query);
    }

    /**
     * Delete from Application Database based on the issuenum provided
     *
     * @param issuenum the number of the issue
     * @return True if the deletion is successful. False otherwise
     */
    public boolean delete(int issuenum) {
        String query = "DELETE FROM \"Issues\"\n"
                + " WHERE "
                + "issuenum ="
                + issuenum + ";";

        return issueDAO.delete(query);
    }

}
