/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 * Users class - parent class for all user types
 * 
 * @author Mathew
 */
public class Users implements FilePersistable {
    // Private variables - only accessible through getters/setters
    private String username;
    private String password;
    private String role;      // Manager, Clerk, or Customer
    private String fullName;  // User's full name

    // Constructor - creates a new user
    public Users(String username, String password, String role, String fullName) {
        this.username = (username != null) ? username.trim() : "Unknown";
        this.password = (password != null) ? password.trim() : "Unknown";
        this.role = (role != null) ? role.trim() : "Clerk"; // Default to Clerk for safety
        this.fullName = (fullName != null) ? fullName.trim() : "Unknown";
    }

    // Getter methods
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    // Setter methods
    public void setUsername(String username) {
        this.username = (username != null) ? username.trim() : "Unknown";
    }
    
    public void setPassword(String password) {
        this.password = (password != null) ? password.trim() : "Unknown";
    }
    
    public void setRole(String role) {
        this.role = (role != null) ? role.trim() : "Clerk";
    }
    
    public void setFullName(String fullName) {
        this.fullName = (fullName != null) ? fullName.trim() : "Unknown";
    }

    // Method to convert user data to string for file saving
    @Override
    public String toFileString() {
        return username + "," + password + "," + role + "," + fullName;
    }
}