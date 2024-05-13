import java.sql.*;

public class Main {

    static String databaseURL = "jdbc:mysql://localhost:3306/project";

    public static void main(String[] args) {

//        try {
//            Connection conn = DriverManager.getConnection(databaseURL,"root","C856500n.");
//
//            Statement statement = conn.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM userinfo");
//
//            while(resultSet.next())
//            {
//                System.out.println("USER ID:" + resultSet.getInt("userID"));
//                System.out.println("USERNAME:" + resultSet.getString("username"));
//                System.out.println("PASSWORD:" + resultSet.getString("password"));
//            }
//        }
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }

        DataBaseManager database = new DataBaseManager(databaseURL,"root","12345678");
        //database.AddNewVisit("Sheldon","Turkey","Izmir","Summer","Foods","!!",5);
        //database.DeleteVisit(0);



    }





}