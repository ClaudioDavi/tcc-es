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
import java.util.Locale;
import model.Feature;
import model.Issue;
import model.Project;
import model.repository.FeatureRepository;
import model.repository.IssueRepository;
import model.repository.ProjectRepository;

/**
 *
 * @author Claudio Davi
 */
public class FeatureController {

    FeatureRepository fRep = FeatureRepository.getInstance();
    IssueRepository iRep = IssueRepository.getInstance();
                        Locale l = Locale.getDefault();

    /**
     * Loads features from issues on the database.
     * Each feature list is based on the project the issues are in.
     * @return boolean 
     */
    public boolean loadFeatData() {
        ProjectRepository pr = ProjectRepository.getInstance();
        List<Feature> listFeatures = new ArrayList<>();
        for(Project p : pr.selectFromAppDB()) {
            Feature ft = new Feature();
            ft.setId(p.getId());
            for(Issue i : iRep.selectFromAppDb()) {
                if(i.getProject() == ft.getId()) {
                    ft.getFeatures().addAll(ft.filterFeature(i.getDescription().toLowerCase(l)));
                }
            }
            listFeatures.add(ft);          
        }
        listFeatures.stream().forEach((f) -> {
            fRep.insert(f);
        });
        return true;
    }
}
