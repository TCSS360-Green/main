package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserController {
    private List<Users> users;
    private CSVHandler csvHandler;

    public UserController(CSVHandler csvHandler) throws IOException {
        this.csvHandler = csvHandler;
        List<String[]> userData = csvHandler.readAll();
        this.users = new ArrayList<>();

        for (String[] data : userData) {
            Users user = new Users(data[0], data[1], data[2],data[3]);
            this.users.add(user);
        }
    }

    public boolean addUser(String username, String password, String fullName , String email) throws IOException {
        // Check if username already exists
        for (Users user : users) {
            if (user.getUsername().equals(username) ) {
                return false;
            }
        }

        Users newUser = new Users(username, password, fullName, email);
        users.add(newUser);

        List<String[]> data = new ArrayList<>();
        for (Users user : users) {
            String[] userData = { user.getUsername(), user.getPassword(), user.getFullName() ,user.getEmail() };
            data.add(userData);
        }

        csvHandler.writeAll(data);

        return true;
    }

    public boolean login(String username, String password) {
        for (Users user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
   
}
