package CinemaAPP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Visual Seat Selection Interface for Movie Bookings
 * Similar to modern cinema booking apps
 * 
 * @author Mathew
 */
public class VisualSeatBooking extends JFrame {
    
    private Moviedata selectedMovie;
    private Users currentUser;
    private Set<String> selectedSeats = new HashSet<>();
    private Map<String, JButton> seatButtons = new HashMap<>();
    private JLabel totalPriceLabel;
    private JButton bookButton;
    
    // Seat layout configuration
    private static final int ROWS = 10; // A to K (skipping I)
    private static final int COLS = 13;
    private static final char START_ROW = 'A';
    
    // Colors
    private static final Color COLOR_AVAILABLE = new Color(52, 152, 219); // Blue
    private static final Color COLOR_SELECTED = new Color(255, 152, 0); // Orange
    private static final Color COLOR_SOLD = new Color(231, 76, 60); // Red
    private static final Color COLOR_BG = new Color(32, 32, 32); // Dark background
    private static final Color COLOR_TEXT = Color.WHITE;
    
    public VisualSeatBooking(Moviedata movie, Users user) {
        this.selectedMovie = movie;
        this.currentUser = user;
        
        setupUI();
    }
    
    private void setupUI() {
        this.setTitle("Select Seats");
        this.setSize(900, 850);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(COLOR_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header Section
        mainPanel.add(createHeaderPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Movie Details Section
        mainPanel.add(createMovieDetailsPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Showtime Selection (if needed)
        mainPanel.add(createShowtimePanel());
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Legend Section
        mainPanel.add(createLegendPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Screen Section
        mainPanel.add(createScreenPanel());
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Seat Map Section
        JScrollPane scrollPane = new JScrollPane(createSeatMapPanel());
        scrollPane.setPreferredSize(new Dimension(850, 450));
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(COLOR_BG);
        mainPanel.add(scrollPane);
        
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Footer with Total and Book Button
        mainPanel.add(createFooterPanel());
        
        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setBorder(null);
        mainScrollPane.getViewport().setBackground(COLOR_BG);
        this.add(mainScrollPane);
        this.setVisible(true);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel stepLabel = new JLabel("Select Seats");
        stepLabel.setForeground(COLOR_TEXT);
        stepLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton closeButton = new JButton("X");
        closeButton.setBackground(COLOR_BG);
        closeButton.setForeground(COLOR_TEXT);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.addActionListener(e -> {
            if (currentUser != null && currentUser.getRole().equals("Guest")) {
                new ClerkDashboard();
            } else {
                new CustomerDashboard(currentUser);
            }
            this.dispose();
        });
        
        panel.add(stepLabel, BorderLayout.WEST);
        panel.add(closeButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createMovieDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        JLabel titleLabel = new JLabel(selectedMovie.getName());
        titleLabel.setForeground(COLOR_TEXT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String details = String.format("%s | %s | %s | %s", 
            selectedMovie.getRating(),
            selectedMovie.getGenre(),
            selectedMovie.getDuration(),
            selectedMovie.getLanguage());
        
        JLabel detailsLabel = new JLabel(details);
        detailsLabel.setForeground(new Color(180, 180, 180));
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel locationLabel = new JLabel(selectedMovie.getHall() + " - " + selectedMovie.getTime());
        locationLabel.setForeground(new Color(180, 180, 180));
        locationLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        locationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(detailsLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(locationLabel);
        
        return panel;
    }
    
    private JPanel createShowtimePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JLabel label = new JLabel("Available showtimes:");
        label.setForeground(new Color(180, 180, 180));
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label);
        
        JButton currentTimeButton = new JButton(selectedMovie.getTime());
        currentTimeButton.setBackground(new Color(60, 60, 60));
        currentTimeButton.setForeground(new Color(100, 200, 100));
        currentTimeButton.setFont(new Font("Arial", Font.BOLD, 12));
        currentTimeButton.setFocusPainted(false);
        currentTimeButton.setBorderPainted(false);
        panel.add(currentTimeButton);
        
        return panel;
    }
    
    private JPanel createLegendPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        panel.add(createLegendItem("SELECTED", COLOR_SELECTED));
        panel.add(createLegendItem("SOLD", COLOR_SOLD));
        panel.add(createLegendItem("AVAILABLE", COLOR_AVAILABLE));
        
        return panel;
    }
    
    private JPanel createLegendItem(String text, Color color) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        item.setBackground(COLOR_BG);
        
        JLabel icon = new JLabel("[  ]");
        icon.setForeground(color);
        icon.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel label = new JLabel(text);
        label.setForeground(new Color(180, 180, 180));
        label.setFont(new Font("Arial", Font.PLAIN, 11));
        
        item.add(icon);
        item.add(label);
        
        return item;
    }
    
    private JPanel createScreenPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel screenLabel = new JLabel("SCREEN");
        screenLabel.setForeground(new Color(120, 120, 120));
        screenLabel.setFont(new Font("Arial", Font.BOLD, 12));
        screenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(100, 100, 100));
        separator.setMaximumSize(new Dimension(700, 2));
        
        panel.add(separator);
        panel.add(Box.createVerticalStrut(5));
        panel.add(screenLabel);
        
        return panel;
    }
    
    private JPanel createSeatMapPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        
        // Load sold seats for this movie
        Set<String> soldSeats = getSoldSeats();
        
        // Row letters A-K, skipping I
        char[] rowLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K'};
        
        for (int row = 0; row < ROWS; row++) {
            char rowLetter = rowLetters[row];
            
            // Row label (left)
            gbc.gridx = 0;
            gbc.gridy = row;
            JLabel leftLabel = new JLabel(String.valueOf(rowLetter));
            leftLabel.setForeground(new Color(150, 150, 150));
            leftLabel.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(leftLabel, gbc);
            
            // Seats
            for (int col = 0; col < COLS; col++) {
                gbc.gridx = col + 1;
                
                String seatNumber = rowLetter + String.valueOf(col + 1);
                
                // Create seat button
                JButton seatBtn = new JButton();
                seatBtn.setPreferredSize(new Dimension(50, 40));
                seatBtn.setFont(new Font("Arial", Font.BOLD, 9));
                seatBtn.setFocusPainted(false);
                seatBtn.setBorderPainted(false);
                seatBtn.setMargin(new Insets(0, 0, 0, 0));
                
                // Check if seat is sold
                if (soldSeats.contains(seatNumber)) {
                    seatBtn.setBackground(COLOR_SOLD);
                    seatBtn.setEnabled(false);
                    seatBtn.setText(seatNumber);
                    seatBtn.setForeground(Color.WHITE);
                } else {
                    seatBtn.setBackground(COLOR_AVAILABLE);
                    seatBtn.setText(seatNumber); // Show seat number
                    seatBtn.setForeground(COLOR_TEXT);
                    
                    // Add click listener
                    seatBtn.addActionListener(e -> toggleSeatSelection(seatNumber, seatBtn));
                }
                
                seatButtons.put(seatNumber, seatBtn);
                panel.add(seatBtn, gbc);
            }
            
            // Row label (right)
            gbc.gridx = COLS + 1;
            JLabel rightLabel = new JLabel(String.valueOf(rowLetter));
            rightLabel.setForeground(new Color(150, 150, 150));
            rightLabel.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(rightLabel, gbc);
        }
        
        return panel;
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BG);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        // Total Price Panel
        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pricePanel.setBackground(COLOR_BG);
        
