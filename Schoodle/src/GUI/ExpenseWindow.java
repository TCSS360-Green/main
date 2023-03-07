package GUI;

import javax.swing.*;

import model.ExpenseController;
import model.Projects;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseWindow extends JDialog {
    private final JTextField amountField;
    private final JTextField descriptionField;
    private final JTextField dateField;

    public ExpenseWindow(Projects project) {
        setTitle("Add Expense");
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        JLabel dateLabel = new JLabel("Date:");
        dateField = new JTextField();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(okButton);
        panel.add(cancelButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountText = amountField.getText();
                String description = descriptionField.getText();
                String date = dateField.getText();

                if (amountText.isEmpty() || description.isEmpty() || date.isEmpty()) {
                    JOptionPane.showMessageDialog(ExpenseWindow.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    double amount = Double.parseDouble(amountText);
                    try {
                        ExpenseController.addExpense(project.getProjectId(),amount, description, date);
                        dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ExpenseWindow.this, "An error occurred while adding the expense.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(panel);
        pack();
        setLocationRelativeTo(null);
    }
}
