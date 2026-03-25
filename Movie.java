package CinemaAPP;

/**
 *
 * @author Mathew
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.*;

/**
 * Enhanced Movie Addition System - Cinema Management
 * @author Mathew
 */
public class Movie extends JFrame{
    
    private static final Color COLOR_BG = new Color(214, 217, 223);
    private static final Color COLOR_TITLE = new Color(31, 58, 94);
    private static final Color COLOR_LABEL = new Color(35, 57, 86);
    private static final Color COLOR_PRIMARY = new Color(74, 138, 214);
    private static final Color COLOR_SECONDARY = new Color(155, 170, 177);
    
    Movie(){
        this.setTitle("Add New Movie");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(COLOR_BG);
        
        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        headerPanel.setBackground(COLOR_BG);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel titleLabel = new JLabel("Add Movie");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 44));
        titleLabel.setForeground(COLOR_TITLE);
        
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Form Panel with ScrollPane
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(COLOR_BG);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Helper method reference for adding fields
        int row = 0;

        // 1. Movie Title
        JLabel movieLabel = new JLabel("Movie Title:");
        movieLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        movieLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(movieLabel, gbc);
        
        JTextField movieText = new JTextField(20);
        movieText.setFont(new Font("Arial", Font.PLAIN, 13));
        movieText.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(movieText, gbc);
        row++;
        
        // 2. Genre
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        genreLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(genreLabel, gbc);
        
        String[] genres = {"Select Genre...", "Action", "Comedy", "Drama", "Horror", 
                          "Sci-Fi", "Romance", "Thriller", "Animation", "Documentary"};
        JComboBox<String> genreBox = new JComboBox<>(genres);
        genreBox.setFont(new Font("Arial", Font.PLAIN, 13));
        genreBox.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(genreBox, gbc);
        row++;
        
        // 3. Duration
        JLabel durationLabel = new JLabel("Duration (e.g., 2h 15m):");
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        durationLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(durationLabel, gbc);
        
        JTextField durationText = new JTextField(20);
        durationText.setFont(new Font("Arial", Font.PLAIN, 13));
        durationText.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(durationText, gbc);
        row++;
        
        // 4. Rating
        JLabel ratingLabel = new JLabel("Age Rating:");
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        ratingLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(ratingLabel, gbc);
        
        String[] ratings = {"Select Rating...", "U (Universal)", "P13 (Parental Guidance 13)", 
                           "18+ (Adults Only)", "R (Restricted)"};
        JComboBox<String> ratingBox = new JComboBox<>(ratings);
        ratingBox.setFont(new Font("Arial", Font.PLAIN, 13));
        ratingBox.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(ratingBox, gbc);
        row++;
        
        // 5. Language
        JLabel languageLabel = new JLabel("Language:");
        languageLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        languageLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(languageLabel, gbc);
        
        String[] languages = {"Select Language...", "English", "Malay", "Chinese (Mandarin)", 
                             "Tamil", "Japanese", "Korean", "Hindi", "English (Subtitled)"};
        JComboBox<String> languageBox = new JComboBox<>(languages);
        languageBox.setFont(new Font("Arial", Font.PLAIN, 13));
        languageBox.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(languageBox, gbc);
        row++;
        
        // 6. Director
        JLabel directorLabel = new JLabel("Director:");
        directorLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        directorLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(directorLabel, gbc);
        
        JTextField directorText = new JTextField(20);
        directorText.setFont(new Font("Arial", Font.PLAIN, 13));
        directorText.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(directorText, gbc);
        row++;
        
        // 7. Release Date
        JLabel releaseDateLabel = new JLabel("Release Date:");
        releaseDateLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        releaseDateLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(releaseDateLabel, gbc);
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner releaseDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(releaseDateSpinner, "dd/MM/yyyy");
        releaseDateSpinner.setEditor(dateEditor);
        releaseDateSpinner.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(releaseDateSpinner, gbc);
        row++;
        
        // 8. Hall Number
        JLabel hallLabel = new JLabel("Cinema Hall:");
        hallLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        hallLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(hallLabel, gbc);
        
        String[] halls = {"Select Hall...", "Hall 1", "Hall 2", "Hall 3", "Hall 4", 
                         "Hall 5 (IMAX)", "Hall 6 (VIP)"};
        JComboBox<String> hallBox = new JComboBox<>(halls);
        hallBox.setFont(new Font("Arial", Font.PLAIN, 13));
        hallBox.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(hallBox, gbc);
        row++;
        
        // 9. Showtime
        JLabel timeLabel = new JLabel("Showtime:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        timeLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(timeLabel, gbc);
        
        String[] timeSlots = {
            "Select Time...",
            "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM", 
            "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM", 
            "8:00 PM", "9:00 PM", "10:00 PM"
        };
        JComboBox<String> timeBox = new JComboBox<>(timeSlots);
        timeBox.setFont(new Font("Arial", Font.PLAIN, 13));
        timeBox.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(timeBox, gbc);
        row++;
        
        // 10. Ticket Price
        JLabel priceLabel = new JLabel("Ticket Price (RM):");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        priceLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(priceLabel, gbc);
        
        JTextField priceText = new JTextField(20);
        priceText.setFont(new Font("Arial", Font.PLAIN, 13));
        priceText.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(priceText, gbc);
        row++;
        
        // 11. Discount Type
        JLabel discountLabel = new JLabel("Discount Type:");
        discountLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        discountLabel.setForeground(COLOR_LABEL);
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0;
        formPanel.add(discountLabel, gbc);
        
        String[] discountTypes = {"Select Discount...", Moviedata.DISCOUNT_NONE, 
                                  Moviedata.DISCOUNT_STUDENT, Moviedata.DISCOUNT_SENIOR, 
                                  Moviedata.DISCOUNT_CHILDREN, Moviedata.DISCOUNT_EARLY_BIRD, 
                                  Moviedata.DISCOUNT_WEEKDAY};
        JComboBox<String> discountBox = new JComboBox<>(discountTypes);
        discountBox.setFont(new Font("Arial", Font.PLAIN, 13));
        discountBox.setPreferredSize(new Dimension(300, 30));
        gbc.gridx = 1; gbc.gridy = row; gbc.weightx = 1;
        formPanel.add(discountBox, gbc);
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(COLOR_BG);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(COLOR_BG);
        
        JButton movieBtn = new JButton("Add Movie");
        movieBtn.setFont(new Font("Arial", Font.BOLD, 14));
        movieBtn.setBackground(COLOR_PRIMARY);
        movieBtn.setForeground(Color.WHITE);
        movieBtn.setFocusPainted(false);
        movieBtn.setBorderPainted(false);
        movieBtn.setPreferredSize(new Dimension(140, 45));
        
        JButton backbtn = new JButton("Cancel");
        backbtn.setFont(new Font("Arial", Font.BOLD, 14));
        backbtn.setBackground(COLOR_SECONDARY);
        backbtn.setForeground(Color.WHITE);
        backbtn.setFocusPainted(false);
        backbtn.setBorderPainted(false);
        backbtn.setPreferredSize(new Dimension(120, 45));
        
        buttonPanel.add(movieBtn);
        buttonPanel.add(backbtn);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Button Listeners
        backbtn.addActionListener(e -> {this.dispose(); new ManagerDashboard();});
        
        movieBtn.addActionListener(e -> {
            // 1. Get text and trim whitespace
            String name = movieText.getText().trim();
            String genre = (String) genreBox.getSelectedItem();
            String duration = durationText.getText().trim();
            String rating = (String) ratingBox.getSelectedItem();
            String language = (String) languageBox.getSelectedItem();
            String director = directorText.getText().trim();
            String hall = (String) hallBox.getSelectedItem();
            String time = (String) timeBox.getSelectedItem();
            String priceStr = priceText.getText().trim();

            // 2. Validation Check: If any field is empty or default, show a warning and stop
            if (name.isEmpty() || duration.isEmpty() || director.isEmpty() || priceStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill in all text fields!", 
                    "Input Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String discountType = (String) discountBox.getSelectedItem();

            if (genre == null || genre.equals("Select Genre...") || 
                rating == null || rating.equals("Select Rating...") ||
                language == null || language.equals("Select Language...") ||
                hall == null || hall.equals("Select Hall...") ||
                time == null || time.equals("Select Time...") ||
                discountType == null || discountType.equals("Select Discount...")) {
                JOptionPane.showMessageDialog(this, 
                    "Please select all dropdown options!", 
                    "Input Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Format release date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String releaseDate = sdf.format((Date) releaseDateSpinner.getValue());
            
            // 3. Check if movie already exists (same name, hall, and time)
            if (FileHandler.isMovieExists(name, hall, time)) {
                JOptionPane.showMessageDialog(this, 
                    "Movie already exists!\n\nMovie: " + name + "\nHall: " + hall + "\nTime: " + time + 
                    "\n\nPlease choose a different hall or time.", 
                    "Duplicate Movie", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double price;
            try {
                price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid price (positive number).", 
                    "Invalid Price", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 4. Create the Object with all enhanced fields
            Moviedata currentMovie = new Moviedata(name, hall, time, price, genre, 
                                                  duration, rating, language, director, releaseDate, "", discountType);

            // 5. Use the Object to save to the file
            if (!saveToFile(currentMovie)) {
                return;
            }
            
            // Ask if user wants to add another showtime for the same movie
            int response = JOptionPane.showConfirmDialog(this, 
                "Movie Added Successfully!\n\n" +
                "Title: " + name + "\n" +
                "Showtime ID: " + currentMovie.getShowtimeId() + "\n" +
                "Genre: " + genre + "\n" +
                "Hall: " + hall + "\n" +
                "Time: " + time + "\n\n" +
                "Would you like to add another showtime for '" + name + "'?",
                "Add Another Showtime?", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (response == JOptionPane.YES_OPTION) {
                // Keep movie details, only reset Hall, Time, and Price
                hallBox.setSelectedIndex(0); // Reset to "Select Hall..."
                timeBox.setSelectedIndex(0); // Reset to "Select Time..."
                priceText.setText("");
                discountBox.setSelectedIndex(0); // Reset to "Select Discount..."
                // Focus on hall field for convenience
                hallBox.requestFocusInWindow();
            } else {
                // Return to Dashboard
                new ManagerDashboard(); 
                this.dispose();
            }
        });
        
        this.add(mainPanel);
        this.setVisible(true);
    }


    // Basic save method logic
    private boolean saveToFile(Moviedata data) {
        try {
            FileHandler.addMovie(data);
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving to file.");
            return false;
        }
    }
}



