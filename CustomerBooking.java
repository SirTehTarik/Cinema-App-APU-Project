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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CustomerBooking extends JFrame {

    private Users currentUser;

    // Helper method to check seating availability
    private boolean isSeatAvailable(String movie, String seat) {
        List<TicketData> allBookings = FileHandler.loadAllBookings();
        for (TicketData t : allBookings) {
            if (t.getMovieName().equalsIgnoreCase(movie) && t.getSeatNumber().equalsIgnoreCase(seat)) {
                return false;
            }
        }
        return true;   
    }

    public CustomerBooking(Users currentUser) {
        this.currentUser = currentUser;
        this.setTitle("Book Tickets - " + currentUser.getFullName());
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // --- SECTION 1: VIEW CURRENTLY SHOWING MOVIES ---
        JLabel moviesLabel = new JLabel("Currently Showing Movies:");
        moviesLabel.setBounds(20, 10, 200, 25);
        panel.add(moviesLabel);

        // Setup Table to display movies
        String[] columns = {"Movie Name", "Hall", "Showtime", "Price (RM)"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable movieTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.setBounds(20, 40, 540, 150);
        panel.add(scrollPane);

        // Load movies into the table
        List<Moviedata> existingMovies = FileHandler.getAllMovies();
        for (Moviedata m : existingMovies) {
            Object[] row = {m.getName(), m.getHall(), m.getTime(), String.format(java.util.Locale.US, "%.2f", m.getPrice())};
            tableModel.addRow(row);
        }

        // --- SECTION 2: BOOKING FORM ---
        JLabel bookLabel = new JLabel("Book Your Seat:");
        bookLabel.setBounds(20, 210, 200, 25);
        panel.add(bookLabel);

        JLabel movieComboLabel = new JLabel("Select Movie:");
        movieComboLabel.setBounds(20, 240, 100, 25);
        panel.add(movieComboLabel);
        JComboBox<String> movieBox = new JComboBox<>();
        movieBox.addItem("Select Movie...");
        movieBox.setBounds(130, 240, 180, 25);
        
        // Add only unique movie names
        java.util.Set<String> uniqueMovies = new java.util.HashSet<>();
        for (Moviedata m : existingMovies) {
            uniqueMovies.add(m.getName());
        }
        for (String movieName : uniqueMovies) {
            movieBox.addItem(movieName);
        }
        panel.add(movieBox);

        JLabel hallLabel = new JLabel("Hall:");
        hallLabel.setBounds(330, 240, 40, 25);
        panel.add(hallLabel);
        JTextField hallText = new JTextField();
        hallText.setEditable(false);
        hallText.setBounds(380, 240, 80, 25);
        panel.add(hallText);

        JLabel priceLabel = new JLabel("Price (RM):");
        priceLabel.setBounds(330, 280, 80, 25);
        panel.add(priceLabel);
        JTextField priceText = new JTextField();
        priceText.setEditable(false);
        priceText.setBounds(410, 280, 80, 25);
        panel.add(priceText);

        JLabel seatLabel = new JLabel("Seat #:");
        seatLabel.setBounds(20, 280, 100, 25);
        panel.add(seatLabel);
        
        // Generate seat numbers (A1-A13, B1-B13, etc., skipping row I)
        String[] seats = new String[131]; // 10 rows × 13 seats + 1 for "Select Seat..."
        seats[0] = "Select Seat...";
        int index = 1;
        char[] rowLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K'};
        for (char row : rowLetters) {
            for (int num = 1; num <= 13; num++) {
                seats[index++] = row + String.valueOf(num);
            }
        }
        JComboBox<String> seatBox = new JComboBox<>(seats);
        seatBox.setBounds(130, 280, 100, 25);
        panel.add(seatBox);

        // Booking Date (Calendar Picker)
        JLabel dateLabel = new JLabel("Booking Date:");
        dateLabel.setBounds(20, 320, 100, 25);
        panel.add(dateLabel);
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setBounds(130, 320, 180, 25);
        panel.add(dateSpinner);

        // Booking Time (Combobox - will be populated based on selected movie)
        JLabel timeLabel = new JLabel("Booking Time:");
        timeLabel.setBounds(330, 320, 100, 25);
        panel.add(timeLabel);
        
        JComboBox<String> timeBox = new JComboBox<>();
        timeBox.addItem("Select Movie First");
        timeBox.setEnabled(false);
        timeBox.setBounds(430, 320, 120, 25);
        panel.add(timeBox);
        
        // When Movie is selected, populate available showtimes
        movieBox.addActionListener(e -> {
            String selected = (String) movieBox.getSelectedItem();
            if (selected == null || selected.equals("Select Movie...")) {
                timeBox.removeAllItems();
                timeBox.addItem("Select Movie First");
                timeBox.setEnabled(false);
                hallText.setText("");
                priceText.setText("");
                return;
            }
            
            // Populate timeBox with available showtimes for this movie
            timeBox.removeAllItems();
            timeBox.addItem("Select Time...");
            for (Moviedata m : existingMovies) {
                if (m.getName().equals(selected)) {
                    timeBox.addItem(m.getTime());
                }
            }
            timeBox.setEnabled(true);
            
            // Clear hall and price until time is selected
            hallText.setText("");
            priceText.setText("");
        });
        
        // When Time is selected, auto-fill Hall and Price
        timeBox.addActionListener(e -> {
            String selectedMovie = (String) movieBox.getSelectedItem();
            String selectedTime = (String) timeBox.getSelectedItem();
            
            if (selectedMovie == null || selectedMovie.equals("Select Movie...") ||
                selectedTime == null || selectedTime.equals("Select Time...")) {
                hallText.setText("");
                priceText.setText("");
                return;
            }
            
            // Find the movie with matching name and time
            for (Moviedata m : existingMovies) {
                if (m.getName().equals(selectedMovie) && m.getTime().equals(selectedTime)) {
                    hallText.setText(m.getHall());
                    priceText.setText(String.format(java.util.Locale.US, "%.2f", m.getPrice()));
                    break;
                }
            }
        });

        JLabel payLabel = new JLabel("Payment:");
        payLabel.setBounds(20, 360, 100, 25);
        panel.add(payLabel);
        String[] methods = {"Select Payment...", "Credit Card", "Online Banking", "E-Wallet"};
        JComboBox<String> payBox = new JComboBox<>(methods);
        payBox.setBounds(130, 360, 180, 25);
        panel.add(payBox);

        JButton confirmBtn = new JButton("Confirm Booking");
        confirmBtn.setBounds(150, 420, 300, 40);
        panel.add(confirmBtn);

        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.setBounds(150, 470, 300, 40);
        panel.add(backBtn);

        // --- BUTTON LOGIC ---
        backBtn.addActionListener(e -> {
            new CustomerDashboard(currentUser);
            this.dispose();
        });

        confirmBtn.addActionListener(e -> {
            try{
                String movie = (String) movieBox.getSelectedItem();
                String hall = hallText.getText();
                String seat = (String) seatBox.getSelectedItem();
                
                // Get date and time
                Date selectedDate = (Date) dateSpinner.getValue();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String bookingDate = dateFormat.format(selectedDate);
                String bookingTime = (String) timeBox.getSelectedItem();
                
                if (seat == null || movie == null || hall.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please select a movie and a seat.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!isSeatAvailable(movie, seat)) {
                    JOptionPane.showMessageDialog(this, "Seat " + seat + " is already taken! Please choose another.", "Unavailable", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double finalPrice = Double.parseDouble(priceText.getText());
                

                // Save to file
                // Create ticket using the CUSTOMER'S name automatically
                TicketData newTicket = new TicketData(currentUser.getFullName(), movie, seat, hall, finalPrice);

                // Save to file using FileHandler
                FileHandler.addBooking(newTicket);

                String receipt = "------------------------------------------\n" +
                         "            CINEMA RECEIPT                \n" +
                         "------------------------------------------\n" +
                         "Booking ID: " + newTicket.getBookingId() + "\n" +
                         "Customer: " + currentUser.getFullName() + "\n" +
                         "Movie:    " + movie + "\n" +
                         "Date:     " + bookingDate + "\n" +
                         "Time:     " + bookingTime + "\n" +
                         "Hall:     " + hall + "\n" +
                         "Seat:     " + seat + "\n" +
                         "Total:    RM " + String.format("%.2f", finalPrice) + "\n" +
                         "Payment:  " + payBox.getSelectedItem() + "\n" +
                         "------------------------------------------\n" +
                         "      THANK YOU FOR YOUR PURCHASE!        ";

                JOptionPane.showMessageDialog(this, receipt, "Booking Successful", JOptionPane.INFORMATION_MESSAGE);
                new CustomerDashboard(currentUser);
                this.dispose();
       
                } catch (NumberFormatException ex) {
            // This catches the error if the price isn't a valid number
            JOptionPane.showMessageDialog(this, "Error calculating price. Please select the movie again.", "Price Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception ex) {
            // This acts as a safety net for any other random crashes
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "System Error", JOptionPane.ERROR_MESSAGE);
        }
        });

        this.add(panel);
        this.setVisible(true);
    }
}