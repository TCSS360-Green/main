package GUI;

import model.CSVHandler;
import model.ProjectController;
import model.Projects;
import model.Users;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class HomePage extends JFrame {
    private JButton dashboardBtn, projectsBtn, productsBtn, addProjectBtn;
    private JTextField searchField;
    private JPanel listPanel;
    private List<Projects> projectList;
    JMenuBar menuBar;

    public HomePage(Users user) throws IOException {
        setTitle("Schoodle: Home Project And Appliance Organizer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        CSVHandler projectHandler = new CSVHandler("Schoodle/src/resources/projects.csv");
        ProjectController projectController = new ProjectController(projectHandler);
        projectList = projectController.getAllProjects(user.getUserId());

        // Create top panel with buttons and search field
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

        // Dashboard button
        dashboardBtn = new JButton("Dashboard");
        dashboardBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement dashboard view
                listPanel.removeAll();
                listPanel.revalidate();
                listPanel.repaint();
                addProjectBtn.setVisible(false);
            }
        });
        buttonPanel.add(dashboardBtn);

        /*
         * Project buttons element prep
         */
        //Create Panel for `+` and `-` buttons:
        JPanel addRemovePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addRemovePanel.setPreferredSize(new Dimension(400, 40));
        // Create "+" button to add project
        JButton addButton = new JButton("+");
        addButton.setToolTipText("Add a new project");
        addButton.setFont(new Font("Arial", Font.BOLD, 25));
        addButton.setForeground(Color.RED);
        addButton.setPreferredSize(new Dimension(40, 40));
        //Create "-" button to remove project
        JButton removeButton = new JButton("-");
        removeButton.setToolTipText("Remove selected project");
        removeButton.setFont(new Font("Arial", Font.BOLD, 25));
        removeButton.setForeground(Color.RED);
        removeButton.setPreferredSize(new Dimension(40,40));
        // Display "There is no projects to display" message
        JLabel noProjectsLabel = new JLabel("There is no projects to display");
        noProjectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Create a scrollable list of project buttons
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel scrollableList = new JPanel(new GridLayout(0, 1, 0, 10));
        
       
        // Projects button
        projectsBtn = new JButton("Projects");
        projectsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listPanel.removeAll();
                  //add + and - button 
                  addRemovePanel.add(addButton);
                  addRemovePanel.add(removeButton);
                  listPanel.add(addRemovePanel,BorderLayout.NORTH);
                
                    for (Projects project : projectList) {
                        JPanel projectButtonPanel = new JPanel(new BorderLayout());
                        JButton projectButton = new JButton(project.getName());
                        projectButton.setFont(new Font("Arial", Font.PLAIN, 16));
                        projectButton.setPreferredSize(new Dimension(400, 50));
                        projectButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                // TODO: Display project details in a separate panel
                            }
                        });
                        projectButtonPanel.add(projectButton, BorderLayout.WEST);
    
                        scrollableList.add(projectButtonPanel);
                    }
                    scrollPane.setViewportView(scrollableList);
                    listPanel.add(scrollPane);



        
  
               
                listPanel.revalidate();
                listPanel.repaint();
                
            }
        });
     

        buttonPanel.add(projectsBtn);
        
         //Add action lister for `+` button
        addButton.addActionListener(new ActionListener() {          
            public void actionPerformed(ActionEvent e) {
                JLabel nameLabel = new JLabel("Project Name:");
                JTextField nameField = new JTextField();
                JLabel budgetLabel = new JLabel("Budget:");
                JTextField budgetField = new JTextField();
                JLabel estimateCostLabel = new JLabel("Estimate Cost:");
                JTextField estimateCostField = new JTextField();
                // Create the panel with the fields
                JPanel panel = new JPanel(new GridLayout(4, 2));
                panel.add(nameLabel);
                panel.add(nameField);
                panel.add(budgetLabel);
                panel.add(budgetField);
                panel.add(estimateCostLabel);
                panel.add(estimateCostField);
                // Show the input dialog and get the result
                JFrame frame = new JFrame();
                int result = JOptionPane.showConfirmDialog(frame, panel, "Add Project", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Get the values from the fields
                    String name = nameField.getText();
                    double budget = Double.parseDouble(budgetField.getText());
                    double estimateCost =estimateCostField.getText().isEmpty() ? 0.0 : Double.parseDouble(estimateCostField.getText());
                    // Create the new project
                    Projects project;
                    try {
                        project = new Projects(projectController.getNextProjectID(),user.getUserId(),name, budget, estimateCost);
                        projectController.addProject(project);
                         // Update the projectList with the new project
                        projectList = projectController.getAllProjects(user.getUserId());
    
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                     
                }
                // Clear the current listPanel and redraw it with the updated projectList
                for (Projects project : projectList) {
                    JPanel projectButtonPanel = new JPanel(new BorderLayout());
                    JButton projectButton = new JButton(project.getName());
                    projectButton.setFont(new Font("Arial", Font.PLAIN, 16));
                    projectButton.setPreferredSize(new Dimension(400, 50));
                    projectButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // TODO: Display project details in a separate panel
                        }
                    });
                    projectButtonPanel.add(projectButton, BorderLayout.WEST);

                    scrollableList.add(projectButtonPanel);
                }
                scrollPane.setViewportView(scrollableList);
                listPanel.add(scrollPane);



    

           
            listPanel.revalidate();
            listPanel.repaint();
            
            }
        });

        //Add action lister for `-` button
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Remove project from list   
                listPanel.revalidate();
                listPanel.repaint();
            }
        });        

        // Products button
        productsBtn = new JButton("Products");
        productsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Replace draft with actual products
                listPanel.removeAll();

                // Add project list items to listPanel
                String[] productNames = {"Product 1", "Product 2", "Product 3", "Product 4", "Product 5", "Product 6", "Product 7", "Product 8", "Product 9", "Product 10"};

                // Create a scrollable list of project buttons
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                JPanel scrollableList = new JPanel(new GridLayout(0, 1, 0, 10));

                for (String productName : productNames) {
                    JButton button = new JButton(productName);
                    button.setFont(new Font("Arial", Font.PLAIN, 16));
                    button.setPreferredSize(new Dimension(400, 50));
                    button.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // TODO: Handle click on project button
                        }
                    });
                    scrollableList.add(button);
                }
                scrollPane.setViewportView(scrollableList);
                listPanel.add(scrollPane);

                listPanel.revalidate();
                listPanel.repaint();
                addProjectBtn.setVisible(false);
            }
        });
        buttonPanel.add(productsBtn);

        topPanel.add(buttonPanel, BorderLayout.WEST);

     

        topPanel.add(buttonPanel, BorderLayout.WEST);

        // Search field
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(20);
        searchField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // String searchQuery = searchField.getText();
                // TODO: Filter project or product list based on search query
            }
        });
        searchPanel.add(searchField);

        // Settings button
        JButton settingsBtn = new JButton(new ImageIcon("settings.png"));
        settingsBtn.setBorderPainted(false);
        settingsBtn.setContentAreaFilled(false);
        settingsBtn.setFocusPainted(false);
        searchPanel.add(settingsBtn);

        topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Add empty panel to represent the list of projects or products
        listPanel = new JPanel(new BorderLayout());
        // listPanel.setPreferredSize(new Dimension(600, 400));
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add panels to main window
        add(listPanel, BorderLayout.CENTER);

        //Welcome Label 
        JLabel welcomeUser = new JLabel("Welcome Back " + user.getFullName() , null, SwingConstants.CENTER);
        welcomeUser.setFont(new Font("Arial", Font.PLAIN, 30));
        listPanel.add(welcomeUser);

        menuBar = new JMenuBar();
        JMenu aboutSection = new JMenu("About");
        menuBar.add(aboutSection);
        aboutSection.addSeparator();
        this.setJMenuBar(menuBar);
        JMenuItem aboutItem = new JMenuItem("Schoodle Inc.");
        aboutSection.add(aboutItem);
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listPanel.removeAll();
                // Add project list items to listPanel
                String USER_NAME = user.getFullName();
                String TEAM_NAME = "Team Hi Chew";
                String[] TEAM_MEMBERS = {"Aaron", "Anna", "Veasna", "Ivan"};
                StringBuilder message = new StringBuilder();
                message.append("This app is registered to: ").append(USER_NAME).append("\n");
                message.append("Provided by ").append(TEAM_NAME).append("\n");
                for (String member : TEAM_MEMBERS) {
                    message.append("\t").append(member).append("\n");
                }
                JOptionPane.showMessageDialog(null, message, "Version 1.0", JOptionPane.INFORMATION_MESSAGE);

                listPanel.revalidate();
                listPanel.repaint();
                addProjectBtn.setVisible(false);
            }
        });


        setVisible(true);
    }
    
    public static void main(String[] args) throws IOException {
        new HomePage(null);
    }
}
