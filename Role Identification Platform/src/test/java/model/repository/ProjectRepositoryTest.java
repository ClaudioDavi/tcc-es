/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.repository;

import model.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

/**
 *
 * @author davi
 */
@Ignore
public class ProjectRepositoryTest {

    Project prj;
    ProjectRepository pRep;

    public ProjectRepositoryTest() {
    }

    @Before
    public void setUp() {
        prj = new Project();
        pRep = ProjectRepository.getInstance();
    }

    @After
    public void tearDown() {
        prj = null;
        pRep = null;
    }

   

}
