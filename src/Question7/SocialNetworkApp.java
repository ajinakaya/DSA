package Question7;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SocialNetworkApp extends JFrame {

    private JTextField textFieldUsername, textFieldPost;
    private JTextArea textArea;
    private List<User> userList;

    public SocialNetworkApp() {
        setTitle("Social Media Recommendation System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userList = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(150, 10, 150, 20);
        panel.add(textFieldUsername);

        textFieldPost = new JTextField();
        textFieldPost.setBounds(150, 40, 150, 20);
        panel.add(textFieldPost);

        JButton btnCreateUser = new JButton("Create User");
        btnCreateUser.setBounds(10, 10, 120, 20);
        btnCreateUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createUser(textFieldUsername.getText());
                textFieldUsername.setText("");
            }
        });
        panel.add(btnCreateUser);

        JButton btnAddPost = new JButton("Add Post");
        btnAddPost.setBounds(10, 40, 120, 20);
        btnAddPost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPost(textFieldUsername.getText(), textFieldPost.getText());
                textFieldPost.setText("");
            }
        });
        panel.add(btnAddPost);

        JButton btnRecommend = new JButton("Recommend Content");
        btnRecommend.setBounds(10, 70, 150, 20);
        btnRecommend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                recommendContent(textFieldUsername.getText());
            }
        });
        panel.add(btnRecommend);

        textArea = new JTextArea();
        textArea.setBounds(150, 70, 200, 150);
        panel.add(textArea);

        add(panel);
    }

    private void createUser(String username) {
        User newUser = new User(username, username, username);
        userList.add(newUser);
        textArea.append("User created: " + username + "\n");
    }

    private void addPost(String username, String postContent) {
        User user = findUserByUsername(username);

        if (user != null) {
            user.addPost(postContent);
            textArea.append("Post added by " + username + ": " + postContent + "\n");
        } else {
            textArea.append("User not found\n");
        }
    }

    private void recommendContent(String username) {
        User user = findUserByUsername(username);

        if (user != null) {
            // Implement your recommendation algorithm here
            // For simplicity, let's just show the user's posts as a recommendation
            List<String> userPosts = user.getPosts();
            textArea.append("Recommended Content for " + username + ":\n");
            for (String post : userPosts) {
                textArea.append("- " + post + "\n");
            }
        } else {
            textArea.append("User not found\n");
        }
    }

    private User findUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SocialNetworkApp().setVisible(true);
            }
        });
    }
}
