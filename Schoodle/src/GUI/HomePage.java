package GUI;

import model.CSVHandler;
import model.ProjectController;
import model.Projects;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class HomePage extends JFrame {
    private JButton dashboardBtn, projectsBtn, productsBtn, addProjectBtn;
    private JTextField searchField;
    private JPanel listPanel;
    private List<Projects> projectList;

    public HomePage() throws IOException {
        setTitle("Business Expense Tracker");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        CSVHandler projectHandler = new CSVHandler("src/resources/projects.csv");
        ProjectController projectController = new ProjectController(projectHandler);
        projectList = projectController.getAllProjects();

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

        // Projects button
        projectsBtn = new JButton("Projects");
        projectsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listPanel.removeAll();

                // Create a scrollable list of project buttons
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                JPanel scrollableList = new JPanel(new GridLayout(0, 1, 0, 10));

                for (Projects project : projectList) {
                    JPanel projectButtonPanel = new JPanel(new BorderLayout());
                    JButton projectButton = new JButton(project.getName());
                    projectButton.setFont(new Font("Arial", Font.PLAIN, 16));
                    projectButton.setPreferredSize(new Dimension(400, 50));
                    projectButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // TODO: Handle click on project button
                        }
                    });
                    projectButtonPanel.add(projectButton, BorderLayout.WEST);

                    // Create "-" button to remove project
                    JButton removeButton = new JButton("-");
                    removeButton.setFont(new Font("Arial", Font.BOLD, 16));
                    removeButton.setForeground(Color.RED);
                    removeButton.setPreferredSize(new Dimension(50, 50));
                    removeButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            // Remove project from list
                            scrollableList.remove(projectButtonPanel);
                            listPanel.revalidate();
                            listPanel.repaint();
                        }
                    });
                    projectButtonPanel.add(removeButton, BorderLayout.EAST);

                    scrollableList.add(projectButtonPanel);
                }
                scrollPane.setViewportView(scrollableList);
                listPanel.add(scrollPane);

                listPanel.revalidate();
                listPanel.repaint();
                addProjectBtn.setVisible(true);
            }
        });
        buttonPanel.add(projectsBtn);

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

       // Add project button
       addProjectBtn = new JButton("Add Project");
       addProjectBtn.setVisible(false);
       addProjectBtn.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               // TODO: Need a complete project
               String projectName = JOptionPane.showInputDialog(null, "Enter project name:");
               if (projectName != null && !projectName.isEmpty()) {
                   try {
                       // Random add, need fix
                       projectController.addProject(0, projectName, "01032023", "01062023", 0.0, null);
                   } catch (IOException ex) {
                       throw new RuntimeException(ex);
                   }
                   // Add new project to listPanel
                   JLabel label = new JLabel(projectName);
                   label.setFont(new Font("Arial", Font.PLAIN, 16));
                   listPanel.add(label);
                   listPanel.revalidate();
                   listPanel.repaint();
               }
           }
       });
       buttonPanel.add(addProjectBtn, BorderLayout.EAST);

        topPanel.add(buttonPanel, BorderLayout.WEST);

        // Search field
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(20);
        searchField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText();
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


        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new HomePage();
    }
}
