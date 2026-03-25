/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CusRegistration extends JFrame {

    // Modern color palette
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_SECONDARY = new Color(149, 165, 166);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);

    public CusRegistration() {
        this.setTitle("Customer Registration");
        this.setSize(450, 380);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(COLOR_BG);

        // Title
        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setBounds(140, 25, 200, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT);
        panel.add(titleLabel);

        // Full Name
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 80, 100, 25);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        nameLabel.setForeground(COLOR_TEXT);
        panel.add(nameLabel);
        JTextField nameText = new JTextField();
        nameText.setBounds(160, 80, 230, 30);
        nameText.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(nameText);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 130, 100, 25);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        userLabel.setForeground(COLOR_TEXT);
        panel.add(userLabel);
        JTextField userText = new JTextField();
        userText.setBounds(160, 130, 230, 30);
        userText.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(userText);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 180, 100, 25);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        passLabel.setForeground(COLOR_TEXT);
        panel.add(passLabel);
        JPasswordField passText = new JPasswordField();
        passText.setBounds(160, 180, 230, 30);
        passText.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(passText);

        // Buttons
        JButton regBtn = createButton("Register Account", COLOR_PRIMARY);
        regBtn.setBounds(70, 250, 160, 40);
        panel.add(regBtn);

        JButton backBtn = createButton("Back to Home", COLOR_SECONDARY);
        backBtn.setBounds(240, 250, 150, 40);
        panel.add(backBtn);

        // Back Button Logic
        backBtn.addActionListener(e -> {
            new Homepage();
            this.dispose(); 
        });

        // Register Button Logic
        regBtn.addActionListener(e -> {
            String fullName = nameText.getText().trim();
            String username = userText.getText().trim();
            String password = new String(passText.getPassword()).trim();

            // Do-while loop for password validation
            int passwordAttempts = 0;
            do {
                if (password.length() >= 4) {
                    break; // Password is valid
                } else if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Password cannot be empty!", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, "Password must be at least 4 characters!", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } while (++passwordAttempts < 1);

            // Validation: Make sure no fields are left blank
            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Check if username already exists
            if (FileHandler.isUsernameExists(username)) {
                JOptionPane.showMessageDialog(this, 
                    "Username '" + username + "' already exists!\nPlease choose a different username.", 
                    "Duplicate Username", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create the user object - notice the role is hardcoded as "Customer"
            Users newCustomer = new Users(username, password, "Customer", fullName);
            
            // Save to users.txt directly
           // Save to users.txt directly using FileHandler
            FileHandler.addUser(newCustomer);

            JOptionPane.showMessageDialog(this, "Registration Successful! You can now log in.");
            new Homepage(); this.dispose(); // Close this window
        });

        this.add(panel);
        this.setVisible(true);
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
