package classes;

import java.util.List;

public class Projects {
    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private double budget;
    private List<Expenses> expenses;

    public Projects(int id, String name, String startDate, String endDate, double budget, List<Expenses> expenses) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.expenses = expenses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public List<Expenses> getExpenses() {
        return expenses;
    }

    public void addExpense(Expenses expense) {
        expenses.add(expense);
    }

    public double getTotalExpenses() {
        double totalExpenses = 0;
        for (Expenses expense : expenses) {
            totalExpenses += expense.getAmount();
        }
        return totalExpenses;
    }

    public double getRemainingBudget() {
        return budget - getTotalExpenses();
    }
}
