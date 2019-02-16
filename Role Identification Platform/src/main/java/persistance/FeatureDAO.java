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
import model.Feature;
import static persistance.ConnectionManager.APPDB;

/**
 *
 * @author Claudio Davi
 */
public class FeatureDAO extends ConnectionManager implements DAO {

    /**
     * Select all Feature objects from the selected database.
     *
     * @param database for Application database select 1, for JIRA databse
     * select 2
     * @param query the SQL query to execute
     * @return an array of features
     */
    @Override
    public ArrayList<Feature> select(int database, String query) {
        ArrayList<Feature> features = new ArrayList<>();
        Feature ft;

        try {
            connect(database);
            rs = stm.executeQuery(query);

            while (rs.next()) {
                ft = new Feature();
                List<String> feat = new ArrayList<>();
                ft.setId(rs.getInt(1));
                Array fs = rs.getArray(2);
                for (Object obj : (Object[]) fs.getArray()) {
                    try {
                        String ar = (String) obj;
                        feat.add(ar);

                    } catch (Exception e) {
                        System.out.println("COULD NOT CONVERT FEATURES ARRAY");
                    }
                }
                ft.setFeatures(feat);
                features.add(ft);
            }
        } catch (SQLException ex) {
            System.out.println("FEATURE SELECTION ERROR");
        }
        return features;

    }

    /**
     * Update the features from the application database.
     *
     * @param query the SQL query to be executed
     * @return true if successful, false otherwise.
     */
    @Override
    public boolean update(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.err.println("FEATURE UPDATE ERROR");
            return false;
        } finally {
            close();
        }
    }

    /**
     * Deletes a feature from the database.
     *
     * @param query the SQL query to be executed.
     * @return true if successful, false otherwise
     */
    @Override
    public boolean delete(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.out.println("FEATURE DELETE ERROR");
            return false;
        } finally {
            close();
        }
    }
    /**
     * Inserts a feature inside the application database.
     * @param query the SQL to be executed
     * @return true if successful and false otherwise 
     */
    @Override
    public boolean insert(String query) {
        connect(APPDB);

        try {
            stm.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            System.out.println("FEATURE INSERT ERROR");
            return false;
        } finally {
            close();
        }
    }

}
