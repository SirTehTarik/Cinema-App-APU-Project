/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 * Manager class - extends Users parent class
 * 
 * @author Mathew
 */
public class Manager extends Users {
    
    // Constructor
    public Manager(String username, String password, String fullName) {
        super(username, password, "Manager", fullName);
    }
    
    // Manager can view revenue reports
    public void viewRevenueReports() {
        new ManagerReports();
    }
    
    // Manager can manage users
    public void manageUsers() {
        new ManageUsers();
    }
    
    // Manager can manage movies
    public void manageMovies() {
        // Could open a movie management window
    }
    
    // Override toString method
    @Override
    public String toString() {
        return "Manager: " + getFullName() + " (" + getUsername() + ")";
    }
}
