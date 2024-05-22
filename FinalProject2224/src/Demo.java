import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

public class Demo extends JFrame{
    static String databaseURL = "jdbc:mysql://127.0.0.1:3306/project";
    static String databaseUser = "root";
    static String databasePass = "C856500n.";
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


    public Demo() {
//        tableButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ShowTable();
//            }
//        });
        addLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean b = cityTextField.getText().length() > 0 && countryTextField.getText().length() >0 &&
                        seasonTextField.getText().length() > 0 && bestFeatureTextField.getText().length() >0 &&
                        commentTextField.getText().length() > 0 && ratingTextField.getText().length() >0 &&
                        yearTextField.getText().length() > 0;

                if (b) {
                    String Username = "Can", Country = countryTextField.getText(), City = cityTextField.getText(), Year = yearTextField.getText(),
                            Season = seasonTextField.getText(), BestFeature = bestFeatureTextField.getText(), Comment = commentTextField.getText(),
                    Rating = ratingTextField.getText();
                    dataBaseManager.AddNewVisit(Username,Country,City,Integer.valueOf(Year),Season,BestFeature,Comment,Integer.valueOf(Rating));
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
    }

    public void ShowTable()
    {
        try {
            Connection conn = DriverManager.getConnection(databaseURL,databaseUser,databasePass);
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

    public void UpdateTables()
    {
        ShowTable();
        ShowBestFoodsList();
    }

    public static void main(String[] args) {

        dataBaseManager = new DataBaseManager(databaseURL,databaseUser,databasePass);

        Demo demo = new Demo();
        demo.setTitle("Project");
        demo.setContentPane(demo.mainPanel);
        demo.setSize(800,500);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setVisible(true);
        demo.UpdateTables();

    }

}
