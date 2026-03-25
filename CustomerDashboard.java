/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 *
 * @author Mathew
 */
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDashboard extends JFrame {
    
    private Users currentUser;
    
    // Modern color palette
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    private static final Color COLOR_INFO = new Color(52, 152, 219);
    private static final Color COLOR_WARNING = new Color(241, 196, 15);
    private static final Color COLOR_SECONDARY = new Color(149, 165, 166);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);

    public CustomerDashboard(Users user) {
        this.currentUser = user;

        this.setTitle("Customer Dashboard");
        this.setSize(500, 560);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(COLOR_BG);

        // Welcome Message
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getFullName());
        welcomeLabel.setBounds(50, 30, 400, 30);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setForeground(COLOR_TEXT);
        panel.add(welcomeLabel);
        
        JLabel subtitleLabel = new JLabel("What would you like to do today?");
        subtitleLabel.setBounds(50, 65, 400, 20);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        panel.add(subtitleLabel);

        // View & Book Movies Button
        JButton bookBtn = createButton("View & Book Movies", COLOR_SUCCESS);
        bookBtn.setBounds(80, 120, 340, 45);
        panel.add(bookBtn);

        // Upcoming Movies Button
        JButton upcomingBtn = createButton("Upcoming Movies", new Color(155, 89, 182));
        upcomingBtn.setBounds(80, 180, 340, 45);
        panel.add(upcomingBtn);

        // My Bookings Button
        JButton historyBtn = createButton("My Bookings", COLOR_INFO);
        historyBtn.setBounds(80, 240, 340, 45);
        panel.add(historyBtn);

        // Update Profile Button
        JButton profileBtn = createButton("Update Account Details", COLOR_WARNING);
        profileBtn.setBounds(80, 300, 340, 45);
        panel.add(profileBtn);

        // Logout Button
        JButton logoutBtn = createButton("Logout", COLOR_SECONDARY);
        logoutBtn.setBounds(80, 360, 340, 45);
        panel.add(logoutBtn);

        bookBtn.addActionListener(e -> {
             new MovieSelection(currentUser); 
            this.dispose();
        });

        upcomingBtn.addActionListener(e -> {
            new ViewUpcomingMovies(currentUser);
            this.dispose();
        });

        historyBtn.addActionListener(e -> {
            new CustomerHistory(currentUser);
            this.dispose();
        });

        profileBtn.addActionListener(e -> {
            new CustomerProfile(currentUser); 
            this.dispose();
        });

        logoutBtn.addActionListener(e -> {
            new Homepage();
            this.dispose();
        });

        this.add(panel);
        this.setVisible(true);
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
}
