/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 * TicketData class - stores booking information
 * 
 * @author Mathew
 */
public class TicketData implements FilePersistable {
    // Private variables
    private String customerName;
    private String movieName;
    private String seatNumber;
    private String hall;
    private double price;
    private String bookingId;
    private String date;

    // Constructor
    public TicketData(String name, String movie, String seat, String hall ,double price) {
        this(name, movie, seat, hall, price, null, null);
    }
    
    public TicketData(String name, String movie, String seat, String hall, double price, String bookingId) {
        this(name, movie, seat, hall, price, bookingId, null);
    }

    public TicketData(String name, String movie, String seat, String hall, double price, String bookingId, String date) {
        this.customerName = (name != null) ? name.trim() : "Unknown"; 
        this.movieName = (movie != null) ? movie.trim() : "Unknown";
        this.seatNumber = (seat != null) ? seat.trim() : "N/A";
        this.hall = (hall != null) ? hall.trim() : "N/A";
        this.price = price;
        this.bookingId = (bookingId != null) ? bookingId.trim() : "";
        this.date = (date != null && !date.trim().isEmpty()) ? date.trim() : new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }
    
    // Getter methods
    public String getCustomerName() {
        return customerName;
    }
    
    public String getMovieName() {
        return movieName;
    }
    
    public String getSeatNumber() {
        return seatNumber;
    }
    
    public String getHall() {
        return hall;
    }
    
    public double getPrice() {
        return price;
    }
    
    public String getBookingId() {
        return bookingId;
    }
    
    public String getDate() {
        return date;
    }
    
    // Setter methods
    public void setCustomerName(String customerName) {
        this.customerName = (customerName != null) ? customerName.trim() : "Unknown";
    }
    
    public void setMovieName(String movieName) {
        this.movieName = (movieName != null) ? movieName.trim() : "Unknown";
    }
    
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = (seatNumber != null) ? seatNumber.trim() : "N/A";
    }
    
    public void setHall(String hall) {
        this.hall = (hall != null) ? hall.trim() : "N/A";
    }
    
    public void setPrice(double price) {
        this.price = price;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = (bookingId != null) ? bookingId.trim() : "";
    }
    
    public void setDate(String date) {
        this.date = (date != null && !date.trim().isEmpty()) ? date.trim() : new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }

    // Method to convert ticket data to string for file saving
    @Override
    public String toFileString() {
        return customerName + "," + movieName + "," + seatNumber + "," + hall + "," + price + "," + bookingId + "," + date;
    }
}
