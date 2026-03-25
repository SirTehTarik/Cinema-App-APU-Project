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
import java.util.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class ManagerReports extends JFrame {
    
    // Color palette (matching dashboards)
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    private static final Color COLOR_WARNING = new Color(241, 196, 15);
    private static final Color COLOR_INFO = new Color(52, 152, 219);
    private static final Color COLOR_SECONDARY = new Color(149, 165, 166);
    
    public ManagerReports() {
        setTitle("Manager Revenue Report");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(COLOR_BG);
        setLayout(new BorderLayout(0, 10));

        // Load all booking data
        List<TicketData> bookings = FileHandler.loadAllBookings();

        // ===== HEADER =====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(COLOR_BG);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        JLabel header = new JLabel("Revenue Report");
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setForeground(COLOR_TEXT);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(header);

        JLabel subtitle = new JLabel("Financial overview and breakdown");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(new Color(127, 140, 141));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(subtitle);

        add(headerPanel, BorderLayout.NORTH);

        // ===== CALCULATE ALL DATA =====
        double totalRevenue = 0;
        int totalTickets = bookings.size();
        
        // Revenue by Movie
        Map<String, Double> revenueByMovie = new LinkedHashMap<>();
        Map<String, Integer> ticketsByMovie = new LinkedHashMap<>();
        
        // Revenue by Hall
        Map<String, Double> revenueByHall = new LinkedHashMap<>();
        Map<String, Integer> ticketsByHall = new LinkedHashMap<>();
        
        // Revenue by Customer
        Map<String, Double> revenueByCustomer = new LinkedHashMap<>();
        Map<String, Integer> ticketsByCustomer = new LinkedHashMap<>();
        
        for (TicketData t : bookings) {
            double price = t.getPrice();
            totalRevenue += price;
            
            // By Movie
            String movie = t.getMovieName();
            revenueByMovie.put(movie, revenueByMovie.getOrDefault(movie, 0.0) + price);
            ticketsByMovie.put(movie, ticketsByMovie.getOrDefault(movie, 0) + 1);
            
            // By Hall
            String hall = t.getHall();
            revenueByHall.put(hall, revenueByHall.getOrDefault(hall, 0.0) + price);
            ticketsByHall.put(hall, ticketsByHall.getOrDefault(hall, 0) + 1);
            
            // By Customer
            String customer = t.getCustomerName();
            revenueByCustomer.put(customer, revenueByCustomer.getOrDefault(customer, 0.0) + price);
            ticketsByCustomer.put(customer, ticketsByCustomer.getOrDefault(customer, 0) + 1);
        }
        
        // ===== LOAD F&B REVENUE =====
        double fbRevenue = 0;
        int fbItemsSold = 0;
        Map<String, Double> revenueByFBItem = new LinkedHashMap<>();
        Map<String, Integer> fbItemCountByName = new LinkedHashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("FoodBeveragesOrders.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5) {
                    try {
                        String itemName = parts[1];
                        int qty = Integer.parseInt(parts[2]);
                        double itemTotal = Double.parseDouble(parts[4]);
                        
                        fbRevenue += itemTotal;
                        fbItemsSold += qty;
                        
                        // Track by item
                        revenueByFBItem.put(itemName, revenueByFBItem.getOrDefault(itemName, 0.0) + itemTotal);
                        fbItemCountByName.put(itemName, fbItemCountByName.getOrDefault(itemName, 0) + qty);
                    } catch (NumberFormatException e) {
                        // Skip malformed lines
                    }
                }
            }
        } catch (Exception e) {
            // F&B file may not exist yet
        }
        
        // Update total revenue to include F&B
        double totalRevenueWithFB = totalRevenue + fbRevenue;

        // ===== SUMMARY CARDS =====
        JPanel summaryPanel = new JPanel(new GridLayout(2, 2, 12, 12));
        summaryPanel.setBackground(COLOR_BG);
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));

        summaryPanel.add(createSummaryCard("Total Revenue", "RM " + String.format("%.2f", totalRevenueWithFB), COLOR_SUCCESS));
        summaryPanel.add(createSummaryCard("Ticket Revenue", "RM " + String.format("%.2f", totalRevenue), COLOR_PRIMARY));
        summaryPanel.add(createSummaryCard("F&B Revenue", "RM " + String.format("%.2f", fbRevenue), COLOR_WARNING));
        summaryPanel.add(createSummaryCard("Items Sold", (totalTickets + fbItemsSold) + " units", COLOR_INFO));

        // ===== TABBED BREAKDOWN =====
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 12));
        tabbedPane.setBackground(COLOR_BG);

        // Tab 1: Revenue by Movie
        tabbedPane.addTab("By Movie", createBreakdownTable(
            new String[]{"Movie", "Tickets", "Revenue (RM)"},
            revenueByMovie, ticketsByMovie, totalRevenue
        ));

        // Tab 2: Revenue by Hall
        tabbedPane.addTab("By Hall", createBreakdownTable(
            new String[]{"Hall", "Tickets", "Revenue (RM)"},
            revenueByHall, ticketsByHall, totalRevenue
        ));

        // Tab 3: Revenue by Customer
        tabbedPane.addTab("By Customer", createBreakdownTable(
            new String[]{"Customer", "Tickets", "Revenue (RM)"},
            revenueByCustomer, ticketsByCustomer, totalRevenue
        ));
        
        // Tab 4: F&B Sales Breakdown
        tabbedPane.addTab("F&B Sales", createBreakdownTable(
            new String[]{"Item", "Quantity", "Revenue (RM)"},
            revenueByFBItem, fbItemCountByName, fbRevenue
        ));

        // ===== CENTER PANEL (Summary + Tabs) =====
        JPanel centerPanel = new JPanel(new BorderLayout(0, 5));
        centerPanel.setBackground(COLOR_BG);
        centerPanel.add(summaryPanel, BorderLayout.NORTH);
        centerPanel.add(tabbedPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // ===== BACK BUTTON =====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(COLOR_BG);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 20));

        JButton backBtn = new JButton("Back to Dashboard");
        backBtn.setFont(new Font("Arial", Font.BOLD, 13));
        backBtn.setBackground(COLOR_SECONDARY);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);
        backBtn.setPreferredSize(new Dimension(200, 38));
        backBtn.addActionListener(e -> {
            new ManagerDashboard();
            this.dispose();
        });
        bottomPanel.add(backBtn);

        add(bottomPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    /**
     * Creates a styled summary card with a title and value
     */
    private JPanel createSummaryCard(String title, String value, Color accentColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        titleLabel.setForeground(COLOR_SECONDARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLabel);

        card.add(Box.createVerticalStrut(5));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valueLabel.setForeground(accentColor);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(valueLabel);

        return card;
    }

    /**
     * Creates a breakdown table with name, ticket count, revenue, and % share columns
     */
    private JScrollPane createBreakdownTable(String[] baseColumns, Map<String, Double> revenueMap,
                                              Map<String, Integer> ticketsMap, double totalRevenue) {
        String[] columns = {baseColumns[0], baseColumns[1], baseColumns[2], "% Share"};
        
        // Sort entries by revenue (highest first)
        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(revenueMap.entrySet());
        // Selection sort by revenue descending
        for (int i = 0; i < sortedEntries.size() - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < sortedEntries.size(); j++) {
                if (sortedEntries.get(j).getValue() > sortedEntries.get(maxIdx).getValue()) {
                    maxIdx = j;
                }
            }
            if (maxIdx != i) {
                Map.Entry<String, Double> temp = sortedEntries.get(i);
                sortedEntries.set(i, sortedEntries.get(maxIdx));
                sortedEntries.set(maxIdx, temp);
            }
        }

        Object[][] data = new Object[sortedEntries.size()][4];
        for (int i = 0; i < sortedEntries.size(); i++) {
            String key = sortedEntries.get(i).getKey();
            double revenue = sortedEntries.get(i).getValue();
            int tickets = ticketsMap.getOrDefault(key, 0);
            double percent = totalRevenue > 0 ? (revenue / totalRevenue) * 100 : 0;

            data[i][0] = key;
            data[i][1] = tickets;
            data[i][2] = String.format("%.2f", revenue);
            data[i][3] = String.format("%.1f%%", percent);
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(COLOR_TEXT);

        // Style table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 13));
        tableHeader.setBackground(COLOR_PRIMARY);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setReorderingAllowed(false);
        tableHeader.setPreferredSize(new Dimension(0, 35));

        // Center-align numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        scrollPane.getViewport().setBackground(Color.WHITE);

        return scrollPane;
    }
}