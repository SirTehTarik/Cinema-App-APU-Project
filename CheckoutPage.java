package CinemaAPP;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.List;

/**
 * Checkout Page - Payment and Discount Selection
 * 
 * @author Mathew
 */
public class CheckoutPage extends JFrame {
    
    private Moviedata selectedMovie;
    private Users currentUser;
    private Set<String> selectedSeats;
    
    private JComboBox<String> paymentMethodBox;
    private JComboBox<String> discountTypeBox;
    private JLabel subtotalLabel;
    private JLabel discountLabel;
    private JLabel fbTotalLabel;
    private JLabel finalTotalLabel;
    
    private double basePrice;
    private double discountAmount = 0;
    private double fbTotal = 0;
    private double finalTotal;
    
    private List<FBItem> selectedFBItems;
    
    // Colors
    private static final Color COLOR_BG = new Color(32, 32, 32);
    private static final Color COLOR_CARD = new Color(45, 45, 45);
    private static final Color COLOR_TEXT = Color.WHITE;
    private static final Color COLOR_ACCENT = new Color(52, 152, 219);
    private static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    
    // Constructor with F&B items
    public CheckoutPage(Moviedata movie, Users user, Set<String> seats, List<FBItem> fbItems, double fbTotal) {
        this.selectedMovie = movie;
        this.currentUser = user;
        this.selectedSeats = seats;
        this.basePrice = selectedSeats.size() * selectedMovie.getPrice();
        this.finalTotal = basePrice + fbTotal;
        this.selectedFBItems = fbItems;
        this.fbTotal = fbTotal;
        
        setupUI();
    }
    
