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
import model.Feature;
import model.Issue;
import model.Project;
import persistance.FeatureDAO;

/**
 *
 * @author Claudio Davi
 */
public class FeatureRepository {

    private static final int JIRADB = 0;
    private static final int APPDB = 1;
    private static FeatureRepository instance;

    private FeatureDAO featDAO = new FeatureDAO();

    /**
     * Singleton design pattern
     *
     * @return the instance
     */
    public static FeatureRepository getInstance() {
        if (instance == null) {
            instance = new FeatureRepository();
        }
        return instance;
    }

    /**
     * Select all Features from Database
     *
     * @return list of features
     */
    public ArrayList<Feature> selectAll() {
        String query = "SELECT * FROM \"Features\";";

        return featDAO.select(APPDB, query);
    }

    /**
     * Select feature from its ID.
     *
     * @param id
     * @return Feature
     */
    public Feature selectById(int id) {
        String query = "SELECT * FROM \"Features\""
                + " WHERE"
                + " id=" + id + ";";
       
        return featDAO.select(APPDB, query).get(0);
    }

    /**
     * Creates the list of features, sets the ID to the project ID that the
     * issue was created.
     *
     * @param issuesList from Project
     * @return List of features
     */
    public Feature loadFromIssues(List<Issue> issuesList) {
        Feature ft = new Feature();
        issuesList.stream().forEach((i) -> {
            ft.getFeatures().addAll(ft.filterFeature(i.getDescription()));
        });
        ft.setId(issuesList.get(0).getProject());
        return ft;
    }

    /**
     * For each project on the list, we add a new feature using its ID on the
     * Application Database.
     *
     * @param prjs
     */
    public void inject(List<Project> prjs) {
        Feature ft = new Feature();
        for (Project p : prjs) {
            ft.setId(p.getId());
            insert(ft);
        }
    }

    /**
     * Insert Feature into the application database.
     *
     * @param ft
     * @return true if success and false otherwise
     */
    public boolean insert(Feature ft) {
        String query = "INSERT INTO \"Features\" "
                + "VALUES (" + ft.getId()
                + "," + convertFromArray(ft.getFeatures()) + ");";
        return featDAO.insert(query);
    }

    /**
     * Update the Feature
     *
     * @param f a Feature to be updated
     * @return true if success false otherwise
     */
    public boolean update(Feature f) {
        String query = "UPDATE \"Features\" "
                + "SET id=" + f.getId() + ", "
                + "name=" + convertFromArray(f.getFeatures()) + " "
                + "WHERE id =" + f.getId() + ";";
        return featDAO.update(query);
    }

    /**
     * Delete a feature from appication database based on its ID
     *
     * @param id the id of the feature
     * @return true if success false otherwise
     */
    public boolean delete(int id) {
        String query = "DELETE FROM \"Features\" where id=" + id + ";";
        return featDAO.delete(query);
    }

    /**
     * Converts a List into a database friendly string.
     * @param list 
     * @return result a string containing the array
     */
    public String convertFromArray(List<String> list) {
        String result = "\"";
        StringBuffer buf = new StringBuffer();
        list.stream().forEach((s) -> {
            buf.append(s).append("\",\"");
        });
        result = result + buf.toString();
//        for (String s : list) {
//            result = result + s + "\",\"";
//        }
        //-2 because that way we can get rid of the last coma and backslash
        int lastqt;
        if (result.length() > 2) {
            lastqt = result.length() - 2;
            return "'{" + result.substring(0, lastqt) + "}'";
        } else {
            return "'{\"\"}'";
        }

    }

}
