import java.sql.*;

public class DataBaseManager {

    static String databaseURL = "";
    static String databaseUser = "";
    static String databasePass = "";

    public DataBaseManager(String databaseURL, String databaseUser, String databasePass)
    {
        this.databaseURL = databaseURL;
        this.databaseUser = databaseUser;
        this.databasePass = databasePass;
    }

    public void AddNewVisit(String username,String countryName,String cityName, String season, String feature,String comment,int rating)
    {
        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);

            Statement statement = conn.createStatement();
            String visitIDQuery = "select visitID from visits order by visitID desc limit 1";
            ResultSet resultSet = statement.executeQuery(visitIDQuery);
            int visitID =  resultSet.next() ? resultSet.getInt("userID") + 1: 0;

            PreparedStatement preparedStatement = conn.prepareStatement("insert into visits(visitID,username,country_name,city_name,season,feature,comment,rating) values(?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, visitID);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, countryName);
            preparedStatement.setString(4, cityName);
            preparedStatement.setString(5, season);
            preparedStatement.setString(6, feature);
            preparedStatement.setString(7, comment);
            preparedStatement.setInt(8, rating);
            preparedStatement.executeUpdate();

            System.out.println("The new entry has been successfully added to the database !");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void DeleteVisit(int visitID)
    {
        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
            PreparedStatement preparedStatement = conn.prepareStatement("delete from visits where visitID = ?");
            preparedStatement.setInt(1, visitID);
            preparedStatement.executeUpdate();
            System.out.println("This visits: " + visitID + " has been deleted from the database !");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


}
