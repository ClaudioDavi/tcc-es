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
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Claudio Davi
 */
public class TeamMember {

    private String username;
    private List<String> activities = new ArrayList<>();
    private List<Integer> issuesToMe = new ArrayList<>();
    private List<Integer> state = new ArrayList<>();

    /**
     * Team Member constructor
     */
    public TeamMember() {

    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the activities
     */
    public List<String> getActivities() {
        return activities;
    }

    /**
     * Returns the state
     *
     * @return list of states
     */
    public List<Integer> getState() {
        return state;
    }

    /**
     * the activities to set.
     *
     * @param act
     */
    public void setActivities(List<String> act) {
        this.activities = act;
    }

    /**
     * WARNING: USE ONLY WHEN GETTING A TEAM MEMBER FROM DATABASE
     *
     * @param stt List of Integers to set States of a Team Member
     */
    public void setStateBD(List<Integer> stt) {
        this.state = stt;
    }

    /**
     * The issues assigned to the team member to set.
     *
     * @param issuesToMe
     */
    public void setIssuesToMe(List<Integer> issuesToMe) {
        this.issuesToMe = issuesToMe;
    }

    /**
     * Gets the list of issues assigned to this specific team member.
     *
     * @return issuestome.
     */
    public List getIssuesToMe() {
        return issuesToMe;
    }

    /**
     * Filter issue description to add only features to set Activities
     *
     * @param description Issue description
     * @return descFeatures String[]
     */
    public String[] filterIssueDescription(String description) {
        String[] descFeatures = {""};

        if (description != null) {
            description = description.toLowerCase(Locale.getDefault());
            description = description.trim();
            descFeatures = description.split("\\r?\\n");

            for (int i = 0; i < descFeatures.length; i++) {

                if (!descFeatures[i].trim().startsWith("@")) {
                    descFeatures[i] = "";
                } else {
                    String aux = descFeatures[i];
                    descFeatures[i] = aux.trim();
                }

            }
        }

        return descFeatures;
    }

    /**
     * Receives the list of Global Features and compares to my activities. Adds
     * the state of myActivities in relation to the Global Features
     *
     * @param activity List from object
     * @param feature
     */
    public void setState(List<String> feature, List<String> activity) {
        if (feature.size() > state.size()) {
            int diff = feature.size() - state.size();
            for (int j = 0; j < diff; j++) {
                state.add(0);
            }

        }

        for (int i = 0; i < feature.size(); i++) {
            for (int j = 0; j < activity.size(); j++) {
                String s = feature.get(i).toLowerCase(Locale.getDefault());
                String my = activity.get(j).toLowerCase();
                if (s.trim().regionMatches(0, my, 0, 4)) {
                    String aux[] = my.split(":");
                    state.set(i, state.get(i) + Integer.parseInt(aux[1].trim()));
                }
            }
        }
    }
//    /**
//     * Receives the list of Global Features and compares to my activities. Adds
//     * the state of myActivities in relation to the Global Features
//     *
//     * @param activity List from object
//     * @param feature
//     */
//    public void setStateOpt(List<String> feature, List<String> activity, List<String> lastAdded) {
//        if (!lastAdded.isEmpty() && feature.size() != activity.size()) {
//            for (String last : lastAdded) {
//                if (feature.contains(last)) {
//                    state.add(feature.indexOf(last), 0);
//                }
//            }
//        }
//        for (int i = 0; i < feature.size(); i++) {
//            for (int j = 0; j < activity.size(); j++) {
//                String s = feature.get(i).toLowerCase();
//                String my = activity.get(j).toLowerCase();
//                if (s.trim().regionMatches(0, my, 0, 4)) {
//                    String aux[] = my.split(":");
//                    state.set(i, state.get(i) + Integer.valueOf(aux[1].trim()));
//                }
//            }
//        }
//    }
}
