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

public class GenerateTestData {

    private List<String> features = new ArrayList<>();

    /**
     * List of probability for a feature appearing. In percentage
     */
    private static final int[] PROB1 = {5, 5, 5, 5, 5, 100, 100, 100, 100, 100, 5, 5, 5, 5, 5};

    /**
     * List of probability for a feature appearing. In percentage
     */
    private static final int[] PROB2 = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 100, 100, 100, 100, 100};

    /**
     * List of probability for a feature appearing. In percentage
     */
    private static final int[] PROB3 = {100, 100, 100, 100, 100, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
    /**
     * List of random names to be selected when creating a random report
     */
    private static final String[] NAMES = {"Pedro", "Leticia", "Rodrigo", "Claudio",
        "Marcelo", "Maria", "Roberto", "Willian", "Lucas", "Fernando", "Jean", "Helena",
        "Ricardo", "Davi", "Daniel", "Lena", "Gabriel", "Luis", "Vitor", "Alan", "Vanessa", "Leonardo"};
    public static int[][] featureProb = {PROB1, PROB2, PROB3};

    /**
     * Creates a list of features
     *
     * @return list of features
     */
    public List<String> createFeatures() {

        features.add("@code");
        features.add("@model");
        features.add("@plan");
        features.add("@document");
        features.add("@prototype");
        features.add("@debug");
        features.add("@test");
        features.add("@design");
        features.add("@layout");
        features.add("@reference");
        features.add("@graduate");
        features.add("@report");
        features.add("@decision");
        features.add("@manage");
        features.add("@implement");

        return features;
    }

    /**
     * Creates the team member and randomizes its state. For testing purposes
     *
     * @param prob
     * @param features
     * @param name
     * @return Team Member
     */
    public TeamMember createTeamMembers(int[] prob, List<String> features, String name) {
        TeamMember tm = new TeamMember();
        tm.setUsername(name);
        List<String> acts = new ArrayList();
        for (int i = 0; i < features.size(); i++) {
            String possibleAct = features.get(i);
            if (randomWithRange(0, 100) <= prob[i]) {
                acts.add(possibleAct + ":" + randomWithRange(1, 5));
            }
        }
        System.out.println(tm.getUsername() + " CREATED");
        tm.setState(features, acts);

        return tm;
    }

    public List<TeamMember> createList(int size) {
        List<TeamMember> tList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            int probFeatures = randomWithRange(0, 2);
            

            tList.add(createTeamMembers(featureProb[probFeatures], features, NAMES[i]));
        }

        return tList;
    }

    public int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
}
