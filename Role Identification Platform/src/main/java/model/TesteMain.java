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

import controller.dispatch.ArffWriter;
import controller.dispatch.WekaAlgorithms;
import java.io.FileNotFoundException;
import java.util.List;
import persistance.ReportDAO;
import weka.core.Instances;

/**
 *
 * @author Claudio Davi
 */
public class TesteMain {

    /**
     * main
     * @param args 
     */
    public static void main(String[] args) {
       ERASEEVERYTHING();
    }
    /**
     * Reproduces weka behavior used by the controller algorithms
     * @throws FileNotFoundException
     * @throws Exception 
     */
    public static void reproducingWekaBehaviour() throws FileNotFoundException, Exception {
        ArffWriter writer = new ArffWriter();
        WekaAlgorithms weka = new WekaAlgorithms();
        GenerateTestData generate = new GenerateTestData();

        List<String> features = generate.createFeatures();

        /*Creating 6 team members, will ideally create 3 different profiles 
        that can be classified into 3 different clusters */
        List<TeamMember> tList = generate.createList(3);
        //Gets the file name after the data generation is successful
        writer.write(tList, features, "data");
        String file = writer.generateData(tList, features);
        //uses the filename to write the kmeans output
        //writer.writeKMeansOutput(weka.KMeans(3, file));
        Instances pcaResult = weka.pca("otherdata.arff");
        weka.jScatterChart(weka.kMeansCentroids(pcaResult, 1), tList, pcaResult);       
        
    }
    
    public static void ERASEEVERYTHING(){
            String query = "TRUNCATE TABLE \"TeamMembers\", \"Issues\", "
                + "\"Features\", \"Projects\", \"Reports\";";
            ReportDAO dao = new ReportDAO();
            System.out.println("CLEANING DATABASE");
            dao.clean(query);
    }
}
