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
package controller;

import controller.dispatch.ArffWriter;
import controller.dispatch.WekaAlgorithms;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Report;
import model.TeamMember;
import model.repository.FeatureRepository;
import model.repository.ReportRepository;
import model.repository.TeamMemberRepository;
import weka.core.Instances;

/**
 *
 * @author Claudio Souza
 */
public class ReportController {

    ReportRepository rptRep = ReportRepository.getInstance();
    private ObservableList observableList = FXCollections.observableArrayList();
    WekaAlgorithms weka = new WekaAlgorithms();
    ArffWriter writer = new ArffWriter();
    TeamMemberRepository tRep = TeamMemberRepository.getInstance();
    FeatureRepository ftRep = FeatureRepository.getInstance();

    public ObservableList setReportsListView() {
        List<String> reportNames = new ArrayList<>();
        for (Report rep : rptRep.selectAll()) {
            reportNames.add(rep.getReportName().substring(0, rep.getReportName().length() - 4));
        }
        observableList.setAll(reportNames);
        return observableList;
    }

    public void newReport(int clusterNum, int projId) throws FileNotFoundException, IOException {
        /* TeamMember List */
        List<TeamMember> tList = tRep.selectFromAppDb();

        tRep.updateAll(tRep.selectFromAppDb());
        /* Feature List */
        List<String> features = ftRep.selectById(projId).getFeatures();
        /* date used for identifying the report */
        Date dt = new Date();
        /*name of the dataset */
        String dataSetName = "dataset " + dt.toString();
        /*write dataset*/
        writer.write(tList, features, dataSetName);
        String file = writer.generateData(tList, features);
        Instances pcaResult = weka.pca(file);
        String reportName = weka.jScatterChart(weka.kMeansCentroids(pcaResult, clusterNum), tList, pcaResult);
        Report newReport = new Report();
        newReport.setReportName(reportName);

        rptRep.insert(newReport);
    }

    public void cleanBeforeNewReport() {
        rptRep.clean();
    }

    public void deleteReport(String report) {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "YOU ARE ABOUT TO DELETE REPORT: " + report + "\n ARE YOU SURE? \n", ButtonType.CANCEL, ButtonType.YES);
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.isPresent() && answer.get() == ButtonType.YES) {
            rptRep.delete(report);
        }
    }
}
