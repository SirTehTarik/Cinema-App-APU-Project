/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 *
 * @author Mathew
 */
import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String MOVIE_FILE = "Movies.txt";
    private static final String FILE_NAME = "bookings.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String LOGIN_HISTORY_FILE = "login_history.txt";

    // TICKET METHODS
    // Load all bookings from file
    public static List<TicketData> loadAllBookings() {
        List<TicketData> bookings = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists())
            return bookings;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 7) {
                        bookings.add(new TicketData(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                parts[3].trim(),
                                Double.parseDouble(parts[4].trim()),
                                parts[5].trim(),
                                parts[6].trim()));
                    } else if (parts.length == 6) {
                        bookings.add(new TicketData(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                parts[3].trim(),
                                Double.parseDouble(parts[4].trim()),
                                parts[5].trim(),
                                "N/A"));
                    } else if (parts.length == 5) {
                        bookings.add(new TicketData(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                parts[3].trim(),
                                Double.parseDouble(parts[4].trim())));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed booking record: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        assignMissingBookingIds(bookings);
        return bookings;
    }

    // Save all bookings to file
    public static void saveAllBookings(List<TicketData> bookings) {
        assignMissingBookingIds(bookings);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (TicketData t : bookings) {
                bw.write(t.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // MOVIE METHODS
    // Load all movies from file
    public static List<Moviedata> getAllMovies() {
        List<Moviedata> movies = new ArrayList<>();
        File file = new File(MOVIE_FILE);

        if (!file.exists())
            return movies;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 12) {
                        movies.add(new Moviedata(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                Double.parseDouble(parts[3].trim()),
                                parts[4].trim(),
                                parts[5].trim(),
                                parts[6].trim(),
                                parts[7].trim(),
                                parts[8].trim(),
                                parts[9].trim(),
                                parts[10].trim(),
                                parts[11].trim()));
                    } else if (parts.length >= 11) {
                        movies.add(new Moviedata(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                Double.parseDouble(parts[3].trim()),
                                parts[4].trim(),
                                parts[5].trim(),
                                parts[6].trim(),
                                parts[7].trim(),
                                parts[8].trim(),
                                parts[9].trim(),
                                parts[10].trim()));
                    } else if (parts.length == 10) {
                        movies.add(new Moviedata(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                Double.parseDouble(parts[3].trim()),
                                parts[4].trim(),
                                parts[5].trim(),
                                parts[6].trim(),
                                parts[7].trim(),
                                parts[8].trim(),
                                parts[9].trim()));
                    } else if (parts.length == 4) {
                        movies.add(new Moviedata(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim(),
                                Double.parseDouble(parts[3].trim())));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed movie record: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading movies file: " + e.getMessage());
        }

        for (Moviedata movie : movies) {
            assignMissingShowtimeId(movie, movies);
        }

        return movies;
    }

    // UPCOMING MOVIE METHODS
    private static final String UPCOMING_MOVIE_FILE = "UpcomingMovies.txt";

    public static boolean saveUpcomingMovie(UpcomingMoviedata movie) {
        try (FileWriter fw = new FileWriter(UPCOMING_MOVIE_FILE, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw)) {
            pw.println(movie.toFileString());
            return true;
        } catch (IOException e) {
            System.err.println("Error saving upcoming movie: " + e.getMessage());
            return false;
        }
    }

    public static List<UpcomingMoviedata> getAllUpcomingMovies() {
        List<UpcomingMoviedata> movies = new ArrayList<>();
        File file = new File(UPCOMING_MOVIE_FILE);

        if (!file.exists())
            return movies;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    movies.add(new UpcomingMoviedata(
                            parts[0].trim(), parts[1].trim(), parts[2].trim(),
                            parts[3].trim(), parts[4].trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading upcoming movies file: " + e.getMessage());
        }

        return movies;
    }

    public static void saveAllMovies(List<Moviedata> movies) {
        for (Moviedata movie : movies) {
            assignMissingShowtimeId(movie, movies);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOVIE_FILE))) {
            for (Moviedata m : movies) {
                bw.write(m.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving movies: " + e.getMessage());
        }
    }

    // ---REVENUE REPORTS ---

    public static double calculateTotalRevenue() {
        List<TicketData> bookings = loadAllBookings();
        double total = 0;
        for (TicketData t : bookings) {
            total += t.getPrice(); // Adds up every ticket sold
        }
        return total;
    }

    // --- USER METHODS ---

    // Turns the text file into a List of Users Objects (Useful for a "View All
    // Users" table)
    public static List<Users> loadAllUsers() {
        List<Users> usersList = new ArrayList<>();
        File file = new File(USERS_FILE);

        if (!file.exists())
            return usersList;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                // Ensure the line has 4 pieces of data: username, password, role, fullName
                if (parts.length == 4) {
                    usersList.add(new Users(parts[0], parts[1], parts[2], parts[3]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users file: " + e.getMessage());
        }
        return usersList;
    }

    // Overwrites the text file with an updated List of Users (Useful if a Manager
    // deletes a user)
    public static void saveAllUsers(List<Users> usersList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (Users u : usersList) {
                bw.write(u.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // --- USER / CUSTOMER METHODS ---

    // Appends a single new user to the text file (Great for Registration!)
    public static void addUser(Users newUser) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            bw.write(newUser.toFileString());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving new user: " + e.getMessage());
        }
    }

    // --- TICKET BOOKING METHOD ---

    // Appends a single new ticket to the text file (Great for Clerk and Customer
    // Booking!)
    public static void addBooking(TicketData ticket) {
        if (ticket.getBookingId() == null || ticket.getBookingId().trim().isEmpty()) {
            List<TicketData> existingBookings = loadAllBookings();
            ticket.setBookingId(IdGenerator.generateNextBookingId(existingBookings));
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(ticket.toFileString());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving new booking: " + e.getMessage());

        }
    }

    public static void addMovie(Moviedata movie) {
        List<Moviedata> existingMovies = getAllMovies();
        assignMissingShowtimeId(movie, existingMovies);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOVIE_FILE, true))) {
            bw.write(movie.toFileString());
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error saving new movie: " + e.getMessage());
        }
    }

    // --- LOGIN HISTORY METHOD ---

    // Appends a single login record to the history file
    public static void logUserLogin(String username, String role) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOGIN_HISTORY_FILE, true))) {
            // Get the current date and time
            String timestamp = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            bw.write("Login -> User: " + username + " | Role: " + role + " | Time: " + timestamp);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to login history: " + e.getMessage());
        }
    }

    // Reads the entire login history file and returns it as one big String
    public static String readLoginHistory() {
        StringBuilder sb = new StringBuilder();
        java.io.File file = new java.io.File(LOGIN_HISTORY_FILE);

        if (!file.exists())
            return "No login history found.";

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (java.io.IOException e) {
            return "Error reading history: " + e.getMessage();
        }
        return sb.toString();
    }

    // --- DUPLICATE VALIDATION METHODS ---

    /**
     * Update an existing user in the system
     * 
     * @param updatedUser The user object with updated information
     * @return true if update was successful, false otherwise
     */
    public static boolean updateUser(Users updatedUser) {
        List<Users> allUsers = loadAllUsers();
        boolean found = false;

        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(updatedUser.getUsername())) {
                allUsers.set(i, updatedUser);
                found = true;
                break;
            }
        }

        if (found) {
            saveAllUsers(allUsers);
            return true;
        }
        return false;
    }

    /**
     * Check if a username already exists in the system
     * 
     * @param username The username to check
     * @return true if username exists, false otherwise
     */
    public static boolean isUsernameExists(String username) {
        List<Users> allUsers = loadAllUsers();
        for (Users user : allUsers) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a movie already exists (same name, hall, and time)
     * 
     * @param movieName The movie name
     * @param hall      The hall number
     * @param time      The showtime
     * @return true if movie exists, false otherwise
     */
    public static boolean isMovieExists(String movieName, String hall, String time) {
        List<Moviedata> allMovies = getAllMovies();
        for (Moviedata movie : allMovies) {
            if (movie.getName().equalsIgnoreCase(movieName) &&
                    movie.getHall().equalsIgnoreCase(hall) &&
                    movie.getTime().equalsIgnoreCase(time)) {
                return true;
            }
        }
        return false;
    }

    // SELECTION SORT ALGORITHM - Sort movies by price (low to high)
    public static List<Moviedata> sortMoviesByPrice(List<Moviedata> movies) {
        // Create a copy to avoid modifying the original list
        List<Moviedata> sortedMovies = new ArrayList<>(movies);
        int n = sortedMovies.size();

        // Selection sort algorithm
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (sortedMovies.get(j).getPrice() < sortedMovies.get(minIndex).getPrice()) {
                    minIndex = j;
                }
            }
            // Swap the found minimum element with the first element
            if (minIndex != i) {
                Moviedata temp = sortedMovies.get(i);
                sortedMovies.set(i, sortedMovies.get(minIndex));
                sortedMovies.set(minIndex, temp);
            }
        }
        return sortedMovies;
    }

    // LINEAR SEARCH ALGORITHM - Search for a movie by name
    public static Moviedata linearSearchMovieByName(List<Moviedata> movies, String movieName) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getName().equalsIgnoreCase(movieName)) {
                return movies.get(i); // Movie found
            }
        }
        return null; // Movie not found
    }

    private static void assignMissingBookingIds(List<TicketData> bookings) {
        for (TicketData booking : bookings) {
            if (booking.getBookingId() == null || booking.getBookingId().trim().isEmpty()) {
                booking.setBookingId(IdGenerator.generateNextBookingId(bookings));
            }
        }
    }

    private static void assignMissingShowtimeId(Moviedata movie, List<Moviedata> existingMovies) {
        if (movie.getShowtimeId() != null && !movie.getShowtimeId().trim().isEmpty()) {
            return;
        }

        int movieId = resolveMovieId(movie.getName(), existingMovies);
        String showtimeId = IdGenerator.generateShowtimeId(movie.getHall(), movie.getTime(), movieId);
        movie.setShowtimeId(showtimeId);
    }

    private static int resolveMovieId(String movieName, List<Moviedata> movies) {
        int maxMovieId = 0;

        for (Moviedata movie : movies) {
            int existingMovieId = IdGenerator.extractMovieIdFromShowtimeId(movie.getShowtimeId());
            if (existingMovieId > 0) {
                if (movie.getName().equalsIgnoreCase(movieName)) {
                    return existingMovieId;
                }
                if (existingMovieId > maxMovieId) {
                    maxMovieId = existingMovieId;
                }
            }
        }

        return maxMovieId + 1;
    }
}
