package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectController {
    private CSVHandler csvHandler;

    public ProjectController(CSVHandler csvHandler) {
        this.csvHandler = csvHandler;
    }
    public int getNextProjectID() throws IOException {
        List<String[]> data = csvHandler.readAll();
        int maxProjectID = 999; // start projectID at 1000
        for (String[] row : data) {
            int currentId = Integer.parseInt(row[0]);
            if (currentId > maxProjectID) {
                maxProjectID = currentId;
            }
        }
        return maxProjectID + 1;
    }
    public void addProject(Projects project) throws IOException {
        List<String[]> data = csvHandler.readAll();
        String[] newProject = { Integer.toString(project.getProjectId()), Integer.toString(project.getUserId()), project.getName(), Double.toString(project.getBudget()),Double.toString(project.getEstimateCost())};
        data.add(newProject);
        csvHandler.writeAll(data);
    }
    

    public void deleteProject(int projectID) throws IOException {
        List<String[]> data = csvHandler.readAll();

        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            int currentId = Integer.parseInt(row[0]);

            if (currentId == projectID) {
                data.remove(i);
                csvHandler.writeAll(data);
                return;
            }
        } 
         
    }

    public void updateProject(Projects project) throws IOException {
        List<String[]> data = csvHandler.readAll();
    
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            int currentId = Integer.parseInt(row[0]);
    
            if (currentId == project.getProjectId()) {
                String[] updatedProject = { Integer.toString(project.getProjectId()), Integer.toString(project.getUserId()), project.getName(), Double.toString(project.getBudget()), Double.toString(project.getEstimateCost()) };
                data.set(i, updatedProject);
                csvHandler.writeAll(data);
                return;
            }
        }
    }
    
    public Projects searchProjectByName(int userid,String name) throws IOException {
        List<Projects> projects = getAllProjects(userid);
        for (Projects project : projects) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        return null;
    }
    
    public List<Projects> getAllProjects(int userId) throws IOException {
        List<String[]> projectData = null;
        try {
            projectData = csvHandler.readAll();
        } catch (IOException e) {
            // Handle IO exception
            e.printStackTrace();
        }
    
        List<Projects> projects = new ArrayList<>();
    
        if (projectData != null) {
            for (String[] projectFields : projectData) {
                int projectID = Integer.parseInt(projectFields[0]);
                int userID = Integer.parseInt(projectFields[1]);
                String name = projectFields[2];
                double budget = Double.parseDouble(projectFields[3]);
                double estimateCost = Double.parseDouble(projectFields[4]);
               
    
                if (userID == userId) {
                    Projects project = new Projects(projectID, userID, name, budget,estimateCost);
                    projects.add(project);
                }
            }
        }
    
        return projects;
    }
    
    
    
}
