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

public class ModifyBookings extends JFrame {
    
    // Color constants matching BaseDashboard
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);
    
    public ModifyBookings() {
        setTitle("Modify Booking");
        setSize(820, 280);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(760, 260));
        getContentPane().setBackground(COLOR_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JComboBox<TicketData> bookingBox = new JComboBox<>();
        bookingBox.addItem(null);
        bookingBox.setPreferredSize(new Dimension(540, 28));
        List<TicketData> bookings = FileHandler.loadAllBookings();
        for (TicketData booking : bookings) {
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

        JButton updateBtn = new JButton("Open Seat Map");
        styleButton(updateBtn, COLOR_PRIMARY);
        
        JButton backBtn = new JButton("Back");
        styleButton(backBtn, COLOR_PRIMARY);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; 
        JLabel label1 = new JLabel("Booking ID:");
        label1.setFont(new Font("Arial", Font.BOLD, 11));
        label1.setForeground(COLOR_TEXT);
        add(label1, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1; add(bookingBox, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; add(backBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1; add(updateBtn, gbc);

        updateBtn.addActionListener(e -> {
            TicketData selectedTicket = (TicketData) bookingBox.getSelectedItem();

            if (selectedTicket == null) {
                JOptionPane.showMessageDialog(this, "Please select a Booking ID.");
                return;
            }

            // Create a fake user context for the Clerk so the Map knows where to return
            Users clerkUser = new Users("Clerk", "", "Clerk", "Clerk");
            new ModifySeatMap(selectedTicket, clerkUser);
            this.dispose();
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
}