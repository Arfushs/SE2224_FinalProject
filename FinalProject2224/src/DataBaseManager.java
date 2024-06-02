import java.sql.*;
import java.util.*;

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

    public List<String> GetVisitInfoByYear(int year)
    {
        List<String> st = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
            PreparedStatement statement = conn.prepareStatement("select * from visits where year_visited = " + year);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                String data = "";
                data += resultSet.getString("visitID");
                data += ", " + resultSet.getString("username");
                data += ", " + resultSet.getString("country_name");
                data += ", " + resultSet.getString("city_name");
                data += ", " + resultSet.getInt("year_visited");
                data += ", " + resultSet.getString("season");
                data += ", " + resultSet.getString("feature");
                data += ", " + resultSet.getString("comment");
                data += ", " + resultSet.getInt("rating");
                st.add(data);
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return st;

    }

    public List<String> GetMostVisitedCountry()
    {
        List<String> st = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
            PreparedStatement statement = conn.prepareStatement("SELECT country_name, COUNT(*) AS visit_count\n" +
                    "FROM visits\n" +
                    "GROUP BY country_name\n" +
                    "HAVING COUNT(*) = (\n" +
                    "    SELECT MAX(visit_count)\n" +
                    "    FROM (\n" +
                    "        SELECT COUNT(*) AS visit_count\n" +
                    "        FROM visits\n" +
                    "        GROUP BY country_name\n" +
                    "    ) AS country_visits\n" +
                    ");");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                st.add(resultSet.getString("country_name"));

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return st;
    }

    public List<String> GetVisitedCountryOnlySpring()
    {
        List<String> st = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
            PreparedStatement statement = conn.prepareStatement("SELECT country_name\n" +
                    "FROM visits\n" +
                    "GROUP BY country_name\n" +
                    "HAVING SUM(CASE WHEN season = 'spring' THEN 1 ELSE 0 END) > 0\n" +
                    "   AND SUM(CASE WHEN season <> 'spring' THEN 1 ELSE 0 END) = 0;\n");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                st.add(resultSet.getString("country_name"));

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return st;
    }


    public void ShareVisitIDWithFriend(String username,String friendsUsername,int visitID)
    {
        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
            PreparedStatement preparedStatement = conn.prepareStatement("insert into sharedvisits(shared_visitID,username,friend_username) values(?,?,?)");
            preparedStatement.setInt(1, visitID);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, friendsUsername);
            preparedStatement.executeUpdate();
            System.out.println("VisitID Shared with : " + friendsUsername);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<String> SharedWithMe(String username,String friendsUsername)
    {
        List<String> st = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
            PreparedStatement statement = conn.prepareStatement("SELECT country_name, city_name, season, feature "
                    + "FROM visits "
                    + "WHERE visitID IN (SELECT shared_visitID FROM sharedvisits WHERE username = ? AND friend_username = ?)");
            statement.setString(1, friendsUsername);
            statement.setString(2, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                String data = "";
                data += resultSet.getString("country_name");
                data += ", " + resultSet.getString("city_name");
                data += ", " + resultSet.getString("season");
                data += ", " + resultSet.getString("feature");
                st.add(data);

            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return st;
    }


    public boolean CheckLogin(String username, String password)
    {
        HashMap<String, String> dictionary = new HashMap<String, String>();;
        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
            PreparedStatement statement = conn.prepareStatement("SELECT username,password from userinfo");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                //System.out.println(resultSet.getString("username") +" " + resultSet.getString("password"));
                dictionary.put(resultSet.getString("username"), resultSet.getString("password"));

            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (dictionary.containsKey(username))
            if(dictionary.get(username).equals(password))
                return true;

        return false;
    }
}
