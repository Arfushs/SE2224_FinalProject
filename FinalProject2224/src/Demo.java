import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Demo extends JFrame{

    static DataBaseManager dataBaseManager;

    private JPanel mainPanel;
    //private JButton tableButton;
    private JTable dataTable;
    private JButton addLocationButton;
    private JTextField countryTextField;
    private JTextField cityTextField;
    private JTextField yearTextField;
    private JTextField seasonTextField;
    private JTextField bestFeatureTextField;
    private JTextField commentTextField;
    private JTextField ratingTextField;
    private JButton deleteVisitButton;
    private JTextField deleteWVITextField;
    private JList bestFoodList;
    private JButton displayImageButton;
    private JTextField visitIdTextField;
    private JLabel ImageLabel;
    private JButton displayVisitsButton;
    private JTextField visitYearTextField;
    private JList visitYearList;
    private JList mvcList;
    private JList vosList;
    private JButton shareVisitIDButton;
    private JTextField shareVisitTextField;
    private JTextField friendsUNTextField;
    private JButton sharedWithMeButton;
    private JTextField swmFriendsTextField;
    private JList sharedWMList;
    private JButton UpdateTestButton;


    public Demo() {
//        tableButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ShowTable();
//            }
//        });
        setContentPane(mainPanel);
        dataBaseManager = new DataBaseManager(DatabaseSingleton.databaseURL,DatabaseSingleton.databaseUser,DatabaseSingleton.databasePass);
        UpdateTables();

        addLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean b = cityTextField.getText().length() > 0 && countryTextField.getText().length() >0 &&
                        seasonTextField.getText().length() > 0 && bestFeatureTextField.getText().length() >0 &&
                        commentTextField.getText().length() > 0 && ratingTextField.getText().length() >0 &&
                        yearTextField.getText().length() > 0;

                if (b) {
                    String Country = countryTextField.getText(), City = cityTextField.getText(), Year = yearTextField.getText(),
                            Season = seasonTextField.getText(), BestFeature = bestFeatureTextField.getText(), Comment = commentTextField.getText(),
                    Rating = ratingTextField.getText();
                    dataBaseManager.AddNewVisit(DatabaseSingleton.Username,Country,City,Integer.valueOf(Year),Season,BestFeature,Comment,Integer.valueOf(Rating));
                    UpdateTables();
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter all the fields..");
                }

            }
        });
        deleteVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(deleteWVITextField.getText());
                dataBaseManager.DeleteVisit(id);
                JOptionPane.showMessageDialog(null, "The visit has been deleted. (Visit ID: " +id+")");
                UpdateTables();

            }
        });
        displayImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(visitIdTextField.getText());
                String country_name = dataBaseManager.GetCountryNameByID(id);
                System.out.println(country_name);
                ImageIcon icon;
                if(country_name.equals("England"))
                    icon = new ImageIcon("Images/Location4.jpg");
                else if(country_name.equals("Turkey"))
                    icon = new ImageIcon("Images/Location3.jpg");
                else if(country_name.equals("Italy"))
                    icon = new ImageIcon("Images/Location2.jpg");
                else if(country_name.equals("Japan"))
                    icon = new ImageIcon("Images/Location1.jpg");
                else if(country_name.equals("America"))
                    icon = new ImageIcon("Images/Location5.jpg");
                else icon = new ImageIcon("Images/question.jpg");

                ImageLabel.setIcon(icon);
            }
        });
        displayVisitsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = Integer.parseInt(visitYearTextField.getText());
                DefaultListModel<String> myListModel = new DefaultListModel<>();
                List<String> dataList = dataBaseManager.GetVisitInfoByYear(year);
                int i = 1;
                for(String data : dataList)
                {
                    myListModel.addElement(i+") "+data);
                    i++;
                }
                visitYearList.setModel(myListModel);
            }
        });
        shareVisitIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int visitId = Integer.parseInt(shareVisitTextField.getText());
                String friend_username = friendsUNTextField.getText();
                dataBaseManager.ShareVisitIDWithFriend(DatabaseSingleton.Username,friend_username,visitId);
                JOptionPane.showMessageDialog(null, "This visit has been shared (Visit ID: " +visitId+") with " + friend_username );
            }
        });
        sharedWithMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String friendUsername = swmFriendsTextField.getText();
                DefaultListModel<String> myListModel = new DefaultListModel<>();
                List<String> dataList = dataBaseManager.SharedWithMe(DatabaseSingleton.Username,friendUsername);
                for(String data : dataList)
                {
                    myListModel.addElement("- "+data);
                }
                sharedWMList.setModel(myListModel);
            }
        });
        UpdateTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateDataTable();
                UpdateTables();
            }
        });
    }

    public void ShowTable()
    {
        try {
            Connection conn = DriverManager.getConnection(DatabaseSingleton.databaseURL,DatabaseSingleton.databaseUser,DatabaseSingleton.databasePass);
            Statement statement = conn.createStatement();
            String query = "select * from visits";
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int cols = rsmd.getColumnCount();
            String[] names;
            names = new String[cols];
            for(int i = 0; i < cols; i++)
                names[i] = rsmd.getColumnName(i+1);

            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setColumnIdentifiers(names);

            // Önceki rowları temizlemek için
            for(int i = 0; i < cols; i++)
                if(model.getRowCount() >0)
                    model.removeRow(0);

            String username,country_name,city_name,season,feature,comment;
            int visitID,rate,year_visited;
            while(resultSet.next())
            {
                visitID = resultSet.getInt(1);
                rate = resultSet.getInt(9);
                username = resultSet.getString(2);
                country_name = resultSet.getString(3);
                city_name = resultSet.getString(4);
                year_visited = resultSet.getInt(5);
                season = resultSet.getString(6);
                feature = resultSet.getString(7);
                comment = resultSet.getString(8);
                String row[] = {String.valueOf(visitID),username,country_name,city_name,String.valueOf(year_visited),season,feature,comment, String.valueOf(rate)};
                model.addRow(row);
            }
            statement.close();
            conn.close();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void ShowBestFoodsList()
    {
        DefaultListModel<String> myListModel = new DefaultListModel<>();
        List<String> countryList = dataBaseManager.GetBestFoodsByRating();
        int i = 1;
        for(String country : countryList)
        {
            myListModel.addElement(i+". "+country);
            i++;
        }

        bestFoodList.setModel(myListModel);
    }

    public void ShowMostVisitedCountry()
    {
        DefaultListModel<String> myListModel = new DefaultListModel<>();
        List<String> countryList = dataBaseManager.GetMostVisitedCountry();
        for(String country : countryList)
        {
            myListModel.addElement("- "+country);

        }

        mvcList.setModel(myListModel);
    }

    public void ShowVisitedCountryOnlySpring()
    {
        DefaultListModel<String> myListModel = new DefaultListModel<>();
        List<String> countryList = dataBaseManager.GetVisitedCountryOnlySpring();
        for(String country : countryList)
        {
            myListModel.addElement("- "+country);

        }

        vosList.setModel(myListModel);
    }

    public void UpdateTables()
    {
        ShowTable();
        ShowBestFoodsList();
        ShowMostVisitedCountry();
        ShowVisitedCountryOnlySpring();
    }

    private void UpdateDataTable() {

        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();


        for (int i = 0; i < model.getRowCount(); i++) {
            List<String> rowData = new ArrayList<>();
            for (int j = 0; j < model.getColumnCount(); j++) {
                //rowData[i] = (String) model.getValueAt(i, j);
                rowData.add(model.getValueAt(i, j).toString());
                //System.out.println(rowData[i]);
            }

            dataBaseManager.UpdateTable(rowData);
        }

    }


    public static void main(String[] args) {

        Demo demo = new Demo();
        demo.setTitle("Project");
        //demo.setContentPane(demo.mainPanel);
        demo.setSize(800,850);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setVisible(true);


    }

}
