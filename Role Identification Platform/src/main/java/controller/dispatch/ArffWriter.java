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
package controller.dispatch;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TeamMember;
import weka.core.Instances;

/**
 *
 * @author Claudio Davi
 */
public class ArffWriter {

    private static final String HEADER = "%Author: Claudio Davi Souza \n"
            + "%Role Identification Platform Dataset";
    private static final String RELATION = "@RELATION features";
    private static final String ATTRIBUTES = "@ATTRIBUTE ";
    private static final String TYPE = "NUMERIC";
    private static final String DATA = "@DATA";
    private List<String> usernameOrder = new ArrayList<>();

    private PrintWriter createWriter(String fileName) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName + ".arff", "UTF-8");

        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(ArffWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return writer;

    }
    /**
     * Writes a formatted .arff file that will be analyzed by weka algorithms. 
     * 
     * @param tList list of team members
     * @param features list of features
     * @param fileName the name of the file to be written
     */

    public void write(List<TeamMember> tList, List<String> features, String fileName) {
        PrintWriter writer = createWriter(fileName);

        writer.println(HEADER);
        writer.println();
        writer.println();

        writer.println(RELATION);
        writer.println();
        writer.println();
        for (String f : features) {
            writer.println(ATTRIBUTES + f.substring(1) + " " + TYPE);
        }
        writer.println();
        writer.println();
        writer.println(DATA);

        for (TeamMember t : tList) {
            writer.println(convertFromIntegerArray(t.getState()));
            usernameOrder.add(t.getUsername());
        }

        writer.close();
    }
    /**
     * Writes a file containing the result of the kmeans algorithm in text format.
     * 
     * Result e.g.: "user is an instance of cluster 0".
     * @deprecated 
     * @param kmeansOut the result of the kmeans algorithm
     * @throws FileNotFoundException 
     */
    public void writeKMeansOutput(int[] kmeansOut) throws FileNotFoundException {
        if (kmeansOut.length == 0) {
            System.out.println("EMPTY");
        }
        Date date = new Date();
        String fileName = date.toString();
        try (PrintWriter write = createWriter(fileName)) {
            for (int i = 0; i < kmeansOut.length; i++) {
                write.println(usernameOrder.get(i) + " is an instance of cluster: " + kmeansOut[i]);
            }
        }

    }
    /**
     * Converts weka instances into an ARFF file.
     * @param dataset
     * @throws IOException 
     */
    public void convertFromInstanceToArff(Instances dataset) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("test.arff"));
        writer.write(dataset.toString());
        writer.flush();
        writer.close();
    }

    /**
     * Converts an integer array into a String ready to be processed by the writer
     * @param list
     * @return 
     */
    private String convertFromIntegerArray(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        String result = "";
        int lastqt;
        
        StringBuffer buf = new StringBuffer();
        list.stream().forEach((i) -> {
            buf.append(i).append(", ");
        });
        result = buf.toString();
//        for (int i : list) {
//            result = result + i + ", ";
//        }
        lastqt = result.length();
        return result.substring(0, lastqt - 2).trim();
    }

    /**
     * This method is actually on the interface, I've transfered it because here
     * lies part of the problem and I did not want to make this even more
     * complex than it already is.
     *
     * @param tList
     * @param features
     * @return
     * @throws FileNotFoundException
     */
    public String generateData(List<TeamMember> tList, List<String> features) throws FileNotFoundException {
        Date dt = new Date();
        String datasetUniq = "dataSet" + dt.toString();
        write(tList, features, datasetUniq);
        return datasetUniq + ".arff";
    }

}
