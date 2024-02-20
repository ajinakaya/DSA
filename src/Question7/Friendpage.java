package Question7;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// JFrame class representing the friend page
public class Friendpage extends JFrame {

    private User loggedInUser;
    private SocialGraph socialGraph;
    private static dbconnection database;

    public Friendpage(User loggedInUser, SocialGraph socialGraph, dbconnection database) {
        this.loggedInUser = loggedInUser;
        this.socialGraph = socialGraph;
        this.database = database;
    }

      // Method to initialize the friend page
    public void initialize() {
       
        // Fetch all users from the register table
        List<String> allUsers = getAllUsersFromRegister(database);

         // If no users found, display a message and dispose of the frame
        if (allUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No users found.");
            dispose(); 
        } else {

         // Converting  the list of users to an array for the JOptionPane
            Object[] options = allUsers.toArray();

            // Displaying  a dialog for the user to select a friend
            String selectedFriend = (String) JOptionPane.showInputDialog(
                    this,
                    "Select a friend:",
                    "Add Friend",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    null
            );
    
            // If a friend is selected
            if (selectedFriend != null && !selectedFriend.isEmpty()) {
                System.out.println("Selected Friend: " + selectedFriend);
    
                // Get the User object for the selected friend from the social graph
                User friend = socialGraph.getUser(selectedFriend);
    
                if (friend != null) {
                    System.out.println("Friend found in socialGraph.");
                    
                    // Check if the logged-in user is not null
                    if (loggedInUser != null) {
                        loggedInUser.addFriend(friend);
                        JOptionPane.showMessageDialog(this, "Friend added successfully!");
                    } else {
                        System.out.println("Error: Logged-in user is null.");
                    }
                } else {
                    System.out.println("Friend not found in socialGraph.");
                    JOptionPane.showMessageDialog(this, "User not found!");
                }
            }
        }
    
       
        
    }
    

    // Method to get all users from the register table in the database
    private List<String> getAllUsersFromRegister(dbconnection database) {
        List<String> allUsers = new ArrayList<>();

        String query = "SELECT username FROM register";
        try (PreparedStatement preparedStatement = database.connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                System.out.println("Username from database: " + username);
                allUsers.add(username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                dbconnection database = new dbconnection();
                SocialGraph socialGraph = new SocialGraph(database);
                User loggedInUser = socialGraph.getUser("User123");
                Friendpage friendPage = new Friendpage(loggedInUser, socialGraph, database);
                friendPage.initialize();
            }
        });
    }
}
