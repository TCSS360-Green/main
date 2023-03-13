package model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseController {
    private CSVHandler csvHandler;
    
    
    public ExpenseController(CSVHandler csvHandler) {
        this.csvHandler = csvHandler;
    }
    public void addExpense(int projectId,Expenses addExpense) throws IOException {
        List<String[]> data = csvHandler.readAll();
        String[] newExpense = { Integer.toString(projectId),Double.toString(addExpense.getAmount()),addExpense.getDescription(),
        addExpense.getDate() };
        data.add(newExpense);
        csvHandler.writeAll(data);
    }
    public List<Expenses> getExpensesByProjectId(int projectId) throws IOException {
        List<String[]> data = csvHandler.readAll();
        List<Expenses> expenses = new ArrayList<>();
        for (String[] row : data) {
            if (Integer.parseInt(row[0]) == projectId) {
                Expenses expense = new Expenses(projectId,Double.parseDouble(row[1]), row[2], row[3]);
                expenses.add(expense);
            }
        }
        return expenses;
    }
    public double getTotalExpensesByProjectId(int projectId) throws IOException {
        List<String[]> data = csvHandler.readAll();
        double total = 0.0;
        for (String[] row : data) {
            if (Integer.parseInt(row[0]) == projectId) {
                total += Double.parseDouble(row[1]);
            }
        }
        return total;
    }
    public double getBudgetVsExpenses(Projects project) throws IOException {
        double totalExpenses = getTotalExpensesByProjectId(project.getProjectId());
        double budget = project.getBudget();
        double difference = budget - totalExpenses;
        return difference;
    }
        
    


    
}
