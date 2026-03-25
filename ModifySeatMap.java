package CinemaAPP;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ModifySeatMap extends JFrame {

    private TicketData originalTicket;
    private Users currentUser;
    
    private String newlySelectedSeat = null;
    private Map<String, JButton> seatButtons = new HashMap<>();
    private JButton confirmButton;

    private static final int ROWS = 10;
    private static final int COLS = 13;
    
    private static final Color COLOR_AVAILABLE = new Color(52, 152, 219); // Blue
    private static final Color COLOR_SELECTED = new Color(46, 204, 113); // Green
    private static final Color COLOR_SOLD = new Color(231, 76, 60); // Red
    private static final Color COLOR_CURRENT = new Color(155, 89, 182); // Purple
    private static final Color COLOR_BG = new Color(32, 32, 32); 
    private static final Color COLOR_TEXT = Color.WHITE;

    public ModifySeatMap(TicketData originalTicket, Users currentUser) {
        this.originalTicket = originalTicket;
        this.currentUser = currentUser;

        // Setup UI right away
        
        setupUI();
    }

    private void setupUI() {
        this.setTitle("Modify Seat - " + originalTicket.getMovieName());
        this.setSize(900, 850);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(COLOR_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_BG);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        JLabel stepLabel = new JLabel("Select New Seat");
        stepLabel.setForeground(COLOR_TEXT);
        stepLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JButton closeButton = new JButton("X");
        closeButton.setBackground(COLOR_BG);
        closeButton.setForeground(COLOR_TEXT);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.addActionListener(e -> returnToPrevious());
        
        headerPanel.add(stepLabel, BorderLayout.WEST);
        headerPanel.add(closeButton, BorderLayout.EAST);
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Movie Details Panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(COLOR_BG);
        detailsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        String details = String.format("<html><b>%s</b><br/><font color='#b4b4b4'>Hall: %s | Customer: %s | Booking ID: %s</font></html>", 
                originalTicket.getMovieName(),
                originalTicket.getHall(),
                originalTicket.getCustomerName(),
                originalTicket.getBookingId());
        JLabel detailsLabel = new JLabel(details);
        detailsLabel.setForeground(COLOR_TEXT);
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsPanel.add(detailsLabel, BorderLayout.WEST);
        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Legend Section
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        legendPanel.setBackground(COLOR_BG);
        legendPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        legendPanel.add(createLegendItem("CURRENT", COLOR_CURRENT));
        legendPanel.add(createLegendItem("AVAILABLE", COLOR_AVAILABLE));
        legendPanel.add(createLegendItem("NEW SELECTED", COLOR_SELECTED));
        legendPanel.add(createLegendItem("SOLD", COLOR_SOLD));
        mainPanel.add(legendPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Screen Area
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new BoxLayout(screenPanel, BoxLayout.Y_AXIS));
        screenPanel.setBackground(COLOR_BG);
        screenPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        JLabel screenLabel = new JLabel("SCREEN");
        screenLabel.setForeground(new Color(120, 120, 120));
        screenLabel.setFont(new Font("Arial", Font.BOLD, 12));
        screenLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(100, 100, 100));
        separator.setMaximumSize(new Dimension(700, 2));
        screenPanel.add(separator);
        screenPanel.add(Box.createVerticalStrut(5));
        screenPanel.add(screenLabel);
        mainPanel.add(screenPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Seat Map Section
        JScrollPane scrollPane = new JScrollPane(createSeatMapPanel());
        scrollPane.setPreferredSize(new Dimension(850, 450));
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(COLOR_BG);
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalStrut(15));

        // Footer Section
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(COLOR_BG);
        footerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        confirmButton = new JButton("Confirm Seat Change");
        confirmButton.setBackground(COLOR_SELECTED);
        confirmButton.setForeground(COLOR_TEXT);
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setFocusPainted(false);
        confirmButton.setPreferredSize(new Dimension(250, 45));
        confirmButton.setEnabled(false);
        confirmButton.addActionListener(e -> updateSeatInSystem());
        
        footerPanel.add(confirmButton);
        mainPanel.add(footerPanel);

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setBorder(null);
        mainScrollPane.getViewport().setBackground(COLOR_BG);
        this.add(mainScrollPane);

        this.setVisible(true);
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

    private JPanel createSeatMapPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        
        Set<String> soldSeats = getSoldSeats();
        String currentSeat = originalTicket.getSeatNumber();
        
        char[] rowLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K'};
        
        for (int row = 0; row < ROWS; row++) {
            char rowLetter = rowLetters[row];
            
            gbc.gridx = 0; gbc.gridy = row;
            JLabel leftLabel = new JLabel(String.valueOf(rowLetter));
            leftLabel.setForeground(new Color(150, 150, 150));
            leftLabel.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(leftLabel, gbc);
            
            for (int col = 0; col < COLS; col++) {
                gbc.gridx = col + 1;
                String seatNumber = rowLetter + String.valueOf(col + 1);
                
                JButton seatBtn = new JButton(seatNumber);
                seatBtn.setPreferredSize(new Dimension(50, 40));
                seatBtn.setFont(new Font("Arial", Font.BOLD, 9));
                seatBtn.setFocusPainted(false);
                seatBtn.setBorderPainted(false);
                seatBtn.setMargin(new Insets(0, 0, 0, 0));
                
                if (seatNumber.equalsIgnoreCase(currentSeat)) {
                    seatBtn.setBackground(COLOR_CURRENT);
                    seatBtn.setForeground(COLOR_TEXT);
                    seatBtn.setEnabled(false);
                } else if (soldSeats.contains(seatNumber)) {
                    seatBtn.setBackground(COLOR_SOLD);
                    seatBtn.setForeground(Color.WHITE);
                    seatBtn.setEnabled(false);
                } else {
                    seatBtn.setBackground(COLOR_AVAILABLE);
                    seatBtn.setForeground(COLOR_TEXT);
                    seatBtn.addActionListener(e -> selectSeat(seatNumber, seatBtn));
                }
                
                seatButtons.put(seatNumber, seatBtn);
                panel.add(seatBtn, gbc);
            }
            
            gbc.gridx = COLS + 1;
            JLabel rightLabel = new JLabel(String.valueOf(rowLetter));
            rightLabel.setForeground(new Color(150, 150, 150));
            rightLabel.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(rightLabel, gbc);
        }
        
        return panel;
    }

    private Set<String> getSoldSeats() {
        Set<String> soldSeats = new HashSet<>();
        List<TicketData> allBookings = FileHandler.loadAllBookings();
        for (TicketData booking : allBookings) {
            if (booking.getMovieName().equalsIgnoreCase(originalTicket.getMovieName()) &&
                booking.getHall().equalsIgnoreCase(originalTicket.getHall())) {
                soldSeats.add(booking.getSeatNumber());
            }
        }
        return soldSeats;
    }

    private void selectSeat(String seatNumber, JButton seatBtn) {
        // Reset previous selection if any
        if (newlySelectedSeat != null) {
            JButton prevBtn = seatButtons.get(newlySelectedSeat);
            if (prevBtn != null) {
                prevBtn.setBackground(COLOR_AVAILABLE);
            }
        }
        
        newlySelectedSeat = seatNumber;
        seatBtn.setBackground(COLOR_SELECTED);
        confirmButton.setEnabled(true);
    }

    private void updateSeatInSystem() {
        if (newlySelectedSeat == null) return;

        List<TicketData> allBookings = FileHandler.loadAllBookings();
        boolean found = false;

        for (TicketData t : allBookings) {
            if (t.getBookingId().equals(originalTicket.getBookingId())) {
                t.setSeatNumber(newlySelectedSeat);
                found = true;
                break;
            }
        }

        if (found) {
            FileHandler.saveAllBookings(allBookings);
            JOptionPane.showMessageDialog(this, 
                "Successfully changed seat from " + originalTicket.getSeatNumber() + " to " + newlySelectedSeat,
                "Seat Updated", 
                JOptionPane.INFORMATION_MESSAGE);
            returnToPrevious();
        } else {
            JOptionPane.showMessageDialog(this, "Could not find booking in system to update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnToPrevious() {
        if (currentUser == null || currentUser.getRole().equals("Guest") || currentUser.getRole().equals("Clerk")) {
            new ClerkDashboard();
        } else if (currentUser.getRole().equals("Manager")) {
            new ManagerDashboard();
        } else {
            // Customer
            new CustomerHistory(currentUser);
        }
        this.dispose();
    }
}
