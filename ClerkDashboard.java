/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

import javax.swing.*;
import java.awt.Color;

/**
 * ClerkDashboard extends BaseDashboard - demonstrates inheritance
 * Clerk-specific dashboard with booking management functionality
 * 
 * @author Mathew
 */
public class ClerkDashboard extends BaseDashboard {
    
    ClerkDashboard() {
        // Call parent constructor with specific title
        super("Clerk Dashboard");
        // Display the dashboard (calls setupButtons via template method)
        display();
    }
    
    /**
     * Implementation of abstract method from BaseDashboard
     * This demonstrates polymorphism - each dashboard has its own button setup
     */
    @Override
    protected void setupButtons() {
        // Button 1: BOOK TICKET (Visual Seat Booking)
        JButton button1 = addButton("BOOK TICKET", 0, COLOR_SUCCESS);
        button1.addActionListener(e -> {
            new ClerkBookingInput();
            this.dispose();
        });
        
        // Button 2: MODIFY BOOKINGS
        JButton button2 = addButton("MODIFY TICKET", 1, COLOR_INFO);
        button2.addActionListener(e -> {new ModifyBookings(); this.dispose();});

        // Button 3: CANCEL BOOKING
        JButton button3 = addButton("CANCEL BOOKING", 2, COLOR_WARNING);
        button3.addActionListener(e -> {new CancelBooking(); this.dispose();});
        
        // Button 4: VIEW ALL BOOKINGS
        JButton button4 = addButton("VIEW BOOKINGS", 3, COLOR_PRIMARY);
        button4.addActionListener(e -> {new ViewBookings(); this.dispose();});

        // Button 5: UPCOMING MOVIES
        JButton buttonUpcoming = addButton("UPCOMING MOVIES", 4, new Color(155, 89, 182));
        buttonUpcoming.addActionListener(e -> {new ViewUpcomingMovies(null); this.dispose();});

        // Button 6: LOGOUT
        JButton button5 = addButton("LOGOUT", 5, COLOR_SECONDARY);
        button5.addActionListener(e -> {new Homepage(); this.dispose();});
    }
}
