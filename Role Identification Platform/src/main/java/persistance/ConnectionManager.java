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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author davi
 */
public class ConnectionManager {

    /**
     * JIRA database code.
     */
    protected static final int JIRADB = 0;

    /**
     * Application database code.
     */
    protected static final int APPDB = 1;
    private static ConnectionManager instance = null;
    /**
     * The connection.
     */
    public Connection con;
    /**
     * The statement.
     */
    public Statement stm;
    /**
     * The result set.
     */
    public ResultSet rs;

    /**
     * Singleton design pattern.
     *
     * @return a connection manager instance.
     */
    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    /**
     * Creates a connection. It relies on the database that is being passed
     * through the parameter. One should edit the user and password when
     * configuring the application to run with their SO and settings.
     *
     * @param database
     * @return the connection established
     */
    private Connection getConnection(int database) {
        try {
            switch (database) {
                case JIRADB:

                    String url = "jdbc:postgresql://localhost:5432/jiradb";
                    String user = "jirauser";
                    String pswd = "jirauser";
                    con = DriverManager.getConnection(url, user, pswd);
                    //     System.out.println("CONNECTING TO JIRA DATABASE");
                    break;
                case APPDB:
                    String urlx = "jdbc:postgresql://localhost:5432/roleApp";
                    String userx = "postgres";
                    String pswdx = "root";
                    con = DriverManager.getConnection(urlx, userx, pswdx);
                    //    System.out.println("CONNECTING TO MAIN DATABASE");
                    break;
                default:
                    String urly = "jdbc:postgresql://localhost:5432/roleApp";
                    String usery = "postgres";
                    String pswdy = "root";
                    con = DriverManager.getConnection(urly, usery, pswdy);
                    //      System.out.println("CONNECTING TO MAIN DATABASE");
                    break;
            }
        } catch (SQLException sq) {
            System.err.println("SQL FAIL DATABASE");
        }
        return con;
    }

    /**
     * Creates a new connection to the database.
     *
     * @param database to connect
     * @return
     */
    public boolean connect(int database) {
        try {
            con = ConnectionManager.getInstance().getConnection(database);
            stm = con.createStatement();
            //      System.out.println("CONNECTED");
            return true;
        } catch (SQLException ex) {
            System.err.println("FATAL FAIL CONNECTING WITH DATABASE");
            return false;
        }
    }

    /**
     * Close the database connection
     *
     * @return true if success, false otherwise.
     */
    public boolean close() {
        try {
            stm.close();
            con.close();
            //        System.out.println("CONNECTION CLOSED");
            return true;
        } catch (SQLException ex) {
            System.err.println("FATAL FAIL CLOSING CONNECTION WITH DATABASE");
            return false;
        }
    }

    /**
     * Case a database cleanup need to be executed, call this method providing
     * the SQL routine to do so.
     *
     * @param query
     * @return true if success, false otherwise
     */
    public boolean clean(String query) {
        connect(APPDB);
        try {
            stm.executeUpdate(query);
         //   System.out.println("DATABASE CLEANED");
            return true;
        } catch (SQLException ex) {
            System.err.println("CLEAN DATABASE ERROR");
            return false;
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
