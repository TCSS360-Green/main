package classes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseController {
    private static List<Expenses> expenses = new ArrayList<>();
    private static CSVHandler csvHandler = new CSVHandler("expenses.csv");

    public static void addExpense(double amount, String description, String date, int projectId) throws IOException {
        Expenses newExpense = new Expenses(projectId,amount, description, date);
        expenses.add(newExpense);
        csvHandler.writeAll(expensesToString());
    }

    public static List<Expenses> getExpensesForProject(int projectId) throws IOException {
        expenses = stringToExpenses(csvHandler.readAll());
        List<Expenses> result = new ArrayList<>();
        for (Expenses e : expenses) {
            if (e.getProjectId() == projectId) {
                result.add(e);
            }
        }
        return result;
    }

    private static List<String[]> expensesToString() {
        List<String[]> data = new ArrayList<>();
        for (Expenses e : expenses) {
            String[] values = {Double.toString(e.getAmount()), e.getDescription(), e.getDate(), Integer.toString(e.getProjectId())};
            data.add(values);
        }
        return data;
    }

    private static List<Expenses> stringToExpenses(List<String[]> data) {
        List<Expenses> expenses = new ArrayList<>();
        for (String[] values : data) {
            double amount = Double.parseDouble(values[0]);
            String description = values[1];
            String date = values[2];
            int projectId = Integer.parseInt(values[3]);
            Expenses expense = new Expenses(projectId,amount, description, date);
            expenses.add(expense);
        }
        return expenses;
    }
    public static String expensesToString(List<Expenses> expenses) {
        StringBuilder sb = new StringBuilder();
        for (Expenses expense : expenses) {
            sb.append(expense.getAmount())
              .append(",")
              .append(expense.getDescription())
              .append(",")
              .append(expense.getDate())
              .append(";");
        }
        return sb.toString();
    }
    
}
