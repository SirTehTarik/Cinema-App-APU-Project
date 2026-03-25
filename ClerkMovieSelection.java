package CinemaAPP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Clerk Movie Selection Screen - Browse and select movies for customer booking
 * Clerk enters customer information first, then proceeds with visual seat booking
 * 
 * @author Mathew
 */
public class ClerkMovieSelection extends JFrame {
    
    private String customerName;
    private String customerPhone;
    private DefaultTableModel tableModel;
    private JTable movieTable;
    
    public ClerkMovieSelection(String custName, String custPhone) {
        this.customerName = custName;
        this.customerPhone = custPhone;
        setupUI();
    }
    
    private void setupUI() {
        this.setTitle("Select Movie for: " + customerName);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Now Showing - Clerk Booking");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel subtitleLabel = new JLabel("Booking for: " + customerName + " | Phone: " + customerPhone);
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(52, 73, 94));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        // Movie Table
        String[] columns = {"Movie Title", "Genre", "Rating", "Duration", "Hall", "Showtime", "Price (RM)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        movieTable = new JTable(tableModel);
        movieTable.setFont(new Font("Arial", Font.PLAIN, 13));
        movieTable.setRowHeight(35);
        movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        movieTable.getTableHeader().setBackground(new Color(41, 128, 185));
        movieTable.getTableHeader().setForeground(Color.WHITE);
        movieTable.setSelectionBackground(new Color(52, 152, 219));
        movieTable.setSelectionForeground(Color.WHITE);
        
        // Load movies
        loadMovies();
        
        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(236, 240, 241));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel infoLabel = new JLabel("Tip: Double-click on a movie or click 'Select Movie' to proceed to seat selection");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(52, 73, 94));
        infoPanel.add(infoLabel);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton selectButton = new JButton("Select Movie");
        selectButton.setFont(new Font("Arial", Font.BOLD, 14));
        selectButton.setBackground(new Color(46, 204, 113));
        selectButton.setForeground(Color.WHITE);
        selectButton.setFocusPainted(false);
        selectButton.setPreferredSize(new Dimension(150, 40));
        selectButton.addActionListener(e -> openBooking());
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(120, 40));
        refreshButton.addActionListener(e -> loadMovies());
        
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(149, 165, 166));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.addActionListener(e -> {
            new ClerkBookingInput();
            this.dispose();
        });
        
        buttonPanel.add(selectButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        // Double-click to select
        movieTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openBooking();
                }
            }
        });
        
        // Layout
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        centerPanel.add(infoPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        this.add(mainPanel);
        this.setVisible(true);
    }
    
    private void loadMovies() {
        // Clear existing rows
        tableModel.setRowCount(0);
        
        // Load all movies from file
        List<Moviedata> movies = FileHandler.getAllMovies();
        
        if (movies.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No movies available at the moment.\nPlease contact the manager to add movies.",
                "No Movies",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Add each movie to the table
        for (Moviedata movie : movies) {
            Object[] row = {
                movie.getName(),
                movie.getGenre(),
                movie.getRating(),
                movie.getDuration(),
                movie.getHall(),
                movie.getTime(),
                String.format("%.2f", movie.getPrice())
            };
            tableModel.addRow(row);
        }
    }
    
    private void openBooking() {
        int selectedRow = movieTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a movie first!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Get the selected movie details
        String movieName = (String) tableModel.getValueAt(selectedRow, 0);
        String hall = (String) tableModel.getValueAt(selectedRow, 4);
        String time = (String) tableModel.getValueAt(selectedRow, 5);
        
        // Find the complete movie object
        List<Moviedata> movies = FileHandler.getAllMovies();
        Moviedata selectedMovie = null;
        
        for (Moviedata movie : movies) {
            if (movie.getName().equals(movieName) && 
                movie.getHall().equals(hall) && 
                movie.getTime().equals(time)) {
                selectedMovie = movie;
                break;
            }
        }
        
        if (selectedMovie != null) {
            // Create a temporary user object for the customer
            // Role is "Guest" to indicate this is a clerk-assisted booking
            Users guestUser = new Users("guest_" + System.currentTimeMillis(), 
                                       "", 
                                       "Guest", 
                                       customerName);
            
            // Open the visual seat booking interface
            new VisualSeatBooking(selectedMovie, guestUser);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Error loading movie data. Please try again.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
