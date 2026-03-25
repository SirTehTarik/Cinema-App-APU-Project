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
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ManageUsers extends JFrame {

    // Modern color palette
    private static final Color COLOR_BG = new Color(245, 247, 250);
    private static final Color COLOR_PRIMARY = new Color(74, 144, 226);
    private static final Color COLOR_SECONDARY = new Color(149, 165, 166);
    private static final Color COLOR_DANGER = new Color(231, 76, 60);
    private static final Color COLOR_SUCCESS = new Color(46, 204, 113);
    private static final Color COLOR_TEXT = new Color(44, 62, 80);

    private JTabbedPane tabbedPane;

    public ManageUsers() {
        this.setTitle("Manage System Users");
        this.setSize(700, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(COLOR_BG);

        // Create tabbed pane for different sections
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(COLOR_BG);
        
        // Tab 1: Add New User
        tabbedPane.addTab("Add New User", createAddUserPanel());
        
        // Tab 2: Manage Existing Users
        tabbedPane.addTab("Manage Users", createManageUsersPanel());

        this.add(tabbedPane);
        this.setVisible(true);
    }

    // ===== ADD NEW USER PANEL =====
    private JPanel createAddUserPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(COLOR_BG);

        // Title
        JLabel titleLabel = new JLabel("Add New User");
        titleLabel.setBounds(200, 25, 200, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT);
        panel.add(titleLabel);

        // Full Name
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(60, 80, 100, 25);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        nameLabel.setForeground(COLOR_TEXT);
        panel.add(nameLabel);
        JTextField nameText = new JTextField();
        nameText.setBounds(170, 80, 240, 30);
        nameText.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(nameText);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(60, 130, 100, 25);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        userLabel.setForeground(COLOR_TEXT);
        panel.add(userLabel);
        JTextField userText = new JTextField();
        userText.setBounds(170, 130, 240, 30);
        userText.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(userText);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(60, 180, 100, 25);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        passLabel.setForeground(COLOR_TEXT);
        panel.add(passLabel);
        JPasswordField passText = new JPasswordField();
        passText.setBounds(170, 180, 240, 30);
        passText.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(passText);

        // Role Selection
        JLabel roleLabel = new JLabel("System Role:");
        roleLabel.setBounds(60, 230, 100, 25);
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        roleLabel.setForeground(COLOR_TEXT);
        panel.add(roleLabel);
        String[] roles = {"Select Role...", "Clerk", "Manager", "Customer"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setBounds(170, 230, 240, 30);
        roleBox.setFont(new Font("Arial", Font.PLAIN, 13));
        panel.add(roleBox);

        // Buttons
        JButton addBtn = createButton("Add User", COLOR_PRIMARY);
        addBtn.setBounds(150, 300, 120, 40);
        panel.add(addBtn);

        JButton clearBtn = createButton("Clear", COLOR_SECONDARY);
        clearBtn.setBounds(290, 300, 120, 40);
        panel.add(clearBtn);

        JButton backBtn = createButton("Go Back", COLOR_SECONDARY);
        backBtn.setBounds(220, 360, 140, 40);
        panel.add(backBtn);

        // --- BUTTON LOGIC ---

        // Back Button Logic
        backBtn.addActionListener(e -> {
            new ManagerDashboard();
            this.dispose(); 
        });

        // Clear Button Logic
        clearBtn.addActionListener(e -> {
            nameText.setText("");
            userText.setText("");
            passText.setText("");
            roleBox.setSelectedIndex(0);
        });

        // Add User Button Logic
        addBtn.addActionListener(e -> {
            String fullName = nameText.getText().trim();
            String username = userText.getText().trim();
            String password = new String(passText.getPassword()).trim();
            String role = (String) roleBox.getSelectedItem();

            // Validation: Make sure no fields are left blank
            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || role.equals("Select Role...")) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields and select a role.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Check if username already exists
            if (FileHandler.isUsernameExists(username)) {
                JOptionPane.showMessageDialog(this, 
                    "Username '" + username + "' already exists!\nPlease choose a different username.", 
                    "Duplicate Username", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create the user object
            Users newUser = new Users(username, password, role, fullName);
            
            // Save to file
            FileHandler.addUser(newUser);
            JOptionPane.showMessageDialog(this, "User '" + fullName + "' successfully added!");

            // Clear the text fields
            nameText.setText("");
            userText.setText("");
            passText.setText("");
            roleBox.setSelectedIndex(0);
        });

        return panel;
    }

    // ===== MANAGE USERS PANEL =====
    private JPanel createManageUsersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(COLOR_BG);

        // Title
        JLabel titleLabel = new JLabel("Manage Existing Users");
        titleLabel.setBounds(150, 15, 300, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(COLOR_TEXT);
        panel.add(titleLabel);

        // Create table for displaying users
        String[] columnNames = {"Username", "Full Name", "Role", "Action"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        JTable userTable = new JTable(tableModel);
        userTable.setFont(new Font("Arial", Font.PLAIN, 12));
        userTable.setRowHeight(25);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(20, 60, 640, 300);
        panel.add(scrollPane);

        // Load users into table
        loadUsersIntoTable(tableModel);

        // Buttons for user actions
        JButton changeRoleBtn = createButton("Change Role", COLOR_SUCCESS);
        changeRoleBtn.setBounds(50, 380, 140, 40);
        panel.add(changeRoleBtn);

        JButton deleteUserBtn = createButton("Delete User", COLOR_DANGER);
        deleteUserBtn.setBounds(230, 380, 140, 40);
        panel.add(deleteUserBtn);

        JButton refreshBtn = createButton("Refresh", COLOR_PRIMARY);
        refreshBtn.setBounds(410, 380, 140, 40);
        panel.add(refreshBtn);

        JButton backBtn = createButton("Go Back", COLOR_SECONDARY);
        backBtn.setBounds(240, 440, 140, 40);
        panel.add(backBtn);

        // --- BUTTON LOGIC ---

        // Refresh Button
        refreshBtn.addActionListener(e -> {
            tableModel.setRowCount(0);
            loadUsersIntoTable(tableModel);
            JOptionPane.showMessageDialog(this, "User list refreshed!");
        });

        // Change Role Button
        changeRoleBtn.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user to change role.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String username = (String) tableModel.getValueAt(selectedRow, 0);
            String currentRole = (String) tableModel.getValueAt(selectedRow, 2);

            // Show dialog to select new role
            String[] roles = {"Clerk", "Manager", "Customer"};
            String newRole = (String) JOptionPane.showInputDialog(
                this,
                "Select new role for " + username + ":",
                "Change User Role",
                JOptionPane.QUESTION_MESSAGE,
                null,
                roles,
                currentRole
            );

            if (newRole != null && !newRole.equals(currentRole)) {
                // Update the user's role
                if (updateUserRole(username, newRole)) {
                    tableModel.setRowCount(0);
                    loadUsersIntoTable(tableModel);
                    JOptionPane.showMessageDialog(this, "User role updated successfully to '" + newRole + "'!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update user role.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete User Button
        deleteUserBtn.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String username = (String) tableModel.getValueAt(selectedRow, 0);
            String fullName = (String) tableModel.getValueAt(selectedRow, 1);

            // Confirm deletion
            int confirmDelete = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete user '" + fullName + "' (" + username + ")?\nThis action cannot be undone!",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirmDelete == JOptionPane.YES_OPTION) {
                if (deleteUser(username)) {
                    tableModel.setRowCount(0);
                    loadUsersIntoTable(tableModel);
                    JOptionPane.showMessageDialog(this, "User deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete user.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back Button Logic
        backBtn.addActionListener(e -> {
            new ManagerDashboard();
            this.dispose();
        });

        return panel;
    }

    // ===== HELPER METHODS =====

    /**
     * Load all users from file and populate the table
     */
    private void loadUsersIntoTable(DefaultTableModel tableModel) {
        List<Users> usersList = FileHandler.loadAllUsers();
        for (Users user : usersList) {
            Object[] row = {user.getUsername(), user.getFullName(), user.getRole()};
            tableModel.addRow(row);
        }
    }

    /**
     * Delete a user from the system
     */
    private boolean deleteUser(String username) {
        List<Users> usersList = FileHandler.loadAllUsers();
        boolean removed = usersList.removeIf(user -> user.getUsername().equalsIgnoreCase(username));
        
        if (removed) {
            FileHandler.saveAllUsers(usersList);
            return true;
        }
        return false;
    }

    /**
     * Update a user's role
     */
    private boolean updateUserRole(String username, String newRole) {
        List<Users> usersList = FileHandler.loadAllUsers();
        for (Users user : usersList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                user.setRole(newRole);
                FileHandler.saveAllUsers(usersList);
                return true;
            }
        }
        return false;
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
}

