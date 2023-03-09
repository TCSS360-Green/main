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


            Users user = new Users(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4]);
            this.users.add(user);
        }
    }

    public boolean addUser(String username, String password, String fullName, String email) throws IOException {
        // Check if username already exists
        for (Users user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        // Find the next available user ID
        int userId = 1;
        for (Users user : users) {
            if (user.getUserId() >= userId) {
                userId = user.getUserId() + 1;
            }
        }

        Users newUser = new Users(userId, username, password, fullName, email);
        users.add(newUser);

        List<String[]> data = new ArrayList<>();
        for (Users user : users) {
            String[] userData = {String.valueOf(user.getUserId()), user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail()};
            data.add(userData);
        }

        csvHandler.writeAll(data);

        return true;
    }

    public Users getUserByUsername(String username) {
        for (Users user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public Users getUserByNameAndEmail(String fullName, String email) {
        for (Users user : users) {
            if (user.getFullName().equals(fullName) && user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean login(String username, String password) {
        for (Users user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public List<Users> getUsers() {
        return users;
    }
}
