package Question7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homepage extends JFrame {

    private JLabel lblUserName;
    private JTextArea textAreaRecommendations;
    private String initialHomeContent;
    private String username;
    private SocialGraph socialGraph;


    public Homepage(String username, SocialGraph socialGraph) {
        this.username = username;
        this.socialGraph = socialGraph;
    }

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
        JButton btnHome = new JButton("Home", new ImageIcon("home_icon.png"));
        JButton btnPost = new JButton("Post", new ImageIcon("post_icon.png"));
        JButton btnAccount = new JButton("Account", new ImageIcon("account_icon.png"));

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaRecommendations.setText(initialHomeContent);
                textAreaRecommendations.append(socialGraph.toString());
            }
        });

        btnPost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaRecommendations.setText("Post Page");
            }
        });

        btnAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaRecommendations.setText("Account Page");
            }
        });

        navBar.add(btnHome);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SocialGraph socialGraph = new SocialGraph();
                Homepage homepage = new Homepage("User123", socialGraph);
                homepage.initialize("User123", socialGraph);
            }
        });
    }
    
}