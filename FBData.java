/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

import java.util.ArrayList;
import java.util.List;

/**
 * FBData class - stores food and beverage order information
 * 
 * @author Mathew
 */
public class FBData implements FilePersistable {
    private String orderId;
    private String bookingId;
    private String customerName;
    private List<FBItem> items;
    private double totalPrice;
    private String date;
    
    // Constructor
    public FBData(String bookingId, String customerName) {
        this.orderId = "FB-" + System.currentTimeMillis();
        this.bookingId = bookingId;
        this.customerName = (customerName != null) ? customerName.trim() : "Unknown";
        this.items = new ArrayList<>();
        this.totalPrice = 0;
        this.date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }
    
    // Getters
    public String getOrderId() {
        return orderId;
    }
    
    public String getBookingId() {
        return bookingId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public List<FBItem> getItems() {
        return items;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public String getDate() {
        return date;
    }
    
    // Add item to order
    public void addItem(FBItem item) {
        if (item != null && item.getQuantity() > 0) {
            items.add(item);
            calculateTotal();
        }
    }
    
    // Remove item from order
    public void removeItem(String itemName) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
        calculateTotal();
    }
    
    // Calculate total price
    private void calculateTotal() {
        this.totalPrice = 0;
        for (FBItem item : items) {
            this.totalPrice += item.getTotal();
        }
    }
    
    // Get order summary
    public String getOrderSummary() {
        StringBuilder summary = new StringBuilder();
        for (FBItem item : items) {
            if (item.getQuantity() > 0) {
                summary.append(item.getQuantity())
                       .append("x ")
                       .append(item.getName())
                       .append(" (RM ")
                       .append(String.format("%.2f", item.getTotal()))
                       .append(")\n");
            }
        }
        return summary.toString();
    }
    
    @Override
    public String toFileString() {
        return orderId + "|" + bookingId + "|" + customerName + "|" + totalPrice + "|" + date;
    }
    
    @Override
    public String toString() {
        return orderId + "|" + bookingId + "|" + customerName + "|" + totalPrice + "|" + date;
    }
}
