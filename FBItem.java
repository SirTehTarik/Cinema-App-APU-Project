/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 * FBItem class - represents a food or beverage item
 * 
 * @author Mathew
 */
public class FBItem {
    private String name;
    private double price;
    private int quantity;
    
    // Constructor
    public FBItem(String name, double price) {
        this.name = (name != null) ? name.trim() : "Unknown";
        this.price = price;
        this.quantity = 0;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getTotal() {
        return price * quantity;
    }
    
    // Setters
    public void setName(String name) {
        this.name = (name != null) ? name.trim() : "Unknown";
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = Math.max(0, quantity);
    }
    
    public void incrementQuantity() {
        this.quantity++;
    }
    
    public void decrementQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }
    
    @Override
    public String toString() {
        return name + " (RM" + String.format("%.2f", price) + ")";
    }
}
