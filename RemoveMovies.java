/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.*;

/**
 *
 * @author Mathew
 */
public class RemoveMovies extends JFrame {
    
    // Color constants matching BaseDashboard
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_DANGER = new Color(231, 76, 60);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);
    
    public RemoveMovies() {
        setTitle("Remove Movie from Showtimes");
        setSize(780, 260);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(720, 240));
        getContentPane().setBackground(COLOR_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Showtime ID to Remove:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 11));
        nameLabel.setForeground(COLOR_TEXT);
        
        // Load existing movies and populate combobox
        JComboBox<Moviedata> movieBox = new JComboBox<>();
        movieBox.addItem(null);
        movieBox.setPreferredSize(new Dimension(500, 28));
        List<Moviedata> allMovies = FileHandler.getAllMovies();
        for (Moviedata movie : allMovies) {
            movieBox.addItem(movie);
        }

        movieBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                   boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText("Select Showtime ID...");
                } else {
                    Moviedata movie = (Moviedata) value;
                    setText(movie.getShowtimeId() + " | " + movie.getName() + " | " + movie.getHall() + " | " + movie.getTime());
                }
                return this;
            }
        });
        
        JButton removeBtn = new JButton("Confirm Removal");
        styleButton(removeBtn, COLOR_DANGER);
        
        JButton backBtn = new JButton("Back");
        styleButton(backBtn, COLOR_PRIMARY);

        // Layout
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1; add(movieBox, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; add(backBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1; add(removeBtn, gbc);

        // Action: Remove Logic
        removeBtn.addActionListener(e -> {
            Moviedata selectedMovie = (Moviedata) movieBox.getSelectedItem();
            
            if (selectedMovie == null) {
                JOptionPane.showMessageDialog(this, "Please select a Showtime ID to remove.");
                return;
            }

            // 1. Load current movies using our FileHandler method
            List<Moviedata> currentMovies = FileHandler.getAllMovies();
            String showtimeIdToDelete = selectedMovie.getShowtimeId();
            
            // 2. Remove the movie object if the showtime ID matches
            boolean removed = currentMovies.removeIf(m -> m.getShowtimeId().equalsIgnoreCase(showtimeIdToDelete));

            if (removed) {
                // 3. Save the updated list back to Movies.txt
                FileHandler.saveAllMovies(currentMovies);
                
                JOptionPane.showMessageDialog(this, "Showtime '" + showtimeIdToDelete + "' has been removed.");
                new ManagerDashboard();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Showtime ID not found in the system.");
            }
        });

        backBtn.addActionListener(e -> {
            new ManagerDashboard();
            this.dispose();
        });

        setVisible(true);
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(140, 35));
    }
}
