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
import model.Project;
import persistance.ProjectDAO;

/**
 *
 *
 * @author Claudio Davi
 */
public class ProjectRepository {

    /**
     * the instance of the class.
     */
    private static ProjectRepository instance;
    private static final int JIRADB = 0;
    private static final int APPDB = 1;
    private ProjectDAO projDAO = new ProjectDAO();

    /**
     * Singleton design pattern.
     *
     * @return the instance of the class.
     */
    public static ProjectRepository getInstance() {
        if (instance == null) {
            instance = new ProjectRepository();
        }
        return instance;
    }

    /**
     * Select all projects from Jira database.
     *
     * @return List of projects.
     */
    public ArrayList<Project> selectFromJiraDb() {

        String query = "SELECT project.id, "
                + "project.pname "
                + "FROM public.project;";

        return projDAO.select(JIRADB, query);
    }

    /**
     * Select all projects from application database.
     *
     * @return List of projects.
     */
    public ArrayList<Project> selectFromAppDB() {

        String query = "SELECT * "
                + "FROM \"Projects\" ;";

        return projDAO.select(APPDB, query);
    }

    /**
     * Insert a project into the application database.
     *
     * @param prj the project to be inserted
     * @return true if successful, false otherwise.
     */
    public boolean insert(Project prj) {

        String query = "INSERT INTO \"Projects\" "
                + "VALUES ("
                + prj.getId() + ", '"
                + prj.getName() + "');";

        return projDAO.insert(query);

    }

    /**
     * Updates the project on the application database.
     *
     * @param prj the project to be updated.
     * @return true if successful, false otherwise.
     */
    public boolean update(Project prj) {

        String query = "UPDATE \"Projects\""
                + " SET "
                + "id = " + prj.getId() + ", '"
                + prj.getName() + "' "
                + "WHERE id = "
                + prj.getId() + ";";

        return projDAO.update(query);

    }

    /**
     * Deletes a project on the application database.
     *
     * @param prj the project to be deleted.
     * @return true if successful, false otherwise.
     */
    public boolean delete(int prj) {

        String query = "DELETE FROM \"Projects\" "
                + "WHERE id = " + prj + ";";

        return projDAO.delete(query);

    }

    /**
     * Loads all projects from jira database and inserts them into the
     * application database
     *
     * @return true if successful, false otherwise.
     */
    public boolean inject() {

        selectFromJiraDb().stream().forEach((p) -> {
            insert(p);
        });

        return true;
    }
}
