package Question7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends JFrame {

    final private Font mainFont = new Font("Segoe Print", Font.BOLD, 18);
    private JTextField tfEmail;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private SocialGraph socialGraph;
    private static dbconnection database; 

    public Register(SocialGraph socialGraph, dbconnection database) {
        this.socialGraph = socialGraph;
        this.database = database;
    }


    public void Initialize() {

        JLabel register = new JLabel("Register Form", SwingConstants.CENTER);
        register.setFont(mainFont);

        JLabel Email = new JLabel("Email");
        Email.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel Username = new JLabel("Username");
        Username.setFont(mainFont);

        tfUsername = new JTextField();
        tfUsername.setFont(mainFont);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(mainFont);

        pfPassword = new JPasswordField();
        pfPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10, 10));
        formPanel.add(register);
        formPanel.add(Email);
        formPanel.add(tfEmail);
        formPanel.add(Username);
        formPanel.add(tfUsername);
        formPanel.add(lbPassword);
        formPanel.add(pfPassword);

        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(mainFont);
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String username = tfUsername.getText();
                String password = String.valueOf(pfPassword.getPassword());

                // Register the user
                registerUser(username, email, password);
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonsPanel.add(btnRegister);

        add(formPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        setTitle("Register Form");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(400, 500);
        setMinimumSize(new Dimension(350, 450));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registerUser(String username, String email, String password) {
        if (socialGraph != null && !socialGraph.containsUser(username)) {
            // Create a User object
            User newUser = new User(username, email, password);
            

            // Insert data into the database
            if (insertUserDataIntoDatabase(username, email, password) > 0) {
                // Show a success message
                JOptionPane.showMessageDialog(this, "Registration successful!");

                // Open the Home Page
                openHomePage(username);
            } else {
                // Handle database insertion failure
                JOptionPane.showMessageDialog(this, "Error inserting data into the database");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists. Please choose another username.");
        }
    }

    private int insertUserDataIntoDatabase(String username, String email, String password) {
        String query = "INSERT INTO register (username, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = database.connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    private void openHomePage(String username) {
        
        Homepage homepage = new Homepage(username, socialGraph,database);
        homepage.initialize(username, socialGraph); 
        this.dispose(); 
    }

    public static void main(String[] args) {
        dbconnection database = new dbconnection();  
        SocialGraph socialGraph = new SocialGraph(database);
        Register register = new Register(socialGraph, database);
        register.Initialize();
    }
}
