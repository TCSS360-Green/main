package model;



public class Projects {
    private int userId;
    private int projectId;
    private String name;
    private double budget;
    private double estimateCost;
    

    public Projects(int projectID,int userId, String name, double budget, double estimateCost) {
        this.projectId=projectID;
        this.userId = userId;
        this.name = name;
        this.budget = budget;
        this.estimateCost = estimateCost;
       
        

    }
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
    public double getEstimateCost() {
        return estimateCost;
    }

    public void setEstimateCost(double estimateCost) {
        this.estimateCost = estimateCost;
    }
    

}