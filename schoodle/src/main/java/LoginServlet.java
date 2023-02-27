

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserController userController;

    public void init() throws ServletException {
        try {
            CSVHandler csvHandler = new CSVHandler("src/main/resources/users.csv");
            System.out.println("test");
            userController = new UserController(csvHandler);

        } catch (IOException e) {
            throw new ServletException("Error initializing servlet", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userController.login(username, password)) {
            response.sendRedirect("homepage.html");
        } else {
            response.sendRedirect("login.html");
        }
        
    }
}
