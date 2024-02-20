package Question7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// JFrame class representing the homepage
public class Homepage extends JFrame {

    private JLabel lblUserName;
    private JTextArea textAreaRecommendations;
    private String initialHomeContent;
    private String username;
    private SocialGraph socialGraph;
    private static dbconnection database;
    private User loggedInUser; 

    public Homepage(String username, SocialGraph socialGraph, dbconnection database) {
        this.username = username;
        this.socialGraph = socialGraph;
        this.database = database;
        this.loggedInUser = socialGraph.getUser(username); 
    }

    // Method to initialize the homepage GUI
    public void initialize(String userName, SocialGraph socialGraph) {
        this.socialGraph = socialGraph;

        setTitle("Home Page");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Navigation Bar
        JPanel navBar = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton btnAddFriend = new JButton("Add Friend", new ImageIcon("add_friend_icon.png"));
        JButton btnPost = new JButton("Post", new ImageIcon("post_icon.png"));
        JButton btnAccount = new JButton("Account", new ImageIcon("account_icon.png"));

         // ActionListener for Add Friend button
        btnAddFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFriendpage(loggedInUser); // 
            }
        });

         // ActionListener for Post button
        btnPost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPostPage(username);
            }
        });

        // ActionListener for Account button
        btnAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAccountPage(username);
            }
        });

        navBar.add(btnAddFriend);
        navBar.add(btnPost);
        navBar.add(btnAccount);

        // User Info Panel
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblUserName = new JLabel("Welcome, " + userName);
        userInfoPanel.add(lblUserName);

        // Recommendations Panel
        JPanel recommendationsPanel = new JPanel(new BorderLayout());
        JLabel lblRecommendations = new JLabel("Recommended Images:");
        textAreaRecommendations = new JTextArea();
        textAreaRecommendations.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textAreaRecommendations);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        recommendationsPanel.add(lblRecommendations, BorderLayout.NORTH);
        recommendationsPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(navBar, BorderLayout.NORTH);
        mainPanel.add(userInfoPanel, BorderLayout.CENTER);
        mainPanel.add(recommendationsPanel, BorderLayout.SOUTH);

        initialHomeContent = textAreaRecommendations.getText();

        add(mainPanel);
        setVisible(true);
    }

    // Method to open the friend page
    private void openFriendpage(User loggedInUser) {
        Friendpage friendpage = new Friendpage(loggedInUser, socialGraph, database);
        friendpage.initialize();
        friendpage.setVisible(true);
    }

     // Method to open the post page
    private void openPostPage(String username) {
        PostPage postPage = new PostPage(username, database);
        postPage.initialize();
        postPage.setVisible(true);
    }

      // Method to open the account page
    private void openAccountPage(String username) {
        AccountPage accountPage = new AccountPage(username, database);
        accountPage.initialize();
        accountPage.setVisible(true);
    }

    // Setter method to set the logged-in user
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    // Method to display recommended images in the text area
    public void displayRecommendedImage(byte[] photoBytes) {
        ImageIcon imageIcon = new ImageIcon(photoBytes);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH); 
        imageIcon = new ImageIcon(scaledImage);
        textAreaRecommendations.append("\n"); 
        textAreaRecommendations.setCaretPosition(textAreaRecommendations.getDocument().getLength()); 
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
