/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 * Clerk class - extends Users parent class
 * 
 * @author Mathew
 */
public class Clerk extends Users {
    
    // Constructor
    public Clerk(String username, String password, String fullName) {
        super(username, password, "Clerk", fullName);
    }
    
    // Clerk can book tickets
    public void bookTicket() {
        new Ticket();
    }
    
    // Clerk can modify bookings
    public void modifyBooking() {
        new ModifyBookings();
    }
    
    // Clerk can cancel bookings
    public void cancelBooking() {
        new CancelBooking();
    }
    
    // Clerk can view all bookings
    public void viewAllBookings() {
        new ViewBookings();
    }
    
    // Override toString method
    @Override
    public String toString() {
        return "Clerk: " + getFullName() + " (" + getUsername() + ")";
    }
}
