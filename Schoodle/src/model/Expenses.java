package model;
public class Expenses {
    private int projectId;
    private double amount;
    private String description;
    private String date;

    public Expenses(int projectId, double amount, String description, String date) {
        this.projectId = projectId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public String toString() {
    return "Amount: " + amount + "\n" +
           "Description: " + description + "\n" +
           "Date: " + date;
}

}
