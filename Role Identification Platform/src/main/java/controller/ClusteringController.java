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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Project;
import model.repository.FeatureRepository;
import model.repository.IssueRepository;
import model.repository.ProjectRepository;
import model.repository.TeamMemberRepository;

/**
 *
 * @author Claudio Davi
 */
public class ClusteringController implements Initializable {

    ProjectRepository prjRep = ProjectRepository.getInstance();
    ReportController rpCtrl = new ReportController();
    TeamMemberController tmCtrl = new TeamMemberController();

    @FXML
    private Button generate;
    @FXML
    private ChoiceBox algorithmChoice;
    @FXML
    private ChoiceBox projectChoice;
    @FXML
    private TextField clusterNum;

    private List<String> algoImpl = new ArrayList<>();

    private int projId;

    private ObservableList algorithmList = FXCollections.observableArrayList();
    private ObservableList projectList = FXCollections.observableArrayList();
    private List<Project> proj = new ArrayList<>();

    /**
     * Creates the list of algorithms that will appear on the interface
     *
     * @return list containing all algorithms
     */
    public ObservableList generateAlgoList() {
        algoImpl.add("K-Means");
        algoImpl.add("Coming soon");

        algorithmList.setAll(algoImpl);
        return algorithmList;
    }

    /**
     * Creates the list of projects to be chosen when creating a new report
     *
     * @return list of projects
     */
    public ObservableList generateProject() {

        List<String> projNames = new ArrayList<>();
        proj.stream().forEach((p) -> {
            projNames.add(p.getName());
        });
        projectList.setAll(projNames);

        return projectList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        proj = prjRep.selectFromAppDB();

        projectChoice.setItems(generateProject());
        algorithmChoice.setItems(generateAlgoList());

        clusterNum.setText("2");
        algorithmChoice.getSelectionModel().selectFirst();

        projectChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue observable, Number oldValue, Number newValue) {
                projId = proj.get(newValue.intValue()).getId();
            }

        });

        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (clusterNum.getText().matches("\\d+")) {
                        tmCtrl.updateAll(IssueRepository.getInstance().selectFromAppDb(),
                                TeamMemberRepository.getInstance().selectFromAppDb(),
                                FeatureRepository.getInstance().selectById(projId).getFeatures());
                        rpCtrl.newReport(Integer.parseInt(clusterNum.getText()), projId);
                    } else {

                    }
                } catch (NumberFormatException | IOException array) {
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Insuficient Data for a meaningful answer", ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });

    }

}
