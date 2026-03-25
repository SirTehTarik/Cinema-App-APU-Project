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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CustomerHistory extends JFrame {

    private Users currentUser;

    public CustomerHistory(Users currentUser) {
        this.currentUser = currentUser;
        this.setTitle("My Booking History - " + currentUser.getFullName());
        this.setSize(900, 550);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("My Bookings");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel subtitleLabel = new JLabel("View and manage your booked tickets");
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(52, 73, 94));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Setup table
        String[] columns = { "Booking ID", "Date", "Movie", "Hall", "Seat", "Price (RM)" };
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable historyTable = new JTable(tableModel);
        historyTable.setFont(new Font("Arial", Font.PLAIN, 13));
        historyTable.setRowHeight(30);
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        historyTable.getTableHeader().setBackground(new Color(41, 128, 185));
        historyTable.getTableHeader().setForeground(Color.WHITE);
        historyTable.setSelectionBackground(new Color(52, 152, 219));
        historyTable.setSelectionForeground(Color.WHITE);

        // Set column widths
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(120); // Booking ID
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(140); // Date
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(180); // Movie
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(80); // Hall
        historyTable.getColumnModel().getColumn(4).setPreferredWidth(80); // Seat
        historyTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Price

        JScrollPane scrollPane = new JScrollPane(historyTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Load ONLY this customer's tickets
        List<TicketData> allBookings = FileHandler.loadAllBookings();
        for (TicketData t : allBookings) {
            // Check if the ticket belongs to the logged-in user
            if (t.getCustomerName().equalsIgnoreCase(currentUser.getFullName())) {
                String dateStr = t.getDate();
                Object[] row = {
                        t.getBookingId(),
                        dateStr,
                        t.getMovieName(),
                        t.getHall(),
                        t.getSeatNumber(),
                        String.format(java.util.Locale.US, "%.2f", t.getPrice())
                };
                tableModel.addRow(row);
            }
        }

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(236, 240, 241));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel infoLabel = new JLabel(
                "Total Bookings: " + tableModel.getRowCount() + " | Select a booking to modify or cancel");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(52, 73, 94));
        infoPanel.add(infoLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton receiptBtn = new JButton("View Receipt");
        receiptBtn.setFont(new Font("Arial", Font.BOLD, 13));
        receiptBtn.setBackground(new Color(52, 152, 219));
        receiptBtn.setForeground(Color.WHITE);
        receiptBtn.setFocusPainted(false);
        receiptBtn.setPreferredSize(new Dimension(140, 40));

        JButton modifyBtn = new JButton("Modify Seat");
        modifyBtn.setFont(new Font("Arial", Font.BOLD, 13));
        modifyBtn.setBackground(new Color(46, 204, 113));
        modifyBtn.setForeground(Color.WHITE);
        modifyBtn.setFocusPainted(false);
        modifyBtn.setPreferredSize(new Dimension(130, 40));

        JButton cancelBtn = new JButton("Cancel Booking");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 13));
        cancelBtn.setBackground(new Color(231, 76, 60));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setPreferredSize(new Dimension(140, 40));

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn.setBackground(new Color(149, 165, 166));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setPreferredSize(new Dimension(100, 40));

        buttonPanel.add(receiptBtn);
        buttonPanel.add(modifyBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(backBtn);

        // Layout
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
        this.setVisible(true);

        // --- BUTTON LOGIC ---
        receiptBtn.addActionListener(e -> {
            int selectedRow = historyTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking to view its receipt.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedBookingId = (String) tableModel.getValueAt(selectedRow, 0);
            String selectedDate = (String) tableModel.getValueAt(selectedRow, 1);
            String selectedMovie = (String) tableModel.getValueAt(selectedRow, 2);
            String selectedHall = (String) tableModel.getValueAt(selectedRow, 3);
            String selectedSeat = (String) tableModel.getValueAt(selectedRow, 4);
            String priceStr = (String) tableModel.getValueAt(selectedRow, 5);

            // Load F&B items for this booking
            List<String> fbItems = loadFBItemsForBooking(selectedBookingId);
            
            // Build F&B section of receipt
            String fbSection = "";
            if (!fbItems.isEmpty()) {
                fbSection = "\n------------------------------------\n" +
                           "  FOOD & BEVERAGES                 \n" +
                           "------------------------------------\n";
                for (String fbItem : fbItems) {
                    fbSection += fbItem + "\n";
                }
            }

            String receiptMessage = String.format(
                    "====================================\n" +
                    "           CINEMA E-RECEIPT           \n" +
                    "====================================\n\n" +
                    "  Booking ID : %s\n" +
                    "  Date/Time  : %s\n\n" +
                    "------------------------------------\n" +
                    "  Customer   : %s\n" +
                    "  Movie      : %s\n" +
                    "  Hall       : %s\n" +
                    "  Seat       : %s\n\n" +
                    "------------------------------------\n" +
                    "  TOTAL PAID : RM %s\n%s" +
                    "====================================\n" +
                    " Thank you for booking with us!\n" +
                    " Please present this at the counter.",
                    selectedBookingId,
                    selectedDate,
                    currentUser.getFullName(),
                    selectedMovie,
                    selectedHall,
                    selectedSeat,
                    priceStr,
                    fbSection);

            JOptionPane.showMessageDialog(this,
                    receiptMessage,
                    "Generated e-Receipt",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        backBtn.addActionListener(e -> {
            new CustomerDashboard(currentUser);
            this.dispose();
        });

        modifyBtn.addActionListener(e -> {
            int selectedRow = historyTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a booking to modify.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get data from the selected row
            String selectedBookingId = (String) tableModel.getValueAt(selectedRow, 0);

            TicketData ticketToModify = null;
            for (TicketData t : allBookings) {
                if (t.getBookingId().equals(selectedBookingId)) {
                    ticketToModify = t;
                    break;
                }
            }

            if (ticketToModify != null) {
                new ModifySeatMap(ticketToModify, currentUser);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> {
            int selectedRow = historyTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a ticket from the table to cancel.", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get data from the selected row
            String selectedBookingId = (String) tableModel.getValueAt(selectedRow, 0);
            String selectedMovie = (String) tableModel.getValueAt(selectedRow, 2);
            String selectedSeat = (String) tableModel.getValueAt(selectedRow, 4);

            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to cancel seat " + selectedSeat + " for " + selectedMovie + "?",
                    "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Find the exact ticket in the master list and remove it
                for (int i = 0; i < allBookings.size(); i++) {
                    TicketData t = allBookings.get(i);
                    if (t.getCustomerName().equalsIgnoreCase(currentUser.getFullName()) &&
                            t.getBookingId().equals(selectedBookingId) &&
                            t.getMovieName().equals(selectedMovie) &&
                            t.getSeatNumber().equals(selectedSeat)) {

                        allBookings.remove(i);
                        break; // Stop searching once we remove it
                    }
                }

                // Save the updated list back to the text file
                FileHandler.saveAllBookings(allBookings);

                // Remove the row from the GUI table so it visually updates immediately
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Booking Cancelled Successfully.");
            }
        });
    }
    
    // Method to load F&B items for a specific booking
    private List<String> loadFBItemsForBooking(String bookingId) {
        List<String> items = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("FoodBeveragesOrders.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && parts[0].equals(bookingId)) {
                    // Format: BookingID|ItemName|Quantity|UnitPrice|TotalPrice
                    String itemName = parts[1].trim();
                    String quantity = parts[2].trim();
                    String unitPrice = parts[3].trim();
                    String totalPrice = parts[4].trim();
                    
                    String fbLine = String.format("  %sx %s (RM %s each) = RM %s", 
                        quantity, itemName, unitPrice, totalPrice);
                    items.add(fbLine);
                }
            }
            reader.close();
        } catch (Exception e) {
            // File might not exist or has no data - that's fine
            System.out.println("No F&B items found for booking: " + bookingId);
        }
        return items;
    }
}

