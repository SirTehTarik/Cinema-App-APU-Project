/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CinemaAPP;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * ManagerDashboard extends BaseDashboard - demonstrates inheritance
 * Manager-specific dashboard with full administrative functionality
 * 
 * @author Mathew
 */
public class ManagerDashboard extends BaseDashboard {
    
    ManagerDashboard() {
        // Call parent constructor with specific title
        super("Manager Dashboard");
        // Display the dashboard (calls setupButtons via template method)
        display();
    }
    
    /**
     * Implementation of abstract method from BaseDashboard
     * This demonstrates polymorphism - Manager has different buttons than Clerk
     */
    @Override
    protected void setupButtons() {
        // Button 1: ADD MOVIE 
        JButton button1 = addButton("ADD MOVIE", 2, COLOR_SUCCESS);
        button1.addActionListener(e -> {new Movie(); this.dispose();});
        
        // Button 2: ADD UPCOMING
        JButton buttonUpcoming = addButton("ADD UPCOMING M.", 3, new Color(155, 89, 182));
        buttonUpcoming.addActionListener(e -> {new AddUpcomingMovie(); this.dispose();});
        
        // Button 3: MODIFY MOVIES
        JButton button2 = addButton("UPDATE MOVIES", 4, COLOR_INFO);
        button2.addActionListener(e -> {new ModifyMovies(); this.dispose();});

        // Button 4: REMOVE MOVIES
        JButton button3 = addButton("REMOVE MOVIES", 5, COLOR_WARNING);
        button3.addActionListener(e -> {new RemoveMovies(); this.dispose();});
        
        // Button 5: VIEW ALL MOVIES
        JButton button4 = addButton("VIEW MOVIES", 6, COLOR_PRIMARY);
        button4.addActionListener(e -> {new ViewMovies(); this.dispose();});
        
        // Button 6: MANAGE ALL USERS
        JButton button5 = addButton("MANAGE USERS", 7, new Color(52, 152, 219));
        button5.addActionListener(e -> {new ManageUsers(); this.dispose();});
        
        // Button 7: VIEW ALL REVENUE REPORTS
        JButton button6 = addButton("VIEW REVENUE", 8, new Color(230, 126, 34));
        button6.addActionListener(e -> {new ManagerReports(); this.dispose();});
        
        // Button 8: VIEW LOGIN HISTORY
        JButton button7 = addButton("LOGIN HISTORY", 9, new Color(52, 73, 94));
        button7.addActionListener(e -> {
            String history = FileHandler.readLoginHistory();
            JTextArea textArea = new JTextArea(15, 40);
            textArea.setText(history);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(this, scrollPane, "System Login History", JOptionPane.INFORMATION_MESSAGE);
        });

        // Button 9: LOGOUT
        JButton button8 = addButton("LOGOUT", 10, COLOR_SECONDARY);
        button8.addActionListener(e -> logout());
    } 
}
