package CinemaAPP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewUpcomingMovies extends JFrame {

    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);

    public ViewUpcomingMovies(Users currentUser) {
        setTitle("Upcoming Movies");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Upcoming Movies");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel subtitleLabel = new JLabel("Check out what's coming soon to our cinemas!");
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(new Color(52, 73, 94));
        titleBox.add(titleLabel);
        titleBox.add(Box.createVerticalStrut(5));
        titleBox.add(subtitleLabel);
        
        headerPanel.add(titleBox);
        add(headerPanel, BorderLayout.NORTH);

        // Table Setup
        String[] columns = {"Movie Name", "Genre", "Language", "Director", "Expected Release"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        List<UpcomingMoviedata> upcomingMovies = FileHandler.getAllUpcomingMovies();
        for (UpcomingMoviedata m : upcomingMovies) {
            model.addRow(new Object[]{
                m.getName(), m.getGenre(), m.getLanguage(), m.getDirector(), m.getReleaseDate()
            });
        }

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(41, 128, 185));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(COLOR_TEXT);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(COLOR_BG);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(COLOR_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setBackground(new Color(149, 165, 166));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setPreferredSize(new Dimension(180, 40));
        
        backBtn.addActionListener(e -> {
            if (currentUser != null) {
                new CustomerDashboard(currentUser);
            } else {
                new ClerkDashboard();
            }
            this.dispose();
        });

        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
