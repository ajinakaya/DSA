package Question7;

import javax.swing.*;

import java.sql.*;



public class dbconnection {

    public Connection connection;

    Statement statement;

    ResultSet resultSet;

    int value;



    public dbconnection(){

        try {

            String username = "root";

            String password = "ajina kaya8860";

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(

                    "jdbc:mysql://localhost:3306/DSA",username,password);



            if(connection!=null){

                System.out.println("Connected to database");

            }else{

                System.out.println("Error connecting to database");

            }

            statement = connection.createStatement();

        }catch (Exception e){

            e.printStackTrace();

        }

    }

    public int manipulate(String query){

        try {

            value = statement.executeUpdate(query);

            connection.close();

        }catch (SQLIntegrityConstraintViolationException ex){

            JOptionPane.showMessageDialog(null, "These details already exist!");

        }catch (SQLException e){

            e.printStackTrace();

        }

        return value;

    }



    public ResultSet retrieve(String query){

        try {

            resultSet = statement.executeQuery(query);

        }catch (SQLException e){

            e.printStackTrace();

        }

        return resultSet;

    }



    public static void main(String[] args) {

        new dbconnection();

    }

    

}
