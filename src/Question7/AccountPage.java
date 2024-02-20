package Question7;
 
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
 
public class AccountPage extends JFrame {
 
    private String username;
    private JLabel lblUsername;
    private JLabel lblEmail;
    private JPanel textAreaPosts;
    private dbconnection database;
 
    public AccountPage(String username, dbconnection database) {
        this.username = username;
        this.database = database;
    }
 
    public void initialize() {
        setTitle("Account Page");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
 
        JPanel mainPanel = new JPanel(new BorderLayout());
 
        // Panel for User Info
        JPanel userInfoPanel = new JPanel(new GridLayout(2, 1));
        lblUsername = new JLabel("Username: " + username);
        lblEmail = new JLabel("Email: " + getUserEmail(username));
        userInfoPanel.add(lblUsername);
        userInfoPanel.add(lblEmail);
 
        // Panel for posts
        textAreaPosts = new JPanel(new FlowLayout());
        JLabel lblPosts = new JLabel("User Posts:");
 
        JScrollPane scrollPane = new JScrollPane(textAreaPosts);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
 
        mainPanel.add(userInfoPanel, BorderLayout.NORTH);
        mainPanel.add(lblPosts, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
 
        add(mainPanel);
        setVisible(true);
 
        loadUserPosts(); // Call method to load user posts
    }
 
    // Method that retrieves the user's email from the database
    private String getUserEmail(String username) {
        String query = "SELECT email FROM register WHERE username = ?";
        try (PreparedStatement preparedStatement = database.connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return "N/A"; 
    }
 
    // Method to load user posts from the database
    private void loadUserPosts() {
        String query = "SELECT photo FROM posts WHERE username = ?";
        try (PreparedStatement preparedStatement = database.connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("photo");
                displayImage(imageBytes); // Call method to display image
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print error stack trace
        }
    }
 
    // Method to display image on the UI
    private void displayImage(byte[] imageBytes) {
        try {
            // Convert byte array to Image
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            Image image = ImageIO.read(bis);
 
            int width = 400;
            int height = 400;
            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
 
            // Create JLabel to display image
            JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
            textAreaPosts.add(imageLabel); // Add image to posts panel
 
            // Refresh posts panel to display new image
            textAreaPosts.revalidate();
            textAreaPosts.repaint();
 
        } catch (IOException e) {
            e.printStackTrace(); // Print error stack trace
        }
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Initialize database connection
            dbconnection database = new dbconnection();
            // Create instance of AccountPage and initialize UI
            AccountPage accountPage = new AccountPage("User123", database);
            accountPage.initialize();
        });
    }
}