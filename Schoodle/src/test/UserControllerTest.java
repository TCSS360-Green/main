package test;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import model.CSVHandler;
import model.UserController;
import model.Users;

public class UserControllerTest {
    private UserController userController;
    private CSVHandler csvHandler;
    private static final String TEST_CSV_FILE = "users.csv";
    @Before
    public void setUp() throws IOException {
        csvHandler = new CSVHandler(TEST_CSV_FILE);
        userController = new UserController(csvHandler);
    }

    @Test
    public void testAddUser() throws IOException {
        boolean result = userController.addUser("test", "testpassword", "Test User", "testuser@example.com");
        assertFalse(result);
        assertNotNull(userController.getUserByUsername("test"));
    }

    @Test
    public void testGetUserByUsername() {
        Users user = userController.getUserByUsername("test");
        assertNotNull(user);
        assertEquals("test", user.getUsername());
    }

    @Test
    public void testGetUserByNameAndEmail() {
        Users user = userController.getUserByNameAndEmail("Test User", "testuser@example.com");
        assertNotNull(user);
        assertEquals("Test User", user.getFullName());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    public void testLogin() {
        boolean result = userController.login("test", "testpassword");
        assertTrue(result);
    }


}
