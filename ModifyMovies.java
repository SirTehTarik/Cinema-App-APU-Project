/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

/**
 *
 * @author Mathew
 */

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ModifyMovies extends JFrame {
    
    // Color constants matching BaseDashboard
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    private static final Color COLOR_SECONDARY = new Color(149, 165, 166);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);
    private static final Color COLOR_LABEL = new Color(35, 57, 86);
    
    // Dropdown options (same as Add Movie page)
    private static final String[] GENRES = {"Select Genre...", "Action", "Comedy", "Drama", "Horror", 
                      "Sci-Fi", "Romance", "Thriller", "Animation", "Documentary"};
    private static final String[] RATINGS = {"Select Rating...", "U (Universal)", "P13 (Parental Guidance 13)", 
                       "18+ (Adults Only)", "R (Restricted)"};
    private static final String[] LANGUAGES = {"Select Language...", "English", "Malay", "Chinese (Mandarin)", 
                         "Tamil", "Japanese", "Korean", "Hindi", "English (Subtitled)"};
    private static final String[] HALLS = {"Select Hall...", "Hall 1", "Hall 2", "Hall 3", "Hall 4", 
                     "Hall 5 (IMAX)", "Hall 6 (VIP)"};
    private static final String[] TIME_SLOTS = {"Select Time...", "10:00 AM", "11:00 AM", "12:00 PM", 
                          "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", 
                          "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM", "10:00 PM"};
    private static final String[] DISCOUNT_TYPES = {"Select Discount...", Moviedata.DISCOUNT_NONE, 
                          Moviedata.DISCOUNT_STUDENT, Moviedata.DISCOUNT_SENIOR, 
                          Moviedata.DISCOUNT_CHILDREN, Moviedata.DISCOUNT_EARLY_BIRD, 
                          Moviedata.DISCOUNT_WEEKDAY};
    
    // Form fields
    private JTextField nameField;
    private JComboBox<String> genreBox;
    private JTextField durationField;
    private JComboBox<String> ratingBox;
    private JComboBox<String> languageBox;
    private JTextField directorField;
    private JTextField releaseDateField;
    private JComboBox<String> hallBox;
    private JComboBox<String> timeBox;
    private JTextField priceField;
    private JComboBox<String> discountBox;
    
    // Table and data
    private JTable movieTable;
    private List<Moviedata> allMovies;
    private Moviedata selectedMovie;
    
    public ModifyMovies() {
        setTitle("Modify Movie Details");
        setSize(750, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout(0, 0));
        
        // Load movies
        allMovies = FileHandler.getAllMovies();

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(COLOR_BG);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel header = new JLabel("Modify Movie");
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setForeground(COLOR_TEXT);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(header);

        JLabel subtitle = new JLabel("Select a movie from the table, edit the fields below, then click Update");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(127, 140, 141));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(subtitle);

        add(headerPanel, BorderLayout.NORTH);

        // ===== MAIN CONTENT (Table + Form) =====
        JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
        contentPanel.setBackground(COLOR_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // --- Movie Table ---
        contentPanel.add(createMovieTable(), BorderLayout.NORTH);

        // --- Edit Form ---
        contentPanel.add(createEditForm(), BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(COLOR_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 15, 20));

        JButton updateBtn = new JButton("Update Movie");
        styleButton(updateBtn, COLOR_SUCCESS);
        updateBtn.setPreferredSize(new Dimension(160, 40));
        
        JButton backBtn = new JButton("Back to Dashboard");
        styleButton(backBtn, COLOR_SECONDARY);
        backBtn.setPreferredSize(new Dimension(160, 40));

        updateBtn.addActionListener(e -> updateMovie());
        backBtn.addActionListener(e -> { new ManagerDashboard(); this.dispose(); });

        buttonPanel.add(updateBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Creates the movie selection table
     */
    private JScrollPane createMovieTable() {
        String[] columns = {"Showtime ID", "Movie", "Hall", "Time", "Price (RM)"};
        Object[][] data = new Object[allMovies.size()][5];

        for (int i = 0; i < allMovies.size(); i++) {
            Moviedata m = allMovies.get(i);
            data[i][0] = m.getShowtimeId();
            data[i][1] = m.getName();
            data[i][2] = m.getHall();
            data[i][3] = m.getTime();
            data[i][4] = String.format("%.2f", m.getPrice());
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        movieTable = new JTable(model);
        movieTable.setFont(new Font("Arial", Font.PLAIN, 12));
        movieTable.setRowHeight(28);
        movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        movieTable.setShowGrid(false);
        movieTable.setIntercellSpacing(new Dimension(0, 0));
        movieTable.setBackground(Color.WHITE);
        movieTable.setSelectionBackground(new Color(232, 240, 254));
        movieTable.setSelectionForeground(COLOR_TEXT);

        // Style header
        JTableHeader tableHeader = movieTable.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 12));
        tableHeader.setBackground(COLOR_PRIMARY);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setPreferredSize(new Dimension(0, 32));

        // When a row is clicked, fill the form with that movie's data
        movieTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = movieTable.getSelectedRow();
                if (row >= 0 && row < allMovies.size()) {
                    selectedMovie = allMovies.get(row);
                    fillFormWithMovie(selectedMovie);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.setPreferredSize(new Dimension(0, 130));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));

        return scrollPane;
    }

    /**
     * Creates the edit form panel with all movie fields
     */
    private JPanel createEditForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220)),
                " Edit Details ",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                COLOR_TEXT
            ),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize all form fields
        nameField = new JTextField(18);
        genreBox = new JComboBox<>(GENRES);
        durationField = new JTextField(18);
        ratingBox = new JComboBox<>(RATINGS);
        languageBox = new JComboBox<>(LANGUAGES);
        directorField = new JTextField(18);
        releaseDateField = new JTextField(18);
        hallBox = new JComboBox<>(HALLS);
        timeBox = new JComboBox<>(TIME_SLOTS);
        priceField = new JTextField(18);
        discountBox = new JComboBox<>(DISCOUNT_TYPES);

        // Layout: 2 columns of label+field pairs
        // Left column
        int row = 0;
        addFormField(formPanel, gbc, "Movie Title:", nameField, 0, row); 
        addFormField(formPanel, gbc, "Genre:", genreBox, 2, row); 
        row++;
        addFormField(formPanel, gbc, "Duration:", durationField, 0, row); 
        addFormField(formPanel, gbc, "Age Rating:", ratingBox, 2, row); 
        row++;
        addFormField(formPanel, gbc, "Language:", languageBox, 0, row); 
        addFormField(formPanel, gbc, "Director:", directorField, 2, row); 
        row++;
        addFormField(formPanel, gbc, "Release Date:", releaseDateField, 0, row); 
        addFormField(formPanel, gbc, "Price (RM):", priceField, 2, row); 
        row++;
        addFormField(formPanel, gbc, "Cinema Hall:", hallBox, 0, row); 
        addFormField(formPanel, gbc, "Showtime:", timeBox, 2, row); 
        row++;
        addFormField(formPanel, gbc, "Discount:", discountBox, 0, row); 

        // Style all text fields and combo boxes
        styleField(nameField);
        styleField(durationField);
        styleField(directorField);
        styleField(releaseDateField);
        styleField(priceField);
        styleComboBox(genreBox);
        styleComboBox(ratingBox);
        styleComboBox(languageBox);
        styleComboBox(hallBox);
        styleComboBox(timeBox);
        styleComboBox(discountBox);

        // Initially disable form until a movie is selected
        setFormEnabled(false);

        return formPanel;
    }

    /**
     * Adds a label + component pair to the form
     */
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, 
                               JComponent field, int col, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(COLOR_LABEL);
        
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(label, gbc);
        
        gbc.gridx = col + 1;
        gbc.weightx = 0.5;
        panel.add(field, gbc);
    }

    /**
     * Fills the form fields with the selected movie's current data
     */
    private void fillFormWithMovie(Moviedata movie) {
        setFormEnabled(true);
        
        nameField.setText(movie.getName());
        durationField.setText(movie.getDuration());
        directorField.setText(movie.getDirector());
        releaseDateField.setText(movie.getReleaseDate());
        priceField.setText(String.format("%.2f", movie.getPrice()));
        
        // Set combo boxes to match current values
        setComboBoxValue(genreBox, movie.getGenre());
        setComboBoxValue(ratingBox, movie.getRating());
        setComboBoxValue(languageBox, movie.getLanguage());
        setComboBoxValue(hallBox, movie.getHall());
        setComboBoxValue(timeBox, movie.getTime());
        setComboBoxValue(discountBox, movie.getDiscountType());
    }

    /**
     * Selects the matching item in a combo box, or index 0 if not found
     */
    private void setComboBoxValue(JComboBox<String> comboBox, String value) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
        comboBox.setSelectedIndex(0);
    }

    /**
     * Enables or disables all form fields
     */
    private void setFormEnabled(boolean enabled) {
        nameField.setEnabled(enabled);
        genreBox.setEnabled(enabled);
        durationField.setEnabled(enabled);
        ratingBox.setEnabled(enabled);
        languageBox.setEnabled(enabled);
        directorField.setEnabled(enabled);
        releaseDateField.setEnabled(enabled);
        hallBox.setEnabled(enabled);
        timeBox.setEnabled(enabled);
        priceField.setEnabled(enabled);
        discountBox.setEnabled(enabled);
    }

    /**
     * Handles the update button click
     */
    private void updateMovie() {
        if (selectedMovie == null) {
            JOptionPane.showMessageDialog(this, 
                "Please select a movie from the table first.", 
                "No Movie Selected", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get values from form
        String name = nameField.getText().trim();
        String genre = (String) genreBox.getSelectedItem();
        String duration = durationField.getText().trim();
        String rating = (String) ratingBox.getSelectedItem();
        String language = (String) languageBox.getSelectedItem();
        String director = directorField.getText().trim();
        String releaseDate = releaseDateField.getText().trim();
        String hall = (String) hallBox.getSelectedItem();
        String time = (String) timeBox.getSelectedItem();
        String priceStr = priceField.getText().trim();

        // Validate text fields
        if (name.isEmpty() || duration.isEmpty() || director.isEmpty() || 
            releaseDate.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all text fields!", 
                "Input Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate dropdowns
        if (genre.startsWith("Select") || rating.startsWith("Select") || 
            language.startsWith("Select") || hall.startsWith("Select") || 
            time.startsWith("Select")) {
            JOptionPane.showMessageDialog(this, 
                "Please select all dropdown options!", 
                "Input Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String discountType = (String) discountBox.getSelectedItem();
        if (discountType == null || discountType.startsWith("Select")) {
            JOptionPane.showMessageDialog(this, 
                "Please select a discount type!", 
                "Input Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validate price
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

        // Check for duplicates (exclude the movie being modified)
        for (Moviedata m : allMovies) {
            if (m != selectedMovie && 
                m.getName().equalsIgnoreCase(name) && 
                m.getHall().equalsIgnoreCase(hall) && 
                m.getTime().equalsIgnoreCase(time)) {
                JOptionPane.showMessageDialog(this, 
                    "Cannot update: This movie already exists at the same hall and time!\n\n" +
                    "Movie: " + name + "\nHall: " + hall + "\nTime: " + time, 
                    "Duplicate Detected", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Apply changes to the selected movie object
        selectedMovie.setName(name);
        selectedMovie.setHall(hall);
        selectedMovie.setTime(time);
        selectedMovie.setGenre(genre);
        selectedMovie.setDuration(duration);
        selectedMovie.setRating(rating);
        selectedMovie.setLanguage(language);
        selectedMovie.setDirector(director);
        selectedMovie.setReleaseDate(releaseDate);
        selectedMovie.setPrice(price);
        selectedMovie.setDiscountType(discountType);

        // Save all movies back to file
        FileHandler.saveAllMovies(allMovies);
        
        JOptionPane.showMessageDialog(this, 
            "Movie updated successfully!\n\n" +
            "Title: " + name + "\n" +
            "Hall: " + hall + "\n" +
            "Time: " + time, 
            "Update Successful", 
            JOptionPane.INFORMATION_MESSAGE);
            
        new ManagerDashboard();
        this.dispose();
    }

    // --- Styling helpers ---
    
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
    
    private void styleField(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setPreferredSize(new Dimension(160, 28));
    }
    
    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Arial", Font.PLAIN, 12));
        box.setPreferredSize(new Dimension(160, 28));
    }
}
