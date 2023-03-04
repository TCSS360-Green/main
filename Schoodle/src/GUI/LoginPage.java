package GUI;
import javax.swing.*;

import model.CSVHandler;
import model.UserController;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginPage extends JFrame {
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;

    public LoginPage() throws IOException {
        super("Schoodle");

        // Set layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

         
         
        // Title label
        titleLabel = new JLabel("Welcome to Schoodle");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Username label and field
        usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(usernameField, gbc);

        // Password label and field
        passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        // Login and sign up buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(buttonsPanel, gbc);

        
        signUpButton = new JButton("Sign Up");
        loginButton = new JButton("Login");
        buttonsPanel.add(signUpButton, gbc);
        buttonsPanel.add(loginButton,gbc);
        
    
        CSVHandler csvHandler = new CSVHandler("src/resources/users.csv");
        UserController userController = new UserController(csvHandler);

         
        
    // Declare fullNameField and emailField outside of actionPerformed method
    JTextField fullNameField = new JTextField(20);
    JTextField emailField = new JTextField(20);
    JLabel fullNameLabel = new JLabel("Full Name:");
    JLabel emailLabel = new JLabel("Email:");
    JButton goBackButton = new JButton("Go Back");
    JButton submitButton = new JButton("Submit");
       
    // Add action listeners to buttons
    loginButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                boolean isValid = userController.login(username, password);
        
                if (isValid) {
                    // Close login page and open homepage GUI
                    dispose();
                    HomePage homePageGUI = new HomePage();
                } else {
                    // Display error message
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Invalid username or password",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    signUpButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            titleLabel.setText("Create an account");
            usernameField.setText("");
            passwordField.setText("");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Add Full Name field
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(fullNameLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            add(fullNameField, gbc);

            // Add Email field
            gbc.gridx = 0;
            gbc.gridy = 2;
            add(emailLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 2;
            add(emailField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            add(usernameLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 3;
            add(usernameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            add(passwordLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = 4;
            add(passwordField, gbc);

            buttonsPanel.remove(loginButton);
            buttonsPanel.remove(signUpButton);
            buttonsPanel.add(goBackButton);
            buttonsPanel.add(submitButton);
            gbc.gridx= 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            add(buttonsPanel,gbc);
            // Resize frame to fit added fields
            setSize(getWidth(), getHeight() + 80);

        }
    });

     submitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                        String fullName = fullNameField.getText();
                        String email = emailField.getText();
                        String username = usernameField.getText();
                        String password = String.valueOf(passwordField.getPassword());
                        // Create new user
                        
                        if(fullName.equals("") || email.equals("") || username.equals("") || password.equals("")){
                            JOptionPane.showMessageDialog(LoginPage.this,
                                "Please fill in all fields",
                                "Sign Up Failed",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                        else {
                        boolean isCreated = false;
                        try {
                            isCreated = userController.addUser(username, password,fullName, email);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        if(isCreated){
                            // Display success message and go back to login form
                            JOptionPane.showMessageDialog(LoginPage.this,
                                "Account created successfully.\nPlease go back to log in",
                                "Sign Up Success",
                                JOptionPane.INFORMATION_MESSAGE);
                            usernameField.setText("");
                            passwordField.setText("");
                            fullNameField.setText("");
                            emailField.setText("");

                        }
                        else{
                            // Display error message
                            JOptionPane.showMessageDialog(LoginPage.this,
                                "Username already exists",
                                "Sign Up Error",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    }
                
                
                }
            });
        
            
     goBackButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
        titleLabel.setText("Welcome to Schoodle");
        usernameField.setText("");
        passwordField.setText("");
        remove(fullNameLabel);
        remove(fullNameField);
        remove(emailLabel);
        remove(emailField);
        buttonsPanel.remove(submitButton);
        buttonsPanel.remove(goBackButton);
        buttonsPanel.add(signUpButton);
        buttonsPanel.add(loginButton);
        setSize(getWidth(), getHeight() - 60);
    
        }
        
     });
     // Set frame properties
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     pack();
     setLocationRelativeTo(null);
     setVisible(true);
    }
    

    public static void main(String[] args) throws IOException {
        new LoginPage();
    }
}
