import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserController {
    
    private List<Users> users = new ArrayList<>();
    
    @Autowired
    private CSVHandler csvHandler = new CSVHandler("src/main/resources/users.csv");

    public void init() throws IOException {
        List<String[]> userData = csvHandler.readAll();

        for (String[] data : userData) {
            Users user = new Users(data[0], data[1], data[2]);
            this.users.add(user);
        }
    }

    public void save(Users user) throws IOException {
        users.add(user);

        List<String[]> data = new ArrayList<>();
        for (Users u : users) {
            String[] userData = { u.getUsername(), u.getPassword(), u.getFullName() };
            data.add(userData);
        }

        csvHandler.writeAll(data);
    }

    public boolean addUser(String username, String password, String fullName) throws IOException {
        // Check if username already exists
        for (Users user : users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }

        Users newUser = new Users(username, password, fullName);
        save(newUser);

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