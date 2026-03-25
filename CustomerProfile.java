package CinemaAPP;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Customer Profile Update Page
 * Allows customers to view and update their account details
 * 
 * @author Mathew
 */
public class CustomerProfile extends JFrame {
    
    private Users currentUser;
    private JTextField fullNameField;
    private JTextField usernameField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    
    // Colors
    private static final Color COLOR_BG = new Color(240, 242, 245);
    private static final Color COLOR_CARD = Color.WHITE;
    private static final Color COLOR_PRIMARY = new Color(52, 152, 219);
    private static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    private static final Color COLOR_DANGER = new Color(231, 76, 60);
    
    public CustomerProfile(Users user) {
        this.currentUser = user;
        setupUI();
    }
    
    private void setupUI() {
        this.setTitle("My Profile - " + currentUser.getFullName());
        this.setSize(600, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(COLOR_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Header
        mainPanel.add(createHeaderPanel());
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Profile Information Card
        mainPanel.add(createProfileInfoCard());
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Change Password Card
        mainPanel.add(createPasswordCard());
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Buttons
        mainPanel.add(createButtonPanel());
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(COLOR_BG);
        this.add(scrollPane);
        
        this.setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        JLabel titleLabel = new JLabel("Profile Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Manage your account information");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitleLabel);
        
        return panel;
    }
    
    private JPanel createProfileInfoCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        
        JLabel cardTitle = new JLabel("Personal Information");
        cardTitle.setFont(new Font("Arial", Font.BOLD, 18));
        cardTitle.setForeground(new Color(44, 62, 80));
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(cardTitle);
        card.add(Box.createVerticalStrut(15));
        
        // Full Name
        card.add(createLabel("Full Name"));
        fullNameField = new JTextField(currentUser.getFullName());
        fullNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        fullNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        card.add(fullNameField);
        card.add(Box.createVerticalStrut(15));
        
        // Username (Read-only)
        card.add(createLabel("Username (Cannot be changed)"));
        usernameField = new JTextField(currentUser.getUsername());
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setEditable(false);
        usernameField.setBackground(new Color(245, 245, 245));
        card.add(usernameField);
        
        return card;
    }
    
    private JPanel createPasswordCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        
        JLabel cardTitle = new JLabel("Change Password");
        cardTitle.setFont(new Font("Arial", Font.BOLD, 18));
        cardTitle.setForeground(new Color(44, 62, 80));
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(cardTitle);
        card.add(Box.createVerticalStrut(10));
        
        JLabel infoLabel = new JLabel("Leave blank if you don't want to change password");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(new Color(127, 140, 141));
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(infoLabel);
        card.add(Box.createVerticalStrut(15));
        
        // Current Password
        card.add(createLabel("Current Password"));
        currentPasswordField = new JPasswordField();
        currentPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        currentPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        card.add(currentPasswordField);
        card.add(Box.createVerticalStrut(15));
        
        // New Password
        card.add(createLabel("New Password"));
        newPasswordField = new JPasswordField();
        newPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        newPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        card.add(newPasswordField);
        card.add(Box.createVerticalStrut(15));
        
        // Confirm Password
        card.add(createLabel("Confirm New Password"));
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        card.add(confirmPasswordField);
        
        return card;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setForeground(new Color(52, 73, 94));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Arial", Font.BOLD, 15));
        saveButton.setBackground(COLOR_SUCCESS);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.setPreferredSize(new Dimension(160, 40));
        saveButton.addActionListener(e -> saveChanges());
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 15));
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.addActionListener(e -> {
            new CustomerDashboard(currentUser);
            this.dispose();
        });
        
        panel.add(saveButton);
        panel.add(cancelButton);
        
        return panel;
    }
    
    private void saveChanges() {
        String newFullName = fullNameField.getText().trim();
        String currentPassword = new String(currentPasswordField.getPassword()).trim();
        String newPassword = new String(newPasswordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();
        
        // Validation
        if (newFullName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Full name cannot be empty!",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if password change is requested
        boolean changePassword = !currentPassword.isEmpty() || !newPassword.isEmpty() || !confirmPassword.isEmpty();
        
        if (changePassword) {
            // Verify current password
            if (!currentPassword.equals(currentUser.getPassword())) {
                JOptionPane.showMessageDialog(this,
                    "Current password is incorrect!",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validate new password
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "New password cannot be empty!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (newPassword.length() < 4) {
                JOptionPane.showMessageDialog(this,
                    "New password must be at least 4 characters!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Confirm passwords match
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this,
                    "New passwords do not match!",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update password
            currentUser.setPassword(newPassword);
        }
        
        // Update full name
        currentUser.setFullName(newFullName);
        
        // Save to file
        if (FileHandler.updateUser(currentUser)) {
            JOptionPane.showMessageDialog(this,
                "Profile updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Clear password fields
            currentPasswordField.setText("");
            newPasswordField.setText("");
            confirmPasswordField.setText("");
            
            // Return to dashboard
            new CustomerDashboard(currentUser);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to update profile. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
