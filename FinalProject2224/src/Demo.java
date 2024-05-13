import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Demo extends JFrame{
    static String databaseURL = "jdbc:mysql://localhost:3306/project";
    static String databaseUser = "root";
    static String databasePass = "12345678";
    static DataBaseManager dataBaseManager;

    private JPanel mainPanel;
    private JButton button1;
    private JTable dataTable;


    public Demo() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowTable();
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
            int visitID,rate;
            while(resultSet.next())
            {
                visitID = resultSet.getInt(1);
                rate = resultSet.getInt(8);
                username = resultSet.getString(2);
                country_name = resultSet.getString(3);
                city_name = resultSet.getString(4);
                season = resultSet.getString(5);
                feature = resultSet.getString(6);
                comment = resultSet.getString(7);
                String row[] = {String.valueOf(visitID),username,country_name,city_name,season,feature,comment, String.valueOf(rate)};
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

    public static void main(String[] args) {

        dataBaseManager = new DataBaseManager(databaseURL,databaseUser,databasePass);

        Demo demo = new Demo();
        demo.setContentPane(demo.mainPanel);
        demo.setSize(600,500);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setVisible(true);
    }

}
