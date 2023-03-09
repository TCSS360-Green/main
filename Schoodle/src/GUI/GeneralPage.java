package GUI;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralPage extends JFrame implements ActionListener {
    JPanel windowPanel;
    Users user;
    private JLabel profileLabel;
    private JLabel nameLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JTextField nameText;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JTextField emailText;
    private JButton changeNameButton;
    private JButton changeUsernameButton;
    private JButton changePasswordButton;
    private JButton changeEmailButton;
    private JButton exportButton;
    private JButton importButton;


    GeneralPage(Users user) {
        this.user = user;
        initializeFrame();
        initializePanel();
        initializeLabels();
        initializeFields();
        initializeButtons();
        initializeComponentBounds();
        addComponentListeners();
        setVisible(true);
    }

    private void initializeFrame() {
        this.setTitle("Profile");
        this.setLayout(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(600,400);
    }

    private void initializeLabels() {
       profileLabel = new JLabel("Your Profile");
       profileLabel.setFont(new Font("Serif", Font.BOLD+Font.ITALIC, 14));
       profileLabel.setForeground(Color.red);
       nameLabel = new JLabel(String.format("Name: %s", user.getFullName()));
       usernameLabel = new JLabel(String.format("Username: %s", user.getUsername()));
       passwordLabel = new JLabel(String.format("Password: *******"));
       emailLabel = new JLabel(String.format("Email: %s", user.getEmail()));
       add(profileLabel);
       add(nameLabel);
       add(usernameLabel);
       add(passwordLabel);
       add(emailLabel);
    }

    private void initializeFields() {
        nameText = new JTextField(20);
        usernameText = new JTextField(20);
        passwordText = new JPasswordField(20);
        emailText = new JTextField(20);
        nameText.setSize(500, 25);
        usernameText.setSize(300, 25);
        passwordText.setSize(300, 25);
        emailText.setSize(300, 25);
        add(nameText);
        add(usernameText);
        add(passwordText);
        add(emailText);
    }

    private void initializeButtons() {
        changeNameButton = new JButton("Edit");
        changeUsernameButton = new JButton("Edit");
        changeEmailButton = new JButton("Edit");
        changePasswordButton = new JButton("Edit");
        exportButton = new JButton("Export");
        importButton = new JButton("Import");
        add(changeNameButton);
        add(changeUsernameButton);
        add(changeEmailButton);
        add(changePasswordButton);
        add(exportButton);
        add(importButton);
    }

    private void initializeComponentBounds() {
        profileLabel.setBounds(30, 10, 350, 100);
        nameLabel.setBounds(30,102,350,30);
        usernameLabel.setBounds(30, 132, 350, 30);
        passwordLabel.setBounds(30, 162, 350, 30);
        emailLabel.setBounds(30, 192, 350,30);
        nameText.setBounds(270, 105, 100, 20);
        usernameText.setBounds(270, 135, 100, 20);
        passwordText.setBounds(270, 165, 100, 20);
        emailText.setBounds(270, 195, 100, 20);
        changeNameButton.setBounds(400, 105, 100, 20);
        changeUsernameButton.setBounds(400, 135, 100, 20);
        changePasswordButton.setBounds(400, 165, 100, 20);
        changeEmailButton.setBounds(400, 195, 100, 20);
        exportButton.setBounds(30, 235, 100, 20);
        importButton.setBounds(30, 275, 100, 20);
        //importButton.setBounds()
    }

    private void addComponentListeners() {
        changeNameButton.addActionListener(this);
        changeUsernameButton.addActionListener(this);
        changePasswordButton.addActionListener(this);
        changeEmailButton.addActionListener(this);
        exportButton.addActionListener(this);
        importButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == changeNameButton) {
            String newName = nameText.getText();
            JOptionPane.showMessageDialog(null, "Name changed to " + newName);
            user.setFullName(newName);
            nameText.setText("");
            nameLabel.setText(String.format("Name: %s", user.getFullName()));
        }
        else if (e.getSource() == changeUsernameButton) {
            String newUsername = usernameText.getText();
            JOptionPane.showMessageDialog(null, "Username changed to " + newUsername);
            user.setUsername(newUsername);
            usernameText.setText("");
            usernameLabel.setText(String.format("Username: %s", user.getUsername()));
        }
        else if (e.getSource() == changePasswordButton) {
            String newPassword = passwordText.getText();
            JOptionPane.showMessageDialog(null, "Username changed to " + newPassword);
            user.setPassword(newPassword);
            passwordText.setText("");
            passwordLabel.setText(String.format("Password: *******", user.getPassword()));
        }
        else if (e.getSource() == changeEmailButton) {
            String newEmail = emailText.getText();
            JOptionPane.showMessageDialog(null, "Username changed to " + newEmail);
            user.setEmail(newEmail);
            emailText.setText("");
            emailLabel.setText(String.format("Email: %s", user.getEmail()));
        }
        else if (e.getSource() == exportButton) {
            ProfileExporter exporter = new ProfileExporter(user);
            String exportMessage = String.format("Export ID: %s", exporter.retrieveExportSettings());
            JOptionPane.showMessageDialog(null, exportMessage);
        }
        else if (e.getSource() == importButton) {
            String importID = JOptionPane.showInputDialog(null, "Enter Import ID", "Import Profile", JOptionPane.INFORMATION_MESSAGE);
            try {
                CSVHandler csvHandler = new CSVHandler("main/Schoodle/src/resources/users.csv");
                UserController userController = new UserController(csvHandler);
                String[] hashID = importID.split("!");
                int idPos = Integer.parseInt(hashID[0]);
                Users otherUser = userController.getUsers().get(idPos-1);
                if (hashID.length == 3 && idPos <= userController.getUsers().size() && hashID[1].equals(otherUser.getFullName())) {
                    int input = JOptionPane.showConfirmDialog(null, String.format("Save to current profile?\nName: %s\nEmail: %s", hashID[1], otherUser.getEmail()));
                    if (input == 0) {
                        this.user.setFullName(otherUser.getFullName());
                        this.user.setEmail(otherUser.getEmail());
                        nameLabel.setText(String.format("Name: %s", user.getFullName()));
                        emailLabel.setText(String.format("Email: %s", user.getEmail()));
                    } else {
                        return;
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(null, "Invalid Import ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void initializePanel() {
        windowPanel = new JPanel();
        windowPanel.setLayout(null);
        this.add(windowPanel);
    }



}
