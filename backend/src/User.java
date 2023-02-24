

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class User {
    private int userID;
    private String username;
    private String password;
    
     //DB configuration
     private static final String DB_URL = "jdbc:mysql://localhost:3306/schoodleDB";
     private static final String DB_USER = "root";
     private static final String DB_PASS = "";

    // Constructor
    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void addUserToDatabase() {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
    
        try (Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
    
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                this.userID = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    
    public static User getUserFromDatabase(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS)) {
             PreparedStatement pstmt = conn.prepareStatement(sql); 
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery(); 
            
            if (rs.next()) {
                User user = new User(rs.getInt("userID"), rs.getString("username"), rs.getString("password"));
                System.out.println("Retrieved user from database: " + user);
                return user;
            }
        }  catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        
        return null;
    }
    public static void main(String[] args) {
        User newUser = new User(0, "king", "macth2023");
  
        newUser.addUserToDatabase();
        System.out.println("New user added to the database.");
    }
}