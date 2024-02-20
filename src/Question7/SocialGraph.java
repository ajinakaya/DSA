package Question7;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

public class SocialGraph {
    private Map<String, User> users;
    private dbconnection database;
    private Homepage homepage;

    public SocialGraph(dbconnection database) {
        this.users = new HashMap<>();
        this.database = database;
        loadUsersFromDatabase(); 
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void addFriend(User user, User friend) {
        user.addFriend(friend);
        friend.addFriend(user);
    }

    public boolean containsUser(String username) {
        return users.containsKey(username);
    }


    private void loadUsersFromDatabase() {
        String query = "SELECT username, email, password FROM register";
        try (PreparedStatement preparedStatement = database.connection.prepareStatement(query)) {
             ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                User user = new User(username, email, password);
                addUser(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printGraph() {
        System.out.println("Social Graph:");
        for (User user : users.values()) {
            System.out.println(user);
            System.out.println("  Friends: " + user.getFriends());
        }
        System.out.println();
    }

    public List<PostPage> getAllPosts() {
        List<PostPage> allPosts = new ArrayList<>();
    
        String query = "SELECT username, photo FROM posts";
        try (PreparedStatement preparedStatement = database.connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                
                // Retrieve the photo as a byte array
                byte[] photoBytes = resultSet.getBytes("photo");
                
                // Create a PostPage instance with the retrieved data
                PostPage post = new PostPage(username, database);
                

                homepage.displayRecommendedImage(photoBytes);
    
                allPosts.add(post);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return allPosts;
    }

public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {

            dbconnection database = new dbconnection();
            SocialGraph socialGraph = new SocialGraph(database);
            socialGraph.printGraph();
            User loggedInUser = new User("User123", "user123@example.com", "password");
            Homepage homepage = new Homepage("User123", socialGraph, database);
            homepage.initialize("User123", socialGraph);
            homepage.setLoggedInUser(loggedInUser);
        }
    });
}
}
