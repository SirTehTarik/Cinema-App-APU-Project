/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 * Moviedata class - stores movie information
 * 
 * @author Mathew
 */

public class Moviedata implements FilePersistable {
    // Private variables
    private String movieName;
    private String auditorium;
    private String time;
    private double price;
    private String genre;
    private String duration;
    private String rating;
    private String language;
    private String director;
    private String releaseDate;
    private String showtimeId;
    private String discountType;
    
    // Discount type constants
    public static final String DISCOUNT_NONE = "None";
    public static final String DISCOUNT_STUDENT = "Student (10% off)";
    public static final String DISCOUNT_SENIOR = "Senior Citizen (15% off)";
    public static final String DISCOUNT_CHILDREN = "Children (20% off)";
    public static final String DISCOUNT_EARLY_BIRD = "Early Bird (25% off)";
    public static final String DISCOUNT_WEEKDAY = "Weekday Special (15% off)";

    // Constructor 1 - basic movie (method overloading)
    public Moviedata(String movieName, String auditorium, String time, double price) {
        this(movieName, auditorium, time, price, "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "", DISCOUNT_NONE);
    }
    
    // Constructor 2 - full movie details without showtimeId (method overloading)
    public Moviedata(String movieName, String auditorium, String time, double price, 
                     String genre, String duration, String rating, String language, 
                     String director, String releaseDate) {
        this(movieName, auditorium, time, price, genre, duration, rating, language, director, releaseDate, "", DISCOUNT_NONE);
    }

    // Constructor 3 - full movie details with showtimeId (method overloading)
    public Moviedata(String movieName, String auditorium, String time, double price,
                     String genre, String duration, String rating, String language,
                     String director, String releaseDate, String showtimeId) {
        this(movieName, auditorium, time, price, genre, duration, rating, language, director, releaseDate, showtimeId, DISCOUNT_NONE);
    }

    // Constructor 4 - full movie details with showtimeId and discountType
    public Moviedata(String movieName, String auditorium, String time, double price,
                     String genre, String duration, String rating, String language,
                     String director, String releaseDate, String showtimeId, String discountType) {
        this.movieName = movieName;
        this.auditorium = auditorium;
        this.time = time;
        this.price = price;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.language = language;
        this.director = director;
        this.releaseDate = releaseDate;
        this.showtimeId = (showtimeId != null) ? showtimeId.trim() : "";
        this.discountType = (discountType != null && !discountType.trim().isEmpty()) ? discountType.trim() : DISCOUNT_NONE;
    }

    // Getter methods
    public String getName() { return movieName; }
    public String getHall() { return auditorium; }
    public String getTime() { return time; }
    public double getPrice() { return price; }
    public String getGenre() { return genre; }
    public String getDuration() { return duration; }
    public String getRating() { return rating; }
    public String getLanguage() { return language; }
    public String getDirector() { return director; }
    public String getReleaseDate() { return releaseDate; }
    public String getShowtimeId() { return showtimeId; }
    public String getDiscountType() { return discountType; }

    /**
     * Returns the discount percentage based on the discount type
     * @return discount percentage (e.g., 0.10 for 10% off)
     */
    public double getDiscountPercentage() {
        if (discountType == null) return 0;
        switch (discountType) {
            case DISCOUNT_STUDENT:     return 0.10;
            case DISCOUNT_SENIOR:      return 0.15;
            case DISCOUNT_CHILDREN:    return 0.20;
            case DISCOUNT_EARLY_BIRD:  return 0.25;
            case DISCOUNT_WEEKDAY:     return 0.15;
            default:                   return 0.0;
        }
    }

    /**
     * Returns the discounted price after applying the discount type
     * @return the final price after discount
     */
    public double getDiscountedPrice() {
        return price * (1 - getDiscountPercentage());
    }

    // Setter methods
    public void setName(String movieName) { this.movieName = movieName; }
    public void setHall(String auditorium) { this.auditorium = auditorium; }
    public void setTime(String time) { this.time = time; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setRating(String rating) { this.rating = rating; }
    public void setLanguage(String language) { this.language = language; }
    public void setDirector(String director) { this.director = director; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
    public void setPrice(double price) { this.price = price; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = (showtimeId != null) ? showtimeId.trim() : ""; }
    public void setDiscountType(String discountType) { 
        this.discountType = (discountType != null && !discountType.trim().isEmpty()) ? discountType.trim() : DISCOUNT_NONE; 
    }

    // Method to convert movie data to string for file saving
    @Override
    public String toFileString() {
        return movieName + "," + auditorium + "," + time + "," + price + "," + 
               genre + "," + duration + "," + rating + "," + language + "," + 
               director + "," + releaseDate + "," + showtimeId + "," + discountType;
    }
}