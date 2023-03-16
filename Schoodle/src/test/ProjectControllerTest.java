package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.ProjectController;
import model.Projects;
import model.CSVHandler;

public class ProjectControllerTest {
    private CSVHandler csvHandler;
    private ProjectController projectController;
    private static final String TEST_CSV_FILE = "test-projects.csv";

    @Before
    public void setup() {
        csvHandler = new CSVHandler(TEST_CSV_FILE);
        projectController = new ProjectController(csvHandler);
    }

    @After
    public void tearDown() {
        File testCsvFile = new File(TEST_CSV_FILE);
        if (testCsvFile.exists()) {
            testCsvFile.delete();
        }
    }

    @Test
    public void testAddAndGetAllProjects() throws IOException {
        Projects project1 = new Projects(1, 1, "Project 1", 1000.0, 500.0);
        Projects project2 = new Projects(2, 1, "Project 2", 2000.0, 1000.0);

        projectController.addProject(project1);
        projectController.addProject(project2);

        assertEquals(2, projectController.getAllProjects(1).size());
    }

    @Test
    public void testDeleteProject() throws IOException {
        Projects project1 = new Projects(1, 1, "Project 1", 1000.0, 500.0);
        Projects project2 = new Projects(2, 1, "Project 2", 2000.0, 1000.0);

        projectController.addProject(project1);
        projectController.addProject(project2);

        projectController.deleteProject(1);

        assertEquals(1, projectController.getAllProjects(1).size());
    }

    @Test
    public void testSearchProjectByName() throws IOException {
        Projects project1 = new Projects(1, 1, "Project 1", 1000.0, 500.0);
        Projects project2 = new Projects(2, 1, "Project 2", 2000.0, 1000.0);

        projectController.addProject(project1);
        projectController.addProject(project2);

        Projects foundProject = projectController.searchProjectByName(1, "Project 1");
        assertEquals(project1.getProjectId(), foundProject.getProjectId());
        assertEquals(project1.getUserId(), foundProject.getUserId());
        assertEquals(project1.getName(), foundProject.getName());
        assertEquals(project1.getBudget(), foundProject.getBudget(), 0.0);
        assertEquals(project1.getEstimateCost(), foundProject.getEstimateCost(), 0.0);

        foundProject = projectController.searchProjectByName(1, "Non-existent Project");
        assertNull(foundProject);
    }
}
