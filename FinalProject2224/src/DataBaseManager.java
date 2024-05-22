import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public void AddNewVisit(String username,String countryName,String cityName, int yearVisited,String season, String feature,String comment,int rating)
    {
        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);

            Statement statement = conn.createStatement();
            String visitIDQuery = "select visitID from visits order by visitID desc limit 1";
            ResultSet resultSet = statement.executeQuery(visitIDQuery);
            int visitID =  resultSet.next() ? resultSet.getInt("visitID") + 1: 0;

            PreparedStatement preparedStatement = conn.prepareStatement("insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating) values(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, visitID);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, countryName);
            preparedStatement.setString(4, cityName);
            preparedStatement.setInt(5, yearVisited);
            preparedStatement.setString(6, season);
            preparedStatement.setString(7, feature);
            preparedStatement.setString(8, comment);
            preparedStatement.setInt(9, rating);
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

    public List<String> GetBestFoodsByRating()
    {
        List<String> countryList = new ArrayList<String>();
        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
            PreparedStatement statement = conn.prepareStatement("SELECT country_name, rating\n" +
                    "FROM (\n" +
                    "    SELECT country_name, MAX(rating) AS rating\n" +
                    "    FROM visits\n" +
                    "    WHERE UPPER(feature) LIKE '%FOOD%'\n" +
                    "    GROUP BY country_name\n" +
                    ") subquery\n" +
                    "ORDER BY rating DESC;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                countryList.add(resultSet.getString("country_name"));

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        //System.out.println(countryList);
        return countryList;
    }

    public String GetCountryNameByID(int visitID)
    {
        String countryName = "";
        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
            Statement statement = conn.createStatement();
            String visitIDQuery = "select country_name from visits where visitID = " + visitID;
            ResultSet resultSet = statement.executeQuery(visitIDQuery);
            countryName = resultSet.next() ? resultSet.getString("country_name") : null;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return countryName;
    }


}
