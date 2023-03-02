import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignUpController {
    
    @Autowired
    private UserController userController;

    @PostMapping("/signup")
    @ResponseBody
    public String signUp(@RequestParam String username, 
                         @RequestParam String password, 
                         @RequestParam String fullName,
                         HttpServletResponse response) throws ServletException, IOException {
        
        if (userController.addUser(username, password, fullName)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return "{\"success\":true,\"message\":\"User registered successfully\"}";
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return "{\"success\":false,\"message\":\"Username already exists\"}";
        }
    }
}