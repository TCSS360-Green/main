

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserController userController;

    public void init() throws ServletException {
        try {
            CSVHandler csvHandler = new CSVHandler("src/main/resources/users.csv");
            userController = new UserController(csvHandler);

        } catch (IOException e) {
            throw new ServletException("Error initializing servlet", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String action = request.getParameter("action");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String fullName = request.getParameter("fullName");
    
    if (action.equals("login")) {
        if (userController.login(username, password)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\":true}");
        } else {
            response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write("{\"success\":false,\"message\":\"Invalid username or password\"}");
        }
    } else if (action.equals("signup")) {
       
        if (userController.addUser(username, password, fullName)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\":true");
            request.getSession().setAttribute("signupSuccess", true);
            response.sendRedirect("login.html");
        } else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\":false,\"message\":\"Username already exists\"}");
            
        }
    }
}


}
