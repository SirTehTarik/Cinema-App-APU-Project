package CinemaAPP;

import javax.swing.*;
import java.awt.*;

/**
 * Clerk Booking Input - First step for clerk-assisted booking
 * Collects customer information before proceeding to movie selection
 * 
 * @author Mathew
 */
public class ClerkBookingInput extends JFrame {
    
    private JTextField customerNameField;
    private JTextField customerPhoneField;

    private static final Color COLOR_BG = new Color(214, 217, 223);
    private static final Color COLOR_TITLE = new Color(31, 58, 94);
    private static final Color COLOR_LABEL = new Color(35, 57, 86);
    private static final Color COLOR_PRIMARY = new Color(74, 138, 214);
    private static final Color COLOR_SECONDARY = new Color(155, 170, 177);
    
    public ClerkBookingInput() {
        setupUI();
    }
    
    private void setupUI() {
        this.setTitle("Clerk Booking Input");
        this.setSize(600, 380);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 30, 40));
        mainPanel.setBackground(COLOR_BG);
        
        JPanel titlePanel = createHeaderPanel();
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        JPanel inputCard = createInputCard();
        mainPanel.add(inputCard);
        mainPanel.add(Box.createVerticalStrut(20));
        
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel);
        
        this.add(mainPanel);
        this.setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));
        
        JLabel titleLabel = new JLabel("Customer Booking");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 44));
        titleLabel.setForeground(COLOR_TITLE);

        panel.add(titleLabel);
        
        return panel;
    }
    
    private JPanel createInputCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(COLOR_BG);
        card.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 12);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Customer Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        nameLabel.setForeground(COLOR_LABEL);
        
        customerNameField = new JTextField();
        customerNameField.setFont(new Font("Arial", Font.PLAIN, 13));
        customerNameField.setPreferredSize(new Dimension(250, 30));
        customerNameField.setBorder(BorderFactory.createLineBorder(new Color(139, 149, 160), 1));

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        phoneLabel.setForeground(COLOR_LABEL);
        
        customerPhoneField = new JTextField();
        customerPhoneField.setFont(new Font("Arial", Font.PLAIN, 13));
        customerPhoneField.setPreferredSize(new Dimension(250, 30));
        customerPhoneField.setBorder(BorderFactory.createLineBorder(new Color(139, 149, 160), 1));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        card.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        card.add(customerNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        card.add(phoneLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        card.add(customerPhoneField, gbc);
        
        return card;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JButton nextButton = new JButton("Start Booking");
        nextButton.setFont(new Font("Arial", Font.BOLD, 13));
        nextButton.setBackground(COLOR_PRIMARY);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setPreferredSize(new Dimension(160, 45));
        nextButton.addActionListener(e -> proceedToMovieSelection());
        
        JButton cancelButton = new JButton("Back to Home");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 13));
        cancelButton.setBackground(COLOR_SECONDARY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setPreferredSize(new Dimension(160, 45));
        cancelButton.addActionListener(e -> {
            new ClerkDashboard();
            this.dispose();
        });
        
        panel.add(nextButton);
        panel.add(cancelButton);
        
        return panel;
    }
    
    private void proceedToMovieSelection() {
        String customerName = customerNameField.getText().trim();
        String customerPhone = customerPhoneField.getText().trim();
        
        // Validation
        if (customerName.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter customer name!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            customerNameField.requestFocus();
            return;
        }
        
        if (customerPhone.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter customer phone number!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            customerPhoneField.requestFocus();
            return;
        }
        
        // Validate phone number: remove spaces and hyphens, then check if it's 10-15 digits
        String cleanPhone = customerPhone.replaceAll("[\\s\\-]", "");
        if (!cleanPhone.matches("^\\+?[0-9]{10,15}$")) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid phone number! (e.g. 0123456789 or +60123456789)",
                "Invalid Format",
                JOptionPane.WARNING_MESSAGE);
            customerPhoneField.requestFocus();
            return;
        }
        
        // Proceed to movie selection
        new ClerkMovieSelection(customerName, customerPhone);
        this.dispose();
    }
}
