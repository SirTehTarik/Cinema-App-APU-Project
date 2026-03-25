/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 * Customer class - extends Users parent class
 * 
 * @author Mathew
 */
public class Customer extends Users {
    
    private String phoneNumber;
    private String email;
    
    // Constructor 1 - basic customer creation
    public Customer(String username, String password, String fullName) {
        super(username, password, "Customer", fullName);
    }
    
    // Constructor 2 - customer with phone and email (method overloading)
    public Customer(String username, String password, String fullName, String phoneNumber, String email) {
        super(username, password, "Customer", fullName);
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    // Customer can view and book movies
    public void viewAndBookMovies() {
        new CustomerBooking(this);
    }
    
    // Customer can view their bookings
    public void viewMyBookings() {
        new CustomerHistory(this);
    }
    
    // Customer can update profile
    public void updateProfile() {
        // Could open profile update window
    }
    
    // Getters and setters for customer-specific fields
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    // Override toString method
    @Override
    public String toString() {
        return "Customer: " + getFullName() + " (" + getUsername() + ")";
    }
}
