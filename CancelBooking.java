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
import java.awt.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class CancelBooking extends JFrame {
    
    // Color constants matching BaseDashboard
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_DANGER = new Color(231, 76, 60);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);
    
    public CancelBooking() {
        setTitle("Cancel Booking");
        setSize(820, 250);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(760, 240));
        getContentPane().setBackground(COLOR_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Booking ID to Cancel:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 11));
        nameLabel.setForeground(COLOR_TEXT);
        
        // Load existing bookings and populate combobox
        JComboBox<TicketData> bookingBox = new JComboBox<>();
        bookingBox.addItem(null);
        bookingBox.setPreferredSize(new Dimension(540, 28));
        List<TicketData> allBookings = FileHandler.loadAllBookings();
        for (TicketData booking : allBookings) {
            bookingBox.addItem(booking);
        }

        bookingBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("Select Booking ID...");
                } else {
                    TicketData ticket = (TicketData) value;
                    setText(ticket.getBookingId() + " | " + ticket.getCustomerName() + " | " + ticket.getMovieName() + " | " + ticket.getSeatNumber());
                }
                return this;
            }
        });
        
        JButton backBtn = new JButton("Back");
        styleButton(backBtn, COLOR_DANGER);
        
        JButton cancelBtn = new JButton("Confirm Cancellation");
        styleButton(cancelBtn, COLOR_PRIMARY);

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1; add(bookingBox, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; add(backBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1; add(cancelBtn, gbc);

        // Action: Cancel Logic
        cancelBtn.addActionListener(e -> {
            TicketData selectedTicket = (TicketData) bookingBox.getSelectedItem();
            
            if (selectedTicket == null) {
                JOptionPane.showMessageDialog(this, "Please select a Booking ID to cancel.");
                return;
            }
            
            List<TicketData> currentBookings = FileHandler.loadAllBookings();
            String bookingIdToCancel = selectedTicket.getBookingId();
            
            // Remove the ticket object by Booking ID
            boolean removed = currentBookings.removeIf(t -> t.getBookingId().equalsIgnoreCase(bookingIdToCancel));

            if (removed) {
                FileHandler.saveAllBookings(currentBookings);
                
                // Also remove associated F&B orders
                removeFBOrdersByBookingId(bookingIdToCancel);
                
                JOptionPane.showMessageDialog(this, "Booking " + bookingIdToCancel + " and associated F&B items have been cancelled.");
                new ClerkDashboard();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No booking found for that Booking ID.");
            }
        });

        backBtn.addActionListener(e -> {
            new ClerkDashboard();
            this.dispose();
        });

        setVisible(true);
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
    }
    
    private void removeFBOrdersByBookingId(String bookingId) {
        java.io.File fbFile = new java.io.File("FoodBeveragesOrders.txt");
        if (!fbFile.exists()) return;

        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fbFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                // Keep only lines that don't match the booking ID
                if (parts.length > 0 && !parts[0].equalsIgnoreCase(bookingId)) {
                    content.append(line).append("\n");
                }
            }
        } catch (java.io.IOException e) {
            // F&B file may not exist or have no orders
            return;
        }

        // Write back the modified content
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fbFile))) {
            bw.write(content.toString());
        } catch (java.io.IOException e) {
            System.err.println("Error updating F&B orders: " + e.getMessage());
        }
    }
}