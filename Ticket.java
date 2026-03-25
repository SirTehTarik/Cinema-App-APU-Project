/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import javax.swing.SpinnerDateModel;
/**
 *
 * @author Mathew
 */
public class Ticket extends JFrame {
    
    // Method to check if seat is available
    private boolean isSeatAvailable(String movie, String seat) {
        List<TicketData> allBookings = FileHandler.loadAllBookings();
        for (TicketData t : allBookings) {
            // Check if seat is taken
            if (t.getMovieName().equalsIgnoreCase(movie) && t.getSeatNumber().equalsIgnoreCase(seat)) {
                return false;
            }
        }
        return true;   
    }
    
    // Method to save booking to file
    private void saveToFile(TicketData ticket) {
    try {
        FileHandler.addBooking(ticket);
        JOptionPane.showMessageDialog(this, "Booking Successful!");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "File Error: " + e.getMessage());
    }
}
    
    Ticket(){
    this.setTitle("Booking");
    this.setSize(500,550);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    JPanel panel = new JPanel();
    panel.setLayout(null);
    

    // 1. Customer Name
    JLabel cusLabel = new JLabel("Customer Name:");
    cusLabel.setBounds(20, 20, 150, 25);
    panel.add(cusLabel);
    JTextField cusText = new JTextField(20);
    cusText.setBounds(180, 20, 180, 25);
    panel.add(cusText);

    // 2. Customer Number
    JLabel numLabel = new JLabel("Customer Number:");
    numLabel.setBounds(20, 60, 150, 25);
    panel.add(numLabel);
    JTextField numText = new JTextField(20);
    numText.setBounds(180, 60, 180, 25);
    panel.add(numText);

    // 3. Movie Selection (DROPDOWN ONLY)
    JLabel movieLabel = new JLabel("Select Movie:");
    movieLabel.setBounds(20, 100, 150, 25);
    panel.add(movieLabel);
    JComboBox<String> movieBox = new JComboBox<>();
    movieBox.addItem("Select Movie...");
    movieBox.setBounds(180, 100, 180, 25);
    panel.add(movieBox);

    // 4. Booking Date (Calendar Picker)
    JLabel dateLabel = new JLabel("Booking Date:");
    dateLabel.setBounds(20, 140, 150, 25);
    panel.add(dateLabel);
    
    SpinnerDateModel dateModel = new SpinnerDateModel();
    JSpinner dateSpinner = new JSpinner(dateModel);
    JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
    dateSpinner.setEditor(dateEditor);
    dateSpinner.setBounds(180, 140, 180, 25);
    panel.add(dateSpinner);

    // 5. Booking Time (Combobox - will be populated based on selected movie)
    JLabel timeLabel = new JLabel("Booking Time:");
    timeLabel.setBounds(20, 170, 150, 25);
    panel.add(timeLabel);
    
    JComboBox<String> timeBox = new JComboBox<>();
    timeBox.addItem("Select Movie First");
    timeBox.setEnabled(false); // Disabled until movie is selected
    timeBox.setBounds(180, 170, 180, 25);
    panel.add(timeBox);

    // 6. Seat & Hall Selection (DROPDOWN FOR HALL)
    JLabel seatLabel = new JLabel("Seat #:");
    seatLabel.setBounds(20, 210, 60, 25);
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
    seatBox.setBounds(80, 210, 80, 25);
    panel.add(seatBox);

    JLabel hallLabel = new JLabel("Hall #:");
    hallLabel.setBounds(220, 210, 60, 25);
    panel.add(hallLabel);
    JComboBox<String> hallBox = new JComboBox<>(); // Removed hallText
    hallBox.setBounds(280, 210, 80, 25);
    panel.add(hallBox);

    // 7. Discount Category
    JLabel typeLabel = new JLabel("Discount Type:");
    typeLabel.setBounds(20, 250, 150, 25);
    panel.add(typeLabel);
    String[] types = {"Select Discount Type...", "Regular", "Student (10% Off)", "Senior (20% Off)"};
    JComboBox<String> typeBox = new JComboBox<>(types);
    typeBox.setBounds(180, 250, 180, 25);
    panel.add(typeBox);

    // 8. Total Amount (Manager's Base Price)
    JLabel priceLabel = new JLabel("Base Price (RM):");
    priceLabel.setBounds(20, 290, 150, 25);
    panel.add(priceLabel);
    JTextField priceText = new JTextField(10);
    priceText.setEditable(false); // Prevents Clerk from manually changing the base price
    priceText.setBounds(180, 290, 180, 25);
    panel.add(priceText);

    // 9. Payment Method
    JLabel payLabel = new JLabel("Payment Method:");
    payLabel.setBounds(20, 330, 150, 25);
    panel.add(payLabel);
    String[] methods = {"Select Payment...", "Cash", "Credit Card", "Digital Wallet"};
    JComboBox<String> payBox = new JComboBox<>(methods);
    payBox.setBounds(180, 330, 180, 25);
    panel.add(payBox);

    // --- LOGIC: POPULATE DROPDOWNS ---
    final java.util.List<Moviedata> existingMovies = FileHandler.getAllMovies();
    
    // Add only unique movie names to movieBox
    java.util.Set<String> uniqueMovies = new java.util.HashSet<>();
    for (Moviedata m : existingMovies) {
        uniqueMovies.add(m.getName());
    }
    for (String movieName : uniqueMovies) {
        movieBox.addItem(movieName);
    }

    // When Movie is selected, populate available showtimes
    movieBox.addActionListener(e -> {
        String selected = (String) movieBox.getSelectedItem();
        if (selected == null || selected.equals("Select Movie...")) {
            timeBox.removeAllItems();
            timeBox.addItem("Select Movie First");
            timeBox.setEnabled(false);
            hallBox.removeAllItems();
            hallBox.addItem("Select Time First");
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
        hallBox.removeAllItems();
        hallBox.addItem("Select Time First");
        priceText.setText("");
    });
    
    // When Time is selected, auto-fill Hall and Price
    timeBox.addActionListener(e -> {
        String selectedMovie = (String) movieBox.getSelectedItem();
        String selectedTime = (String) timeBox.getSelectedItem();
        
        if (selectedMovie == null || selectedMovie.equals("Select Movie...") ||
            selectedTime == null || selectedTime.equals("Select Time...")) {
            hallBox.removeAllItems();
            hallBox.addItem("Select Time First");
            priceText.setText("");
            return;
        }
        
        // Find the movie with matching name and time
        for (Moviedata m : existingMovies) {
            if (m.getName().equals(selectedMovie) && m.getTime().equals(selectedTime)) {
                hallBox.removeAllItems();
                hallBox.addItem(m.getHall());
                priceText.setText(String.format("%.2f", m.getPrice()));
                break;
            }
        }
    });

    // 10. Buttons
    JButton bookBtn = new JButton("Confirm Booking");
    bookBtn.setBounds(100, 390, 200, 40);
    panel.add(bookBtn);

    JButton Backbtn = new JButton("Go Back");
    Backbtn.setBounds(100, 440, 200, 40);
    panel.add(Backbtn);
      
    Backbtn.addActionListener(e ->{new ClerkDashboard(); this.dispose();});
        
        
        
       // Inside the "Confirm Booking" button listener:
      bookBtn.addActionListener(e -> {
    try {
        // 1. Get values from TextFields and ComboBoxes
        String name = cusText.getText().trim();
        String seat = (String) seatBox.getSelectedItem();
        
        // Use getSelectedItem() for the new ComboBoxes
        String movie = (String) movieBox.getSelectedItem();
        String hall = (String) hallBox.getSelectedItem();
        
        // Get date and time
        Date selectedDate = (Date) dateSpinner.getValue();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String bookingDate = dateFormat.format(selectedDate);
        String bookingTime = (String) timeBox.getSelectedItem();
        
        // Validation: Ensure movie and hall are actually selected
        if (movie == null || hall == null || name.isEmpty() || seat == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all details (Name, Movie, and Seat).", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double basePrice = Double.parseDouble(priceText.getText().trim());

        // 2. SEATING AVAILABILITY CHECK
        if (!isSeatAvailable(movie, seat)) {
            JOptionPane.showMessageDialog(this, "SEAT UNAVAILABLE: Seat " + seat + " is already taken for " + movie + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. APPLY DISCOUNT POLICY
        double finalPrice = basePrice;
        String selectedType = (String) typeBox.getSelectedItem();
        if (selectedType.contains("Student")) finalPrice *= 0.90;
        else if (selectedType.contains("Senior")) finalPrice *= 0.80;

        // 4. CREATE TICKET & SAVE
        TicketData currentTicket = new TicketData(name, movie, seat, hall, finalPrice);
        saveToFile(currentTicket);

        // 5. GENERATE RECEIPT
        String receipt = "------------------------------------------\n" +
                         "            CINEMA RECEIPT                \n" +
                         "------------------------------------------\n" +
                         "Booking ID: " + currentTicket.getBookingId() + "\n" +
                         "Customer: " + name + "\n" +
                         "Movie:    " + movie + "\n" +
                         "Date:     " + bookingDate + "\n" +
                         "Time:     " + bookingTime + "\n" +
                         "Hall:     " + hall + "\n" +
                         "Seat:     " + seat + "\n" +
                         "Category: " + selectedType + "\n" +
                         "Total:    RM " + String.format("%.2f", finalPrice) + "\n" +
                         "Payment:  " + payBox.getSelectedItem() + "\n" +
                         "------------------------------------------\n" +
                         "      THANK YOU FOR YOUR PURCHASE!        ";
        
        JOptionPane.showMessageDialog(this, receipt, "Payment Successful", JOptionPane.INFORMATION_MESSAGE);
        
        new ClerkDashboard();
        this.dispose();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Error: Base price is invalid. Please select a valid movie.");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage());
    }
});
      
      
    
    this.add(panel);
    this.setVisible(true);

    }
    
}