        JLabel totalLabel = new JLabel("Total: ");
        totalLabel.setForeground(new Color(180, 180, 180));
        totalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        totalPriceLabel = new JLabel("RM 0.00");
        totalPriceLabel.setForeground(COLOR_TEXT);
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        pricePanel.add(totalLabel);
        pricePanel.add(totalPriceLabel);
        
        // Book Button
        bookButton = new JButton("Book Seat(s)");
        bookButton.setBackground(new Color(139, 69, 19));
        bookButton.setForeground(COLOR_TEXT);
        bookButton.setFont(new Font("Arial", Font.BOLD, 14));
        bookButton.setFocusPainted(false);
        bookButton.setPreferredSize(new Dimension(200, 45));
        bookButton.setEnabled(false);
        bookButton.addActionListener(e -> confirmBooking());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(COLOR_BG);
        buttonPanel.add(bookButton);
        
        panel.add(pricePanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private Set<String> getSoldSeats() {
        Set<String> soldSeats = new HashSet<>();
        List<TicketData> allBookings = FileHandler.loadAllBookings();
        
        for (TicketData booking : allBookings) {
            if (booking.getMovieName().equalsIgnoreCase(selectedMovie.getName()) &&
                booking.getHall().equalsIgnoreCase(selectedMovie.getHall())) {
                soldSeats.add(booking.getSeatNumber());
            }
        }
        
        return soldSeats;
    }
    
    private void toggleSeatSelection(String seatNumber, JButton seatBtn) {
        if (selectedSeats.contains(seatNumber)) {
            // Deselect
            selectedSeats.remove(seatNumber);
            seatBtn.setBackground(COLOR_AVAILABLE);
            seatBtn.setText(seatNumber);
            seatBtn.setForeground(COLOR_TEXT);
        } else {
            // Select
            selectedSeats.add(seatNumber);
            seatBtn.setBackground(COLOR_SELECTED);
            seatBtn.setText("");
            seatBtn.setForeground(Color.WHITE);
        }
        
        updateTotal();
    }
    
    private void updateTotal() {
        double total = selectedSeats.size() * selectedMovie.getPrice();
        totalPriceLabel.setText(String.format("RM %.2f", total));
        
        // Enable/disable book button based on selection
        bookButton.setEnabled(!selectedSeats.isEmpty());
    }
    
    private void confirmBooking() {
        if (selectedSeats.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please select at least one seat.", 
                "No Seats Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Open F&B selection window first
        new FBSelectionWindow(selectedMovie, currentUser, selectedSeats);
        this.dispose();
    }
}
