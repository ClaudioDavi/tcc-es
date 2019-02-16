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

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.repository.ProjectRepository;

/**
 *
 * @author Claudio Davi
 */
public class ProjectController {

    private ProjectRepository pr = ProjectRepository.getInstance();
    private ObservableList observableList = FXCollections.observableArrayList();

    /**
     * Creates the list of projects that will be sent to the interface
     * @return list of project names
     */
    public ObservableList setProjectListView() {
        List<String> projNames = new ArrayList<>();

        pr.selectFromAppDB().stream().forEach((p) -> {
            projNames.add(p.getName());
        });
        observableList.setAll(projNames);
        return observableList;
    }
    /**
     * Loads all projects from JIRA and sends them to the application's database.
     */
    public void loadPrjData() {
        pr.inject();
    }
}
