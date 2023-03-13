package GUI;

import model.CSVHandler;
import model.ExpenseController;
import model.ProductController;
import model.Products;
import model.ProjectController;
import model.Projects;
import model.Users;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class HomePage extends JFrame {
    private JButton dashboardBtn, projectsBtn, productsBtn, addProjectBtn,addProjectButton,removeProjectButton;
    private JPanel listPanel,addRemovePanel,scrollableList;
    private JScrollPane scrollPane;
    private List<Projects> projectList;
    private List<Products> productList;
    private JMenuBar menuBar;
    private CSVHandler projectHandler,productHandler,expenseHandler;
    private ProjectController projectController;
    private ProductController productController;
    private ExpenseController expenseController;

    public HomePage(Users user) throws IOException, ParseException {
        setTitle("Schoodle: Home Project And Appliance Organizer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        projectHandler = new CSVHandler("Schoodle/src/resources/projects.csv");
        projectController = new ProjectController(projectHandler);
        projectList = projectController.getAllProjects(user.getUserId());

        productHandler = new CSVHandler("Schoodle/src/resources/products.csv");
        productController = new ProductController(productHandler);
        productList = productController.getAllProducts(user.getUserId());

        expenseHandler = new CSVHandler("Schoodle/src/resources/expenses.csv");
        expenseController = new ExpenseController(expenseHandler);
        // Create top panel with buttons and search field
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));

        // Dashboard button
        dashboardBtn = new JButton("Dashboard");
        dashboardBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement dashboard view
                listPanel.removeAll();
                //adding logo
                JLabel logo = new JLabel("", (new ImageIcon("Schoodle/src/resources/logo.png")), SwingConstants.CENTER); 
                listPanel.add(logo);
                
                listPanel.revalidate();
                listPanel.repaint();
            }
        });
        buttonPanel.add(dashboardBtn);

        /*
         * Project buttons element prep
         */
        // Create Panel for `+` and `-` buttons:
        addRemovePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addRemovePanel.setPreferredSize(new Dimension(400, 40));
        // Create "+" button to add project
        addProjectButton = new JButton("+");
        addProjectButton.setToolTipText("Add a new project");
        addProjectButton.setFont(new Font("Arial", Font.BOLD, 25));
        addProjectButton.setForeground(Color.RED);
        addProjectButton.setPreferredSize(new Dimension(40, 40));
        // Create "-" button to remove project
        removeProjectButton = new JButton("-");
        removeProjectButton.setToolTipText("Remove selected project");
        removeProjectButton.setFont(new Font("Arial", Font.BOLD, 25));
        removeProjectButton.setForeground(Color.RED);
        removeProjectButton.setPreferredSize(new Dimension(40, 40));
        // Display "There is no projects to display" message
        JLabel noProjectsLabel = new JLabel("There is no projects to display");
        noProjectsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        int viewportHeight = Math.min(10, projectList.size()) * 50; // Assuming a button height of 50 pixels
        // Create a scrollable list of project buttons
        scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableList = new JPanel();
        scrollableList.setLayout(new BoxLayout(scrollableList, BoxLayout.Y_AXIS));
        scrollableList.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Projects button
        projectsBtn = new JButton("Projects");
        projectsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listPanel.removeAll();
                // add + and - button
                addRemovePanel.add(addProjectButton);
                addRemovePanel.add(removeProjectButton);
                listPanel.add(addRemovePanel, BorderLayout.NORTH);
                    scrollableList.removeAll();
                if (projectList.isEmpty()) {
                    listPanel.add(noProjectsLabel);
                } else {
                    for (Projects project : projectList) {
                        JButton projectButton = new JButton(project.getName());
                        projectButton.setFont(new Font("Arial", Font.PLAIN, 16));
                        projectButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                        projectButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                        projectButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                JPanel detailsPanel = new JPanel(new GridLayout(6, 2));
        
        // Add project details
        JLabel nameLabel = new JLabel("Project Name:");
        JLabel nameValue = new JLabel(project.getName());
        detailsPanel.add(nameLabel);
        detailsPanel.add(nameValue);
        
        JLabel budgetLabel = new JLabel("Budget:");
        JLabel budgetValue = new JLabel(String.format("$%.2f", project.getBudget()));
        detailsPanel.add(budgetLabel);
        detailsPanel.add(budgetValue);
        
        JLabel estimateCostLabel = new JLabel("Estimate Cost:");
        JLabel estimateCostValue = new JLabel(String.format("$%.2f", project.getEstimateCost()));
        detailsPanel.add(estimateCostLabel);
        detailsPanel.add(estimateCostValue);
    
        double totalExpenses = 0.0;
        double budgetVsExpenses = 0.0;
        try {
            totalExpenses = expenseController.getTotalExpensesByProjectId(project.getProjectId());
            budgetVsExpenses = expenseController.getBudgetVsExpenses(project);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        JLabel expensesLabel = new JLabel("Total Expenses:");
        JLabel expensesValue = new JLabel(String.format("$%.2f", totalExpenses));
        detailsPanel.add(expensesLabel);
        detailsPanel.add(expensesValue);
        
        JLabel budgetVsExpensesLabel = new JLabel("Budget vs Expenses:");
        JLabel budgetVsExpensesValue = new JLabel(String.format("$%.2f", budgetVsExpenses));
        detailsPanel.add(budgetVsExpensesLabel);
        detailsPanel.add(budgetVsExpensesValue);
        
        // Add button to open ExpenseWindow
        JButton addExpenseButton = new JButton("Add Expense");
        addExpenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExpenseWindow expenseWindow = new ExpenseWindow(project);
                expenseWindow.setVisible(true);
                // Update expense details when expense window is closed
                expenseWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        updateExpenseDetails(expensesValue, budgetVsExpensesValue, project);
                    }
                });
            }
        });
        detailsPanel.add(new JLabel());
        detailsPanel.add(addExpenseButton);
        updateExpenseDetails(expensesValue, budgetVsExpensesValue, project);
        // Display details in a separate panel
        JOptionPane.showMessageDialog(null, detailsPanel, "Project Details", JOptionPane.PLAIN_MESSAGE);
    }
});
                        scrollableList.add(projectButton);
                    }  
                    scrollPane.setViewportView(scrollableList);
                    listPanel.add(scrollPane);    
                }
                listPanel.revalidate();
                listPanel.repaint();
            }
        });
        buttonPanel.add(projectsBtn);

        // Add action lister for `+` button
        addProjectButton.addActionListener(new ActionListener() {
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
                    double estimateCost = estimateCostField.getText().isEmpty() ? 0.0
                            : Double.parseDouble(estimateCostField.getText());
                    // Create the new project
                    Projects project;
                    try {
                        project = new Projects(projectController.getNextProjectID(), user.getUserId(), name, budget,
                                estimateCost);
                        projectController.addProject(project);
                        // Update the projectList with the new project
                        projectList = projectController.getAllProjects(user.getUserId());
                        // Create a new button for the newly added project
                        JButton projectButton = new JButton(project.getName());
                        projectButton.setFont(new Font("Arial", Font.PLAIN, 16));
                        projectButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                        projectButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                        projectButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                JPanel detailsPanel = new JPanel(new GridLayout(6, 2));
        
                                // Add project details
                                JLabel nameLabel = new JLabel("Project Name:");
                                JLabel nameValue = new JLabel(project.getName());
                                detailsPanel.add(nameLabel);
                                detailsPanel.add(nameValue);
                                
                                JLabel budgetLabel = new JLabel("Budget:");
                                JLabel budgetValue = new JLabel(String.format("$%.2f", project.getBudget()));
                                detailsPanel.add(budgetLabel);
                                detailsPanel.add(budgetValue);
                                
                                JLabel estimateCostLabel = new JLabel("Estimate Cost:");
                                JLabel estimateCostValue = new JLabel(String.format("$%.2f", project.getEstimateCost()));
                                detailsPanel.add(estimateCostLabel);
                                detailsPanel.add(estimateCostValue);
                            
                                double totalExpenses = 0.0;
                                double budgetVsExpenses = 0.0;
                                try {
                                    totalExpenses = expenseController.getTotalExpensesByProjectId(project.getProjectId());
                                    budgetVsExpenses = expenseController.getBudgetVsExpenses(project);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                                
                                JLabel expensesLabel = new JLabel("Total Expenses:");
                                JLabel expensesValue = new JLabel(String.format("$%.2f", totalExpenses));
                                detailsPanel.add(expensesLabel);
                                detailsPanel.add(expensesValue);
                                
                                JLabel budgetVsExpensesLabel = new JLabel("Budget vs Expenses:");
                                JLabel budgetVsExpensesValue = new JLabel(String.format("$%.2f", budgetVsExpenses));
                                detailsPanel.add(budgetVsExpensesLabel);
                                detailsPanel.add(budgetVsExpensesValue);
                                
                                // Add button to open ExpenseWindow
                                JButton addExpenseButton = new JButton("Add Expense");
                                addExpenseButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        ExpenseWindow expenseWindow = new ExpenseWindow(project);
                                        expenseWindow.setVisible(true);
                                        // Update expense details when expense window is closed
                                        expenseWindow.addWindowListener(new WindowAdapter() {
                                            @Override
                                            public void windowClosed(WindowEvent e) {
                                                updateExpenseDetails(expensesValue, budgetVsExpensesValue, project);
                                            }
                                        });
                                    }
                                });
                                detailsPanel.add(new JLabel());
                                detailsPanel.add(addExpenseButton);
                                updateExpenseDetails(expensesValue, budgetVsExpensesValue, project);
                                // Display details in a separate panel
                                JOptionPane.showMessageDialog(null, detailsPanel, "Project Details", JOptionPane.PLAIN_MESSAGE);
                            }
                        });
                        scrollableList.add(projectButton);
                        scrollPane.setViewportView(scrollableList);
                        scrollPane.getViewport().setPreferredSize(new Dimension(800, viewportHeight));
                    } catch (IOException e1) {
                        
                        System.out.println("couldnt add");
                        e1.printStackTrace();
                    }

                }
                // Update the listPanel with the scrollable list
                listPanel.removeAll();
                listPanel.add(addRemovePanel, BorderLayout.NORTH);
                listPanel.add(scrollPane);

                listPanel.revalidate();
                listPanel.repaint();

            }
        });

        // Add action listener for `-` button
        removeProjectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the list of project names
                List<String> projectNames = new ArrayList<String>();
                Component[] components = scrollableList.getComponents();
                for (Component component : components) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        projectNames.add(button.getText());
                    }
                }

                // Show a dialog to ask the user which project to remove
                String selectedProject = (String) JOptionPane.showInputDialog(
                        null,
                        "Which project would you like to remove?",
                        "Remove Project",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        projectNames.toArray(),
                        projectNames.get(0));

                // Find the project in the project list and remove it
                for (int i = 0; i < projectList.size(); i++) {
                    Projects project = projectList.get(i);
                    if (project.getName().equals(selectedProject)) {
                        try {
                            projectController.deleteProject(project.getProjectId());
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        projectList.remove(i);

                        break;
                    }
                }

                // Remove the selected project button
                Component[] listComponents = scrollableList.getComponents();
                for (Component component : listComponents) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        if (button.getText().equals(selectedProject)) {
                            scrollableList.remove(button);
                            break;
                        }
                    }
                }
                if (projectList.isEmpty()) {

                    listPanel.removeAll();
                    listPanel.add(addRemovePanel);
                    listPanel.add(noProjectsLabel);
                    listPanel.revalidate();
                    listPanel.repaint();
                } else {

                    // Revalidate and repaint the scrollableList
                    scrollableList.revalidate();
                    scrollableList.repaint();
                }
            }
        });

        // Products button

        // Create Panel for `+` and `-` buttons:
        JPanel addRemoveProductPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addRemoveProductPanel.setPreferredSize(new Dimension(400, 40));
        
        // Create "+" button to add project
        JButton addProduct = new JButton("+");
        addProduct.setToolTipText("Add a new project");
        addProduct.setFont(new Font("Arial", Font.BOLD, 25));
        addProduct.setForeground(Color.RED);
        addProduct.setPreferredSize(new Dimension(40, 40));
        // Create "-" button to remove project
        JButton removeProduct = new JButton("-");
        removeProduct.setToolTipText("Remove selected project");
        removeProduct.setFont(new Font("Arial", Font.BOLD, 25));
        removeProduct.setForeground(Color.RED);
        removeProduct.setPreferredSize(new Dimension(40, 40));
        //no label
        // Display "There is no projects to display" message
        JLabel noProductLabel = new JLabel("There is no products to display");
        noProductLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Create a scrollable list of project buttons
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel scrollableList2 = new JPanel();
        scrollableList2.setLayout(new BoxLayout(scrollableList2, BoxLayout.Y_AXIS));
        scrollableList2.setAlignmentX(Component.LEFT_ALIGNMENT);


        productsBtn = new JButton("Products");
        productsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                listPanel.removeAll();
                addRemoveProductPanel.add(addProduct);
                addRemoveProductPanel.add(removeProduct);
                listPanel.add(addRemoveProductPanel, BorderLayout.NORTH);

                scrollableList2.removeAll();
                if (productList.isEmpty()) {
                    listPanel.add(noProductLabel);
                } else {
                    for (Products product : productList) {
                        JButton productButton = new JButton(product.getProductName());
                        productButton.setFont(new Font("Arial", Font.PLAIN, 16));
                        productButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                        productButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                        productButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                JPanel detailsPanel = new JPanel(new GridLayout(3, 2));
        
                                // Add project details
                                JLabel nameLabel = new JLabel("Product Name:");
                                JLabel nameValue = new JLabel(product.getProductName());
                                detailsPanel.add(nameLabel);
                                detailsPanel.add(nameValue);
                                
                                JLabel priceLabel = new JLabel("Price:");
                                JLabel   priceValue = new JLabel(String.format("$%.2f", product.getPrice()));
                                detailsPanel.add(priceLabel);
                                detailsPanel.add(priceValue);
                                
                                JLabel warrantyDateLabel = new JLabel("Warranty Date:");
                                JLabel warrantyDateValue = new JLabel(product.getWarrantyDate());
                                detailsPanel.add(warrantyDateLabel);
                                detailsPanel.add(warrantyDateValue);
                            
                               
                               

                                JOptionPane.showMessageDialog(null, detailsPanel, "Product Details", JOptionPane.PLAIN_MESSAGE);
                            }
                        });
                        scrollableList2.add(productButton);
                    }
                    
                    scrollPanel.setViewportView(scrollableList2);
                    listPanel.add(scrollPanel);
                    
                }
                listPanel.revalidate();
                listPanel.repaint();
            }
        });
        buttonPanel.add(productsBtn);
        topPanel.add(buttonPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.WEST);


         // Add action lister for `+` button
         addProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JLabel nameLabel = new JLabel("Product Name:");
                JTextField nameField = new JTextField();
                JLabel priceLabel = new JLabel("Price:");
                JTextField priceField = new JTextField();
                JLabel dateLabel = new JLabel("Warranty Date (mm/dd/yyyy): ");
                JTextField dateField = new JTextField();
                // Create the panel with the fields
                JPanel panel = new JPanel(new GridLayout(4, 2));
                panel.add(nameLabel);
                panel.add(nameField);
                panel.add(priceLabel);
                panel.add(priceField);
                panel.add(dateLabel);
                panel.add(dateField);
                // Show the input dialog and get the result
                JFrame frame = new JFrame();
                int result = JOptionPane.showConfirmDialog(frame, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    // Get the values from the fields
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    String warrantyDate = dateField.getText();
                    
                    
                    // Create the new project
                    Products product;
                    try {
                        product = new Products(productController.getNextProductID(), user.getUserId(), name, price,warrantyDate);
                        productController.addProduct(product);
                       
                        productList = productController.getAllProducts(user.getUserId());
                        // Create a new button for the newly added project
                        JButton producButton = new JButton(product.getProductName());
                        producButton.setFont(new Font("Arial", Font.PLAIN, 16));
                        producButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                        producButton.setAlignmentX(Component.LEFT_ALIGNMENT);
                        producButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                JPanel detailsPanel = new JPanel(new GridLayout(3, 2));
        
                                // Add project details
                                JLabel nameLabel = new JLabel("Product Name:");
                                JLabel nameValue = new JLabel(product.getProductName());
                                detailsPanel.add(nameLabel);
                                detailsPanel.add(nameValue);
                                
                                JLabel priceLabel = new JLabel("Price:");
                                JLabel   priceValue = new JLabel(String.format("$%.2f", product.getPrice()));
                                detailsPanel.add(priceLabel);
                                detailsPanel.add(priceValue);
                                
                                JLabel warrantyDateLabel = new JLabel("Warranty Date:");
                                JLabel warrantyDateValue = new JLabel(product.getWarrantyDate());
                                detailsPanel.add(warrantyDateLabel);
                                detailsPanel.add(warrantyDateValue);
                            
                               
                               

                                JOptionPane.showMessageDialog(null, detailsPanel, "Product Details", JOptionPane.PLAIN_MESSAGE);
                            }
                        });
                        scrollableList2.add(producButton);
                        scrollPanel.setViewportView(scrollableList2);
                        scrollPanel.getViewport().setPreferredSize(new Dimension(800, viewportHeight));
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        System.out.println("couldnt add");
                        e1.printStackTrace();
                    }

                }
                // Update the listPanel with the scrollable list
                listPanel.removeAll();
                listPanel.add(addRemoveProductPanel, BorderLayout.NORTH);
                listPanel.add(scrollPanel);

                listPanel.revalidate();
                listPanel.repaint();

            }
        });
        // Add action listener for `-` button
        removeProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the list of project names
                List<String> productNames = new ArrayList<String>();
                Component[] components = scrollableList2.getComponents();
                for (Component component : components) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        productNames.add(button.getText());
                    }
                }

                // Show a dialog to ask the user which project to remove
                String selectedProduct = (String) JOptionPane.showInputDialog(
                        null,
                        "Which product would you like to remove?",
                        "Remove Product",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        productNames.toArray(),
                        productNames.get(0));

                // Find the project in the project list and remove it
                for (int i = 0; i < productList.size(); i++) {
                    Products product = productList.get(i);
                    if (product.getProductName().equals(selectedProduct)) {
                        try {
                            productController.deleteProduct(product.getUserId());
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        productList.remove(i);

                        break;
                    }
                }

                // Remove the selected project button
                Component[] listComponents = scrollableList2.getComponents();
                for (Component component : listComponents) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        if (button.getText().equals(selectedProduct)) {
                            scrollableList2.remove(button);
                            break;
                        }
                    }
                }
                if (productList.isEmpty()) {

                    listPanel.removeAll();
                    listPanel.add(addRemoveProductPanel);
                    listPanel.add(noProductLabel);
                    listPanel.revalidate();
                    listPanel.repaint();
                } else {

                    // Revalidate and repaint the scrollableList
                    scrollableList2.revalidate();
                    scrollableList2.repaint();
                }
            }
        });
        // Logout field
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        
        JButton logOutBtn = new JButton("Log Out");
        logOutBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        
            // Open the LoginPage GUI
            LoginPage loginPage=null;
            try {
                loginPage = new LoginPage();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            loginPage.setVisible(true);
             }
        });
        searchPanel.add(logOutBtn);
        // Settings button
        JButton settingsBtn = new JButton(new ImageIcon("settings.png"));
        settingsBtn.setBorderPainted(false);
        settingsBtn.setContentAreaFilled(false);
        settingsBtn.setFocusPainted(false);
        // searchPanel.add(settingsBtn);

       topPanel.add(searchPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Add empty panel to represent the list of projects or products
        listPanel = new JPanel(new BorderLayout());
        // listPanel.setPreferredSize(new Dimension(600, 400));
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add panels to main window
        add(listPanel, BorderLayout.CENTER);

        // Welcome Label
        JLabel welcomeUser = new JLabel("Welcome Back " + user.getFullName(), null, SwingConstants.CENTER);
        welcomeUser.setFont(new Font("Arial", Font.PLAIN, 30));
        listPanel.add(welcomeUser);

        menuBar = new JMenuBar();
        JMenu settingsSection = new JMenu("Settings");
        menuBar.add(settingsSection);
        settingsSection.addSeparator();
        this.setJMenuBar(menuBar);
        JMenuItem generalItem = new JMenuItem("General");
        JMenuItem aboutItem = new JMenuItem("About Schoodle Inc.");
        settingsSection.add(generalItem);
        settingsSection.add(aboutItem);

        generalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GeneralPage(user);
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listPanel.removeAll();
                // Add project list items to listPanel
                String USER_NAME = user.getFullName();
                String TEAM_NAME = "Team Hi Chew";
                String[] TEAM_MEMBERS = { "Aaron", "Anna", "Veasna", "Ivan" };
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
    private void updateExpenseDetails(JLabel expensesValue, JLabel budgetVsExpensesValue, Projects project) {
        double totalExpenses = 0.0;
        double budgetVsExpenses = 0.0;
        try {
            totalExpenses = expenseController.getTotalExpensesByProjectId(project.getProjectId());
            budgetVsExpenses = expenseController.getBudgetVsExpenses(project);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
        expensesValue.setText(String.format("$%.2f", totalExpenses));
        budgetVsExpensesValue.setText(String.format("$%.2f", budgetVsExpenses));
    }
}
