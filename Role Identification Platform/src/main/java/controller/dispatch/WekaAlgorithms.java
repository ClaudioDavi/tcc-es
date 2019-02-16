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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.imageio.ImageIO;
import model.TeamMember;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.PrincipalComponents;
import weka.gui.beans.WekaOffscreenChartRenderer;

/**
 *
 * @author Claudio Davi
 */
public class WekaAlgorithms {

    /**
     * Method that process the data contained on file and returns Weka-Ready
     * data
     *
     * @param fileName to be accessed
     * @return Weka Instances to be processed
     */
    private Instances createData(String fileName) {

        Instances data = null;
        try {
            BufferedReader input = new BufferedReader(new FileReader(fileName));

            data = new Instances(input);

        } catch (IOException ex) {
              Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Insuficient Data for a meaningful answer", ButtonType.OK);
                    alert.showAndWait();
        //    System.out.println("FILE NOT FOUND");
       //     Logger.getLogger(WekaAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    /**
     * Simple implementation of the KMeans Clusterer Receives a number of
     * Clusters and a file to be processed It returns an integer array
     * containing the Cluster Number attached to each instance in order
     *
     * @deprecated
     * @param clusterNum
     * @param file
     * @return assigments[]
     */
    public int[] KMeans(int clusterNum, String file) {
        SimpleKMeans kmeans = new SimpleKMeans();
        int[] result = null;
        try {

            /* The seed value is used in generating a random number which is, 
            in turn, used for making the initial assignment of instances to clusters.*/
            kmeans.setSeed(10);

            kmeans.setPreserveInstancesOrder(true);
            kmeans.setNumClusters(clusterNum);

            kmeans.buildClusterer(createData(file));

            result = kmeans.getAssignments();

        } catch (Exception ex) {
            System.out.println("FAILED ON CREATING KMEANS CLUSTERER");
            Logger.getLogger(WekaAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    /**
     * Returns the Cluster centroids "only" after receiving weka Ready data.
     * This method is encouraged to use with the result of the PCA Analysis.
     *
     * @param inst
     * @param clusterNum
     * @return the Kmeans centroids
     */
    public Instances kMeansCentroids(Instances inst, int clusterNum) {
        Instances kmeansOutput = null;
        try {
            SimpleKMeans kmeans = new SimpleKMeans();

            kmeans.setSeed(10);
            kmeans.setNumClusters(clusterNum);
            kmeans.setPreserveInstancesOrder(true);
            kmeans.buildClusterer(inst);
            kmeansOutput = kmeans.getClusterCentroids();

        } catch (Exception ex) {
        //    Logger.getLogger(WekaAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return kmeansOutput;
    }

    /**
     * Return the centroids directly from file. It is recommended to use the new
     * Version of this algorithm where the results coming in are already
     * processed.
     *
     * @deprecated
     * @param clusterNum
     * @param file
     * @return
     */
    public Instances KMeansCentroids(String file, int clusterNum) {
        SimpleKMeans kmeans = new SimpleKMeans();
        Instances inst = null;
        try {

            /* The seed value is used in generating a random number which is, 
            in turn, used for making the initial assignment of instances to clusters.*/
            kmeans.setSeed(10);

            kmeans.setPreserveInstancesOrder(true);
            kmeans.setNumClusters(clusterNum);
            Instances data = createData(file);
            kmeans.buildClusterer(data);

            inst = kmeans.getClusterCentroids();

            System.out.println("Kmeans centroids: " + kmeans.getClusterCentroids().toSummaryString());
        } catch (Exception ex) {
            System.out.println("FAILED ON CREATING KMEANS CLUSTERER");
            Logger.getLogger(WekaAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inst;
    }

    /**
     * PCA algorithm, It converts a set of possibly correlated features into a
     * set of values possibly uncorrelated. Our implementation transforms our
     * multidimensional data into two dimensions that can be plotted.
     *
     * @param fileName
     * @return Instances Filtered after PCA analysis
     */
    public Instances pca(String fileName) {
        Instances filtered = null;
        try {
            PrincipalComponents pca = new PrincipalComponents();

            pca.setMaximumAttributes(2);
            pca.setCenterData(true);
            pca.setVarianceCovered(0.95);

            Instances data = createData(fileName);

            pca.setInputFormat(data);
            filtered = Filter.useFilter(data, pca);

        } catch (Exception ex) {
        //    Logger.getLogger(WekaAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filtered;
    }

    /**
     * Creates a scatterChart using the centroids obtained from the clustering
     * algorithm, the result of the PCA analisys, and the list of teamMembers so
     * we can feed the plot with their usernames for a better visualization.
     *
     * @param centroids
     * @param tmList
     * @param pcaResult
     * @throws IOException
     * @return the file name
     */
    public String jScatterChart(Instances centroids, List<TeamMember> tmList, Instances pcaResult) throws IOException {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries series = new XYSeries("Centroids");
        centroids.stream().forEach((i) -> {

            series.add(i.value(0), i.value(1));
        });

        result.addSeries(series);

        for (int i = 0; i < pcaResult.size(); i++) {
            XYSeries teamMember = new XYSeries(tmList.get(i).getUsername());
            teamMember.add(pcaResult.get(i).value(0), pcaResult.get(i).value(1));
            result.addSeries(teamMember);
        }

        JFreeChart scatterChart = ChartFactory.createScatterPlot(
                "Clustering Results", "Distance x", "distance y", result,
                PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = scatterChart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        Date dt = new Date();
        String reportName = dt.toString() + ".png";
        File ScatterFile = new File(reportName);

        ChartUtilities.saveChartAsPNG(ScatterFile, scatterChart, 800, 600);
        return reportName;
    }

    /**
     * Uses weka built-in chart creation. A better implementation of the scatter
     * plot creation can be seen in jScatterPlot();
     *
     * @deprecated
     * @param inst
     * @return
     * @throws Exception
     */
    public String chartGen(Instances inst) throws Exception {
        try {
            //Creating what will be used
            Date data = new Date();
            WekaOffscreenChartRenderer wkChart = new WekaOffscreenChartRenderer();
            List<Instances> listInst = new ArrayList();
            listInst.add(inst);

            //Create the chart
            BufferedImage img = wkChart.renderXYScatterPlot(800, 600, listInst,
                    inst.attribute(0).name(), inst.attribute(1).name(), null);

            //write it as a file
            File outputFile = new File(data.toString() + ".png".trim());
            ImageIO.write(img, "png", outputFile);

            //returns its name
            return outputFile.getName();

        } catch (IOException ex) {
            Logger.getLogger(WekaAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
}
