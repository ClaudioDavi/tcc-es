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
package view;

import controller.ClusteringController;
import controller.FeatureController;
import controller.IssueController;
import controller.ProjectController;
import controller.ReportController;
import controller.TeamMemberController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Claudio Davi
 */
public class RIPPrototypeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button generateReport;
    @FXML
    private MenuItem loadJiraData;
    @FXML
    private MenuItem about;
    @FXML
    private MenuItem quit;
    @FXML
    private MenuItem settings;
    @FXML
    private ImageView reportImg;

    @FXML
    private ListView teamMemberList;
    @FXML
    private ListView projectList;
    @FXML
    private ListView reportsList;

    private ProjectController prjCtrl = new ProjectController();
    private TeamMemberController tmCtrl = new TeamMemberController();
    private IssueController isCtrl = new IssueController();
    private FeatureController ftCtrl = new FeatureController();
    private ReportController rptCtrl = new ReportController();
    private ClusteringController clsCtrl = new ClusteringController();
    private String reportFile = "file:Tue Oct 25 16:23:13 BRST 2016.png";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//
//        Image report = new Image(reportFile, 400, 300, true, true);
//        reportImg.setImage(report);
        reportsList.setItems(rptCtrl.setReportsListView());
        teamMemberList.setItems(tmCtrl.setTeamMemberView());
        projectList.setItems(prjCtrl.setProjectListView());

        loadJiraData.setOnAction((ActionEvent event) -> {
            /* This has to be excecuted first, Thus I'm locking the thread
            until this process is over*/

            rptCtrl.cleanBeforeNewReport();

            System.out.println("Establishing connection to JIRA Database");
            isCtrl.loadIssueData();
            prjCtrl.loadPrjData();
            tmCtrl.loadTMData();
            ftCtrl.loadFeatData();
            projectList.setItems(prjCtrl.setProjectListView());
            teamMemberList.setItems(tmCtrl.setTeamMemberView());

        });
        about.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("About the Platform: ");
            alert.setHeaderText("This Plaftorm is still in development");
            String content = "The Role Identification Platform (RIP) is "
                    + "being developed by Claudio Souza as his final "
                    + "year project for Software Engineering at Unipampa.";
            alert.setContentText(content);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(400, 320);
            alert.show();
        });
        generateReport.setOnAction((ActionEvent event) -> {
            /**
             * clsCtrl.generateData(3, "data",
             * TeamMemberRepository.getInstance().selectFromAppDb(),
             * FeatureRepository.getInstance().selectById(10000).getFeatures());
             *
             */
            FXMLLoader load = new FXMLLoader(getClass().getResource("/view/fxml/clusteringOptions.fxml"));
            Scene settings1 = null;
            try {
                settings1 = new Scene(load.load());
            } catch (IOException ex) {
                Logger.getLogger(RIPPrototypeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage setStage = new Stage();
            setStage.setScene(settings1);
            setStage.setTitle("Clustering Options");
            setStage.setResizable(false);
            setStage.showAndWait();
        });
        quit.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        settings.setOnAction((ActionEvent event) -> {
            reportsList.setItems(rptCtrl.setReportsListView());
            teamMemberList.setItems(tmCtrl.setTeamMemberView());
            projectList.setItems(prjCtrl.setProjectListView());
        });
        reportsList.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton() != MouseButton.SECONDARY) {
                reportFile = "file:" + (String) reportsList.getSelectionModel().getSelectedItem() + ".png";

                Image report = new Image(reportFile, 400, 300, true, true);
                reportImg.setImage(report);
            } else {
                
                rptCtrl.deleteReport((String)reportsList.getSelectionModel().getSelectedItem());
            }
        });

    }

}