    // Constructor without F&B (for backward compatibility)
    public CheckoutPage(Moviedata movie, Users user, Set<String> seats) {
        this.selectedMovie = movie;
        this.currentUser = user;
        this.selectedSeats = seats;
        this.basePrice = selectedSeats.size() * selectedMovie.getPrice();
        this.finalTotal = basePrice;
        this.selectedFBItems = new java.util.ArrayList<>();
        this.fbTotal = 0;
        
        setupUI();
    }
    
    
    private void setupUI() {
        this.setTitle("Checkout - Complete Your Booking");
        this.setSize(600, 800);
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
        
        // Booking Summary
        mainPanel.add(createBookingSummaryPanel());
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Discount Selection
        mainPanel.add(createDiscountPanel());
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Payment Method
        mainPanel.add(createPaymentPanel());
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Price Breakdown
        mainPanel.add(createPriceBreakdownPanel());
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
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel titleLabel = new JLabel("Checkout");
        titleLabel.setForeground(COLOR_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel subtitleLabel = new JLabel("Review and complete your booking");
        subtitleLabel.setForeground(new Color(180, 180, 180));
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(COLOR_BG);
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitleLabel);
        
        panel.add(textPanel, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createBookingSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        JLabel headerLabel = new JLabel("Booking Summary");
        headerLabel.setForeground(COLOR_TEXT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(headerLabel);
        panel.add(Box.createVerticalStrut(15));
        
        // Movie details
        panel.add(createInfoRow("Movie:", selectedMovie.getName()));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createInfoRow("Hall:", selectedMovie.getHall()));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createInfoRow("Time:", selectedMovie.getTime()));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createInfoRow("Seats:", String.join(", ", selectedSeats)));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createInfoRow("Quantity:", selectedSeats.size() + " seat(s)"));
        
        return panel;
    }
    
    private JPanel createDiscountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        JLabel headerLabel = new JLabel("Select Discount Type");
        headerLabel.setForeground(COLOR_TEXT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(headerLabel);
        panel.add(Box.createVerticalStrut(10));
        
        String movieDiscount = selectedMovie.getDiscountType();
        String[] discountTypes;
        
        if (movieDiscount == null || movieDiscount.equals(Moviedata.DISCOUNT_NONE) || movieDiscount.trim().isEmpty()) {
            discountTypes = new String[] { Moviedata.DISCOUNT_NONE };
        } else {
            discountTypes = new String[] { Moviedata.DISCOUNT_NONE, movieDiscount };
        }
        
        discountTypeBox = new JComboBox<>(discountTypes);
        discountTypeBox.setFont(new Font("Arial", Font.PLAIN, 14));
        discountTypeBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        discountTypeBox.setBackground(new Color(60, 60, 60));
        discountTypeBox.setForeground(COLOR_TEXT);
        discountTypeBox.addActionListener(e -> updatePricing());
        panel.add(discountTypeBox);
        
        return panel;
    }
    
    private JPanel createPaymentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        JLabel headerLabel = new JLabel("Payment Method");
        headerLabel.setForeground(COLOR_TEXT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(headerLabel);
        panel.add(Box.createVerticalStrut(10));
        
        String[] paymentMethods = {
            "Credit/Debit Card",
            "Online Banking",
            "E-Wallet (Touch 'n Go, GrabPay)",
            "Cash at Counter"
        };
        
        paymentMethodBox = new JComboBox<>(paymentMethods);
        paymentMethodBox.setFont(new Font("Arial", Font.PLAIN, 14));
        paymentMethodBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        paymentMethodBox.setBackground(new Color(60, 60, 60));
        paymentMethodBox.setForeground(COLOR_TEXT);
        panel.add(paymentMethodBox);
        
        return panel;
    }
    
    private JPanel createPriceBreakdownPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 60)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, fbTotal > 0 ? 210 : 170));
        
        JLabel headerLabel = new JLabel("Price Breakdown");
        headerLabel.setForeground(COLOR_TEXT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(headerLabel);
        panel.add(Box.createVerticalStrut(15));
        
        // Subtotal
        subtotalLabel = new JLabel();
        updateSubtotalLabel();
        panel.add(createPriceRow(subtotalLabel));
        panel.add(Box.createVerticalStrut(8));
        
        // Discount
        discountLabel = new JLabel();
        updateDiscountLabel();
        discountLabel.setForeground(COLOR_SUCCESS);
        panel.add(createPriceRow(discountLabel));
        panel.add(Box.createVerticalStrut(8));
        
        // F&B Total (if there are items)
        if (fbTotal > 0) {
            fbTotalLabel = new JLabel();
            updateFBTotalLabel();
            fbTotalLabel.setForeground(COLOR_ACCENT);
            panel.add(createPriceRow(fbTotalLabel));
            panel.add(Box.createVerticalStrut(8));
        }
        
        // Separator
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(80, 80, 80));
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        panel.add(separator);
        panel.add(Box.createVerticalStrut(8));
        
        // Final Total
        finalTotalLabel = new JLabel();
        updateFinalTotalLabel();
        finalTotalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(createPriceRow(finalTotalLabel));
        
        return panel;
    }
    
    private JPanel createPriceRow(JLabel label) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setBackground(COLOR_CARD);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        row.add(label);
        return row;
    }
    
    private JPanel createInfoRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(COLOR_CARD);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        
        JLabel labelComp = new JLabel(label);
        labelComp.setForeground(new Color(180, 180, 180));
        labelComp.setFont(new Font("Arial", Font.PLAIN, 13));
        
        JLabel valueComp = new JLabel(value);
        valueComp.setForeground(COLOR_TEXT);
        valueComp.setFont(new Font("Arial", Font.PLAIN, 13));
        
        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.EAST);
        
        return row;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JButton confirmButton = new JButton("Confirm & Pay");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 16));
        confirmButton.setBackground(COLOR_SUCCESS);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorderPainted(false);
        confirmButton.setPreferredSize(new Dimension(200, 45));
        confirmButton.addActionListener(e -> processPayment());
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setBackground(new Color(149, 165, 166));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setPreferredSize(new Dimension(120, 45));
        cancelButton.addActionListener(e -> {
            if (currentUser != null && currentUser.getRole().equals("Guest")) {
                new ClerkDashboard();
            } else {
                new CustomerDashboard(currentUser);
            }
            this.dispose();
        });
        
        panel.add(confirmButton);
        panel.add(cancelButton);
        
        return panel;
    }
    
    private void updatePricing() {
        String selectedDiscount = (String) discountTypeBox.getSelectedItem();
        double discountPercentage = 0;
        
        if (selectedDiscount != null && selectedDiscount.equals(selectedMovie.getDiscountType())) {
            // Uses the built-in getter to fetch the percentage matched to the specific discount
            discountPercentage = selectedMovie.getDiscountPercentage();
        }
        
        discountAmount = basePrice * discountPercentage;
        finalTotal = basePrice - discountAmount + fbTotal;
        
        updateSubtotalLabel();
        updateDiscountLabel();
        updateFinalTotalLabel();
    }
    
    private void updateSubtotalLabel() {
        subtotalLabel.setText(String.format("Subtotal: RM %.2f", basePrice));
        subtotalLabel.setForeground(COLOR_TEXT);
        subtotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    }
    
    private void updateDiscountLabel() {
        if (discountAmount > 0) {
            discountLabel.setText(String.format("Discount: - RM %.2f", discountAmount));
        } else {
            discountLabel.setText("Discount: RM 0.00");
        }
        discountLabel.setForeground(COLOR_SUCCESS);
        discountLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    }
    
    private void updateFinalTotalLabel() {
        finalTotalLabel.setText(String.format("Total Amount: RM %.2f", finalTotal));
        finalTotalLabel.setForeground(COLOR_TEXT);
        finalTotalLabel.setFont(new Font("Arial", Font.BOLD, 18));
    }
    
    private void updateFBTotalLabel() {
        if (fbTotal > 0) {
            fbTotalLabel.setText(String.format("F&B Total: RM %.2f", fbTotal));
        } else {
            fbTotalLabel.setText("F&B Total: RM 0.00");
        }
        fbTotalLabel.setForeground(COLOR_ACCENT);
        fbTotalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    }
    
    private void processPayment() {
        String paymentMethod = (String) paymentMethodBox.getSelectedItem();
        String discountType = (String) discountTypeBox.getSelectedItem();
        
        // Extract discount type name
        String discountName = discountType.equals(Moviedata.DISCOUNT_NONE) ? "None" : discountType;
        
        // Show processing message
        JOptionPane.showMessageDialog(this,
            "Processing payment via " + paymentMethod + "...",
            "Processing",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Create bookings for each seat with final price per seat
        double pricePerSeat = finalTotal / selectedSeats.size();
        java.util.List<String> bookingIds = new java.util.ArrayList<>();
        
        for (String seat : selectedSeats) {
            TicketData ticket = new TicketData(
                currentUser.getFullName(),
                selectedMovie.getName(),
                seat,
                selectedMovie.getHall(),
                pricePerSeat
            );
            
            FileHandler.addBooking(ticket);
            bookingIds.add(ticket.getBookingId());
            
            // Save F&B items linked to this booking if any F&B items were ordered
            if (fbTotal > 0) {
                saveFBOrderToFile(ticket.getBookingId(), selectedFBItems);
            }
        }
        
        // Generate current date for the receipt
        String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
        
        // Build F&B items string for receipt
        StringBuilder fbItemsString = new StringBuilder();
        for (FBItem item : selectedFBItems) {
            if (item.getQuantity() > 0) {
                fbItemsString.append(item.getQuantity())
                    .append("x ")
                    .append(item.getName())
                    .append(" (RM ")
                    .append(String.format("%.2f", item.getTotal()))
                    .append(")\n");
            }
        }
        String fbItemsDisplay = fbItemsString.length() > 0 ? fbItemsString.toString() : "None\n";
        
        // Build receipt message
        String receiptMessage;
        if (fbTotal > 0) {
            receiptMessage = String.format(
                "Payment Successful!\n\n" +
                "========== RECEIPT ==========\n" +
                "Date: %s\n\n" +
                "Movie: %s\n" +
                "Hall: %s\n" +
                "Time: %s\n" +
                "Seats: %s\n\n" +
                "Ticket Subtotal: RM %.2f\n" +
                "Discount (%s): - RM %.2f\n\n" +
                "Food & Beverages:\n%s" +
                "F&B Total: RM %.2f\n" +
                "============================\n" +
                "Total Paid: RM %.2f\n\n" +
                "Payment Method: %s\n\n" +
                "Booking ID(s): %s\n\n" +
                "Thank you for your booking!\n" +
                "Enjoy your movie!",
                currentDate,
                selectedMovie.getName(),
                selectedMovie.getHall(),
                selectedMovie.getTime(),
                String.join(", ", selectedSeats),
                basePrice,
                discountName,
                discountAmount,
                fbItemsDisplay,
                fbTotal,
                finalTotal,
                paymentMethod,
                String.join(", ", bookingIds)
            );
        } else {
            receiptMessage = String.format(
                "Payment Successful!\n\n" +
                "========== RECEIPT ==========\n" +
                "Date: %s\n\n" +
                "Movie: %s\n" +
                "Hall: %s\n" +
                "Time: %s\n" +
                "Seats: %s\n\n" +
                "Subtotal: RM %.2f\n" +
                "Discount (%s): - RM %.2f\n" +
                "============================\n" +
                "Total Paid: RM %.2f\n\n" +
                "Payment Method: %s\n\n" +
                "Booking ID(s): %s\n\n" +
                "Thank you for your booking!\n" +
                "Enjoy your movie!",
                currentDate,
                selectedMovie.getName(),
                selectedMovie.getHall(),
                selectedMovie.getTime(),
                String.join(", ", selectedSeats),
                basePrice,
                discountName,
                discountAmount,
                finalTotal,
                paymentMethod,
                String.join(", ", bookingIds)
            );
        }
        
        JOptionPane.showMessageDialog(this,
            receiptMessage,
            "Booking Confirmed",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Close checkout and return to appropriate dashboard
        this.dispose();
        
        // If user is a guest (clerk booking), return to clerk dashboard
        if (currentUser.getRole().equals("Guest")) {
            new ClerkDashboard();
        } else {
            // Regular customer booking
            new CustomerDashboard(currentUser);
        }
    }
    
    // Method to save F&B order to file linked with booking ID
    private void saveFBOrderToFile(String bookingId, List<FBItem> fbItems) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("FoodBeveragesOrders.txt", true);
            java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
            
            for (FBItem item : fbItems) {
                if (item.getQuantity() > 0) {
                    String fbRecord = bookingId + "|" + item.getName() + "|" + item.getQuantity() + "|" 
                        + String.format("%.2f", item.getPrice()) + "|" 
                        + String.format("%.2f", item.getTotal());
                    bw.write(fbRecord);
                    bw.newLine();
                }
            }
            
            bw.close();
            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving F&B order: " + e.getMessage());
        }
    }
}
