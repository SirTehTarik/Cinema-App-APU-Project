/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Food & Beverages Selection Window - Separate standalone window for F&B selection
 * 
 * @author Mathew
 */
public class FBSelectionWindow extends JFrame {
    
    private Moviedata selectedMovie;
    private Users currentUser;
    private Set<String> selectedSeats;
    
    private List<FBItem> fbMenu;
    private List<JSpinner> quantitySpinners;
    private double fbTotal = 0;
    private JLabel totalLabel;  // Store reference to update dynamically
    
    // Colors
    private static final Color COLOR_BG = new Color(32, 32, 32);
    private static final Color COLOR_CARD = new Color(45, 45, 45);
    private static final Color COLOR_TEXT = Color.WHITE;
    private static final Color COLOR_ACCENT = new Color(52, 152, 219);
    private static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    
    public FBSelectionWindow(Moviedata movie, Users user, Set<String> seats) {
        this.selectedMovie = movie;
        this.currentUser = user;
        this.selectedSeats = seats;
        this.fbMenu = loadFBItems();
        this.quantitySpinners = new ArrayList<>();
        
        setupUI();
    }
    
    private void setupUI() {
        this.setTitle("Food & Beverages Selection");
        this.setSize(500, 650);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(COLOR_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Header
        mainPanel.add(createHeaderPanel());
        mainPanel.add(Box.createVerticalStrut(20));
        
        // F&B Menu
        mainPanel.add(createFBMenuPanel());
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Price Summary
        mainPanel.add(createPriceSummaryPanel());
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
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        JLabel titleLabel = new JLabel("Add Food & Beverages");
        titleLabel.setForeground(COLOR_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        
        JLabel subtitleLabel = new JLabel("Select items to add to your order (Optional)");
        subtitleLabel.setForeground(new Color(180, 180, 180));
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(COLOR_BG);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitleLabel);
        
        panel.add(textPanel, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createFBMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        if (fbMenu.isEmpty()) {
            JLabel noItemsLabel = new JLabel("No items available");
            noItemsLabel.setForeground(new Color(180, 180, 180));
            noItemsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(noItemsLabel);
        } else {
            for (int i = 0; i < fbMenu.size(); i++) {
                FBItem item = fbMenu.get(i);
                JPanel itemRow = createFBItemRow(item, i);
                panel.add(itemRow);
                if (i < fbMenu.size() - 1) {
                    panel.add(Box.createVerticalStrut(12));
                }
            }
        }
        
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Math.max(150, fbMenu.size() * 45 + 30)));
        return panel;
    }
    
    private JPanel createFBItemRow(FBItem item, int index) {
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(COLOR_CARD);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel nameLabel = new JLabel(item.getName());
        nameLabel.setForeground(COLOR_TEXT);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel priceLabel = new JLabel("RM" + String.format("%.2f", item.getPrice()));
        priceLabel.setForeground(COLOR_ACCENT);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 13));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setBackground(COLOR_CARD);
        leftPanel.add(nameLabel);
        leftPanel.add(priceLabel);
        
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        quantitySpinner.setMaximumSize(new Dimension(80, 35));
        quantitySpinner.setFont(new Font("Arial", Font.PLAIN, 12));
        quantitySpinner.setBackground(new Color(60, 60, 60));
        ((JComponent) quantitySpinner.getEditor()).setForeground(COLOR_TEXT);
        
        quantitySpinner.addChangeListener(e -> {
            item.setQuantity((Integer) quantitySpinner.getValue());
            updateFBTotal();
        });
        
        quantitySpinners.add(quantitySpinner);
        
        row.add(leftPanel, BorderLayout.WEST);
        row.add(quantitySpinner, BorderLayout.EAST);
        
        return row;
    }
    
    private JPanel createPriceSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        JLabel headerLabel = new JLabel("Order Summary");
        headerLabel.setForeground(COLOR_TEXT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(headerLabel);
        panel.add(Box.createVerticalStrut(12));
        
        // Store reference to update dynamically
        totalLabel = new JLabel(String.format("F&B Total: RM %.2f", fbTotal));
        totalLabel.setForeground(COLOR_SUCCESS);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(totalLabel);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JButton continueButton = new JButton("Continue to Checkout");
        continueButton.setFont(new Font("Arial", Font.BOLD, 14));
        continueButton.setBackground(COLOR_SUCCESS);
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        continueButton.setBorderPainted(false);
        continueButton.setPreferredSize(new Dimension(220, 45));
        continueButton.addActionListener(e -> proceedToCheckout());
        
        JButton skipButton = new JButton("Skip F&B");
        skipButton.setFont(new Font("Arial", Font.BOLD, 14));
        skipButton.setBackground(new Color(149, 165, 166));
        skipButton.setForeground(Color.WHITE);
        skipButton.setFocusPainted(false);
        skipButton.setBorderPainted(false);
        skipButton.setPreferredSize(new Dimension(120, 45));
        skipButton.addActionListener(e -> proceedToCheckout());
        
        panel.add(continueButton);
        panel.add(skipButton);
        
        return panel;
    }
    
    private void updateFBTotal() {
        fbTotal = 0;
        for (FBItem item : fbMenu) {
            fbTotal += item.getTotal();
        }
        // Update the label text dynamically
        if (totalLabel != null) {
            totalLabel.setText(String.format("F&B Total: RM %.2f", fbTotal));
            totalLabel.repaint();
        }
    }
    
    private List<FBItem> loadFBItems() {
        List<FBItem> items = new ArrayList<>();
        try {
            String filePath = "FoodBeverages.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String itemName = parts[0].trim();
                    double itemPrice = Double.parseDouble(parts[1].trim());
                    items.add(new FBItem(itemName, itemPrice));
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading F&B items: " + e.getMessage());
        }
        return items;
    }
    
    private void proceedToCheckout() {
        // Create a filtered list with only selected items (quantity > 0)
        List<FBItem> selectedItems = new ArrayList<>();
        for (FBItem item : fbMenu) {
            if (item.getQuantity() > 0) {
                selectedItems.add(item);
            }
        }
        
        // Open the checkout page with only selected F&B items and total
        new CheckoutPage(selectedMovie, currentUser, selectedSeats, selectedItems, fbTotal);
        this.dispose();
    }
}
