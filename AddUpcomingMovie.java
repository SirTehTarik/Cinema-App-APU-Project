package CinemaAPP;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUpcomingMovie extends JFrame {
    
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    private static final Color COLOR_SECONDARY = new Color(149, 165, 166);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);
    private static final Color COLOR_LABEL = new Color(35, 57, 86);
    
    private static final String[] GENRES = {"Select Genre...", "Action", "Comedy", "Drama", "Horror", 
                                            "Sci-Fi", "Romance", "Thriller", "Animation", "Documentary"};
    private static final String[] LANGUAGES = {"Select Language...", "English", "Malay", "Chinese (Mandarin)", 
                                               "Tamil", "Japanese", "Korean", "Hindi", "English (Subtitled)"};
    
    public AddUpcomingMovie() {
        setTitle("Add Upcoming Movie");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout(0, 0));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(COLOR_BG);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel header = new JLabel("Add Upcoming Movie");
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setForeground(COLOR_TEXT);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(header);
        
        JLabel subtitle = new JLabel("Add a new movie to the upcoming library (no showtimes yet)");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(127, 140, 141));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(subtitle);
        
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(COLOR_BG);
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.weighty = 0;

        int row = 0;

        // Name
        addLabel(formPanel, gbc, "Movie Title:", 0, row);
        JTextField nameField = createTextField();
        addField(formPanel, gbc, nameField, 1, row++);

        // Genre
        addLabel(formPanel, gbc, "Genre:", 0, row);
        JComboBox<String> genreBox = createComboBox(GENRES);
        addField(formPanel, gbc, genreBox, 1, row++);

        // Language
        addLabel(formPanel, gbc, "Language:", 0, row);
        JComboBox<String> languageBox = createComboBox(LANGUAGES);
        addField(formPanel, gbc, languageBox, 1, row++);

        // Director
        addLabel(formPanel, gbc, "Director:", 0, row);
        JTextField directorField = createTextField();
        addField(formPanel, gbc, directorField, 1, row++);

        // Expected Release Date
        addLabel(formPanel, gbc, "Expected Release Date:", 0, row);
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner releaseDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(releaseDateSpinner, "dd/MM/yyyy");
        releaseDateSpinner.setEditor(dateEditor);
        releaseDateSpinner.setFont(new Font("Arial", Font.PLAIN, 13));
        addField(formPanel, gbc, releaseDateSpinner, 1, row++);

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(COLOR_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton addBtn = createButton("Add Movie", COLOR_SUCCESS);
        addBtn.setPreferredSize(new Dimension(150, 40));
        JButton backBtn = createButton("Back", COLOR_SECONDARY);
        backBtn.setPreferredSize(new Dimension(150, 40));

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String genre = (String) genreBox.getSelectedItem();
            String language = (String) languageBox.getSelectedItem();
            String director = directorField.getText().trim();
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String releaseDate = sdf.format((Date) releaseDateSpinner.getValue());

            if (name.isEmpty() || director.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all text fields!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (genre.startsWith("Select") || language.startsWith("Select")) {
                JOptionPane.showMessageDialog(this, "Please select all dropdown options!", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            UpcomingMoviedata movie = new UpcomingMoviedata(name, genre, language, director, releaseDate);
            
            if (FileHandler.saveUpcomingMovie(movie)) {
                JOptionPane.showMessageDialog(this, "Upcoming movie added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                directorField.setText("");
                genreBox.setSelectedIndex(0);
                languageBox.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Error saving movie.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> {
            new ManagerDashboard();
            this.dispose();
        });

        buttonPanel.add(addBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void addLabel(JPanel panel, GridBagConstraints gbc, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setForeground(COLOR_LABEL);
        gbc.gridx = x; gbc.gridy = y; gbc.weightx = 0;
        panel.add(label, gbc);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, JComponent field, int x, int y) {
        gbc.gridx = x; gbc.gridy = y; gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(250, 30));
        return field;
    }

    private JComboBox<String> createComboBox(String[] options) {
        JComboBox<String> box = new JComboBox<>(options);
        box.setFont(new Font("Arial", Font.PLAIN, 13));
        box.setPreferredSize(new Dimension(250, 30));
        return box;
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
}
