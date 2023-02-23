import static org.junit.Assert.assertEquals;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class userTest {
  
    private static User testUser;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        
        // Initialize the test user with some data
        testUser = new User(0, "testuser", "testpassword");
    }
    
    @Before
    public void setUp() throws Exception {
        // Add the test user to the database before each test
        testUser.addUserToDatabase();
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Clean up by deleting the test user from the database
        String sql = "DELETE FROM users WHERE userID=?";
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoodleDB", "root", "");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, testUser.getUserID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void testGetUserFromDatabase() {
        // Test that the user can be retrieved from the database
        User user = User.getUserFromDatabase(testUser.getUsername());
       
        assertEquals(testUser.getUsername(), user.getUsername());
        assertEquals(testUser.getPassword(), user.getPassword());
    }
    
}
