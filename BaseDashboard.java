/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * BaseDashboard class - parent class for all dashboards
 * 
 * @author Mathew
 */
public abstract class BaseDashboard extends JFrame {
    
    protected JPanel panel;
    protected GridBagConstraints gbc;
    
    // Color constants
    protected static final Color COLOR_BG = new Color(245, 247, 250);
    protected static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    protected static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    protected static final Color COLOR_INFO = new Color(52, 152, 219);
    protected static final Color COLOR_WARNING = new Color(241, 196, 15);
    protected static final Color COLOR_SECONDARY = new Color(149, 165, 166);
    protected static final Color COLOR_TEXT = new Color(44, 62, 80);
    
    // Constructor
    public BaseDashboard(String title) {
        this.setTitle(title);
        this.setSize(500, 550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(COLOR_BG);
        
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_BG);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 20, 8, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    }
    // Abstract method - must be implemented by child classes
    
    protected abstract void setupButtons();
    
    protected JButton addButton(String text, int row) {
        return addButton(text, row, COLOR_PRIMARY);
    }
    
    protected JButton addButton(String text, int row, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new java.awt.Dimension(280, 40));
        gbc.gridy = row;
        panel.add(button, gbc);
        return button;
    }
    // Method to display the dashboard
    
    public void display() {
        setupButtons();
        this.add(panel);
        this.setVisible(true);
    }
    // Method to logout
    
    protected void logout() {
        new Homepage();
        this.dispose();
    }
}
