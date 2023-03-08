package GUI;

import javax.swing.*;
import java.awt.*;

public class GeneralPage extends JFrame {
    JPanel windowPanel;
    private JLabel profileLabel;
    private JLabel nameLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;

    GeneralPage() {
        initializeFrame();
    }

    private void initializeFrame() {
        this.setTitle("General");
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
    }

    private void initializeLabels() {
       // profileLabel.
    }

    private void initializePanel() {
        windowPanel = new JPanel();
        windowPanel.setLayout(null);
        this.add(windowPanel);
    }



}
