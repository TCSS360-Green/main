package test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.CSVHandler;
import model.ExpenseController;
import model.Expenses;
import model.Projects;

public class ExpenseControllerTest {
    private CSVHandler csvHandler;
    private ExpenseController expenseController;
    private static final String TEST_CSV_FILE = "test-expenses.csv";
    @Before
    public void setup() {
        csvHandler = new CSVHandler(TEST_CSV_FILE);
        this.expenseController = new ExpenseController(csvHandler);
    }
    @After
    public void tearDown() {
        File testCsvFile = new File(TEST_CSV_FILE);
        if (testCsvFile.exists()) {
            testCsvFile.delete();
        }
    }
    
    @Test
    public void testAddExpense() throws IOException {
        Expenses expense = new Expenses(1, 100.0, "Lunch with clients", "2022-03-15");
        expenseController.addExpense(1, expense);
        List<Expenses> expenses = expenseController.getExpensesByProjectId(1);
        assertEquals(1, expenses.size());
        assertEquals(100.0, expenses.get(0).getAmount(), 0.0);
        assertEquals("Lunch with clients", expenses.get(0).getDescription());
        assertEquals("2022-03-15", expenses.get(0).getDate());
    }
    
    @Test
    public void testGetExpensesByProjectId() throws IOException {
        Expenses expense1 = new Expenses(1, 100.0, "Lunch with clients", "2022-03-15");
        Expenses expense2 = new Expenses(1, 50.0, "Office supplies", "2022-03-16");
        expenseController.addExpense(1, expense1);
        expenseController.addExpense(1, expense2);
        List<Expenses> expenses = expenseController.getExpensesByProjectId(1);
        assertEquals(2, expenses.size());
        assertEquals(100.0, expenses.get(0).getAmount(), 0.0);
        assertEquals("Lunch with clients", expenses.get(0).getDescription());
        assertEquals("2022-03-15", expenses.get(0).getDate());
        assertEquals(50.0, expenses.get(1).getAmount(), 0.0);
        assertEquals("Office supplies", expenses.get(1).getDescription());
        assertEquals("2022-03-16", expenses.get(1).getDate());
    }
    
    @Test
    public void testGetTotalExpensesByProjectId() throws IOException {
        Expenses expense1 = new Expenses(1, 100.0, "Lunch with clients", "2022-03-15");
        Expenses expense2 = new Expenses(1, 50.0, "Office supplies", "2022-03-16");
        expenseController.addExpense(1, expense1);
        expenseController.addExpense(1, expense2);
        double total = expenseController.getTotalExpensesByProjectId(1);
        assertEquals(150.0, total, 0.0);
    }
    
    @Test
    public void testGetBudgetVsExpenses() throws IOException {
        Projects project = new Projects(1,1, "Project A", 500.0,450.0);
        Expenses expense1 = new Expenses(1, 100.0, "Lunch with clients", "2022-03-15");
        Expenses expense2 = new Expenses(1, 50.0, "Office supplies", "2022-03-16");
        expenseController.addExpense(1, expense1);
        expenseController.addExpense(1, expense2);
        double difference = expenseController.getBudgetVsExpenses(project);
        assertEquals(350.0, difference, 0.0);
    }
    
}
