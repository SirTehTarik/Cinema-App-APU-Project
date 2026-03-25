/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package CinemaAPP;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

/**
 *
 * @author Mathew
 */
public class Homepage extends JFrame {

    // Modern color palette
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_SECONDARY = new Color(52, 73, 94);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);

    public Homepage() {
        this.setTitle("Cinema Booking System");
        this.setSize(450, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(COLOR_BG);

        // Title
        JLabel titleLabel = new JLabel("Cinema Booking System");
        titleLabel.setBounds(80, 40, 290, 35);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT);
        panel.add(titleLabel);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Welcome! Please select an option");
        subtitleLabel.setBounds(115, 80, 220, 20);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        panel.add(subtitleLabel);

        // Login Button
        JButton loginButton = createModernButton("Login", COLOR_PRIMARY);
        loginButton.setBounds(90, 140, 270, 45);
        panel.add(loginButton);

        // Register Button
        JButton registerButton = createModernButton("Register as Customer", COLOR_SECONDARY);
        registerButton.setBounds(90, 200, 270, 45);
        panel.add(registerButton);

        // Exit Button
        JButton exitButton = createModernButton("Exit", new Color(149, 165, 166));
        exitButton.setBounds(90, 260, 270, 45);
        panel.add(exitButton);

        // Action Listeners
        loginButton.addActionListener(e -> {
            // Open the Login Page
            new LoginPage();
            this.dispose(); // Close the homepage
        });

        registerButton.addActionListener(e -> {
            // Open the Customer Registration Page
            new CusRegistration().setVisible(true);
            this.dispose(); // Close the homepage
        });

        exitButton.addActionListener(e -> {
            // Exit the application
            System.exit(0);
        });

        this.add(panel);
        this.setVisible(true);
    }
    
    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
    
    public static void main(String[] args) {
        new Homepage();
    }
}
