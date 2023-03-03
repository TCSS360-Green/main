package classes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectController {
    private CSVHandler csvHandler;

    public ProjectController(CSVHandler csvHandler) {
        this.csvHandler = csvHandler;
    }

    public void addProject(int id, String name, String startDate, String endDate, double budget, List<Expenses> expenses) throws IOException {
        List<String[]> data = csvHandler.readAll();
        int maxId = 0;

        for (String[] row : data) {
            int currentId = Integer.parseInt(row[0]);

            if (currentId > maxId) {
                maxId = currentId;
            }
        }

        String[] newProject = { Integer.toString(maxId + 1), name, startDate, endDate, Double.toString(budget), ExpenseController.expensesToString(expenses) };
        data.add(newProject);
        csvHandler.writeAll(data);
    }

    public void deleteProject(int id) throws IOException {
        List<String[]> data = csvHandler.readAll();

        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            int currentId = Integer.parseInt(row[0]);

            if (currentId == id) {
                data.remove(i);
                csvHandler.writeAll(data);
                return;
            }
        }
    }

    public void updateProject(int id, String name, String startDate, String endDate, double budget, List<Expenses> expenses) throws IOException {
        List<String[]> data = csvHandler.readAll();

        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            int currentId = Integer.parseInt(row[0]);

            if (currentId == id) {
                String[] updatedProject = { Integer.toString(id), name, startDate, endDate, Double.toString(budget), ExpenseController.expensesToString(expenses) };
                data.set(i, updatedProject);
                csvHandler.writeAll(data);
                return;
            }
        }
    }
    public Projects searchProjectByName(String name) throws IOException {
        List<Projects> projects = getAllProjects();
        for (Projects project : projects) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        return null;
    }
    public List<Projects> getAllProjects() throws IOException {
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
                int id = Integer.parseInt(projectFields[0]);
                String name = projectFields[1];
                String startDate = projectFields[2];
                String endDate = projectFields[3];
                double budget = Double.parseDouble(projectFields[4]);
                List<Expenses> expenses = ExpenseController.getExpensesForProject(id);
    
                Projects project = new Projects(id, name, startDate, endDate, budget, expenses);
                projects.add(project);
            }
        }
    
        return projects;
    }
    
    
}
