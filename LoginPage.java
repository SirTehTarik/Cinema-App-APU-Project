/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package CinemaAPP;

/**
 *
 * @author Mathew
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import CinemaAPP.ClerkDashboard;
import CinemaAPP.ManagerDashboard;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.*;


public class LoginPage extends JFrame {

    // Modern color palette
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_SECONDARY = new Color(149, 165, 166);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);

    public LoginPage() {
        this.setTitle("Login - Cinema System");
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(COLOR_BG);

        this.add(panel);
        placeComponents(panel);
        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        // Title
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setBounds(160, 20, 100, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT);
        panel.add(titleLabel);

        // Username Label
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(40, 70, 80, 25);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        userLabel.setForeground(COLOR_TEXT);
        panel.add(userLabel);

        // Username Text Field
        JTextField userText = new JTextField(20);
        userText.setBounds(130, 70, 200, 30);
        userText.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(userText);

        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(40, 115, 80, 25);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        passwordLabel.setForeground(COLOR_TEXT);
        panel.add(passwordLabel);

        // Password Field
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(130, 115, 200, 30);
        passwordText.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(passwordText);

        // Login Button
        JButton loginButton = createButton("Login", COLOR_PRIMARY);
        loginButton.setBounds(130, 165, 200, 35);
        panel.add(loginButton);
        
        // Go Back Button
        JButton backButton = createButton("Go Back", COLOR_SECONDARY);
        backButton.setBounds(130, 210, 200, 35);
        panel.add(backButton);
        
        // Action Listener for Back button
        backButton.addActionListener(e -> {
            new Homepage();
            this.dispose();
        });
        
        // Action Listener for Login button
        loginButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText().trim();
        String password = new String(passwordText.getPassword()).trim();
        
        boolean isAuthenticated = false;
        String userRole = "";
        
        Users loggedInUser = null;

        // 1. AUTHENTICATION: Let FileHandler 
        java.util.List<Users> allUsers = FileHandler.loadAllUsers();
        
        for (Users u : allUsers) {
            // Check if the typed credentials match this user object
            if (u.getUsername().equals(user) && u.getPassword().equals(password)) {
                isAuthenticated = true;
                userRole = u.getRole(); // Grab their role
                loggedInUser = u; //Grab the customer account
                break; // Stop searching once we find a match
            }
        }

        // 2. AUTHORISATION & LOGIN RECORDS
        if (isAuthenticated) {
            JOptionPane.showMessageDialog(panel, "Login Successful!");
            
            // ---  Save history using FileHandler ---
            FileHandler.logUserLogin(user, userRole);
            // -----------------------------------------------------------

            // Close the login window
            Window win = SwingUtilities.getWindowAncestor(loginButton);
            if (win != null) {
                win.dispose();
            }

            // Route the user to the correct dashboard based on their role
            // Using switch statement for role-based navigation
            switch (userRole.toLowerCase()) {
                case "manager":
                    new ManagerDashboard();
                    break;
                case "clerk":
                    new ClerkDashboard();
                    break;
                case "customer":
                    new CustomerDashboard(loggedInUser);
                    break;
                default:
                    JOptionPane.showMessageDialog(panel, "Unknown user role!");
                    break;
            }
            
        } else {
            // If the loop finished and isAuthenticated is still false
            JOptionPane.showMessageDialog(panel, "Invalid Username or Password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});
        

    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
}

        
        
        
        
        
        
        
        
        
        
        
      
       
   