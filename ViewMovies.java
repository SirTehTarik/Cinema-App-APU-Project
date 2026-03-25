/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

import java.awt.BorderLayout;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mathew
 */
public class ViewMovies extends JFrame{
    public ViewMovies(){
        setTitle("All Cinema Movies");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Movie Library");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel subtitleLabel = new JLabel("Manage and view all available movies");
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(new Color(52, 73, 94));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);

        // Table Setup
        String[] columnNames = {"Showtime ID", "Movie Name", "Genre", "Duration", "Rating", "Language", 
                                "Director", "Release Date", "Hall", "Time", "Price (RM)"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Load Data
        List<Moviedata> allMovies = FileHandler.getAllMovies(); 

        for(Moviedata m : allMovies) {
            Object[] row = { 
                m.getShowtimeId(),
                m.getName(), 
                m.getGenre(), 
                m.getDuration(), 
                m.getRating(), 
                m.getLanguage(),
                m.getDirector(),
                m.getReleaseDate(),
                m.getHall(), 
                m.getTime(),
                String.format("%.2f", m.getPrice())
            };
            model.addRow(row);
        }

        // Create Table with styling
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(41, 128, 185));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        
        // Set column widths for better display
        table.getColumnModel().getColumn(0).setPreferredWidth(120); // Showtime ID
        table.getColumnModel().getColumn(1).setPreferredWidth(130); // Movie Name
        table.getColumnModel().getColumn(2).setPreferredWidth(90);  // Genre
        table.getColumnModel().getColumn(3).setPreferredWidth(70);  // Duration
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Rating
        table.getColumnModel().getColumn(5).setPreferredWidth(110); // Language
        table.getColumnModel().getColumn(6).setPreferredWidth(120); // Director
        table.getColumnModel().getColumn(7).setPreferredWidth(90);  // Release Date
        table.getColumnModel().getColumn(8).setPreferredWidth(70);  // Hall
        table.getColumnModel().getColumn(9).setPreferredWidth(70);  // Time
        table.getColumnModel().getColumn(10).setPreferredWidth(80); // Price
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Info Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(236, 240, 241));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel infoLabel = new JLabel("Total Movies: " + allMovies.size());
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(52, 73, 94));
        infoPanel.add(infoLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 13));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(120, 40));
        refreshButton.addActionListener(e -> {
            new ViewMovies();
            this.dispose();
        });
        
        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn.setBackground(new Color(149, 165, 166));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setPreferredSize(new Dimension(150, 40));
        backBtn.addActionListener(e -> {
            new ManagerDashboard();
            this.dispose();
        });
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(backBtn);

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
}