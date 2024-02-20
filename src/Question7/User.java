package Question7;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
 
// Represents a user in the system.
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private List<User> friends;
    private List<String> posts;
    private static String loggedInUsername;
 
    
     // Constructs a User object with the specified username, email, and password.
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.friends = new ArrayList<>();
        this.posts = new ArrayList<>();
    }
 
    
     // Gets the username of the user.
    public String getUsername() {
        return username;
    }
 
    
     // Gets the email of the user.
    public String getEmail() {
        return email;
    }
 
    
    // Gets the password of the user.
    public String getPassword() {
        return password;
    }
 
    
     // Gets the list of friends of the user.
     public List<User> getFriends() {
        return friends;
    }
 
    
     //Gets the ID of the user.
    public int getId() {
        return id;
    }
 
    
     // Gets the list of posts made by the user.
    public List<String> getPosts() {
        return posts;
    }
 
    
     // Adds a friend to the user's list of friends.
    public void addFriend(User friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
            friend.friends.add(this);
        }
    }
 
    
      //Adds a post made by the user.
    public void addPost(String postContent) {
        posts.add(postContent);
    }
 
    
     // Returns a string representation of the user.
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
 
    
     // Gets the username of the currently logged-in user.
    public static String getLoggedInUsername() {
        return loggedInUsername;
    }
 
    
     // Sets the username of the currently logged-in user.
    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }
 
    
     // Adds a friend relationship between two users and stores it in the database.
    public void addFriend(User friend, dbconnection database) {
        if (!friends.contains(friend)) {
            friends.add(friend);
            friend.friends.add(this);
 
            // Add friendship to the database
            String query = "INSERT INTO friendships (user_id, friend_id) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = database.connection.prepareStatement(query)) {
                preparedStatement.setInt(1, getId());
                preparedStatement.setInt(2, friend.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}