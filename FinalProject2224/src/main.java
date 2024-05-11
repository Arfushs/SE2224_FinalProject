import java.util.Scanner;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/project";
        try {
            Connection conn = DriverManager.getConnection(url,"root","C856500n.");

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM userinfo");

            while(resultSet.next())
            {
                System.out.println("USER ID:" + resultSet.getInt("userID"));
                System.out.println("USERNAME:" + resultSet.getString("username"));
                System.out.println("PASSWORD:" + resultSet.getString("password"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}