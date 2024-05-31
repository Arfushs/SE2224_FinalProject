import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel mainPanel;
    private DataBaseManager dataBaseManager;

    public LoginForm() {

        dataBaseManager = new DataBaseManager(DatabaseSingleton.databaseURL,DatabaseSingleton.databaseUser,DatabaseSingleton.databasePass);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = String.valueOf(passwordField1.getPassword());
                System.out.println(password);
                if(dataBaseManager.CheckLogin(username,password))
                {
                    DatabaseSingleton.Username = username;
                    OpenDemoProject();
                }
                else
                    JOptionPane.showMessageDialog(null, "Please enter valid username and password");


            }
        });
    }

    public static void main(String[] args) {

        LoginForm demo = new LoginForm();
        demo.setTitle("Project");
        demo.setContentPane(demo.mainPanel);
        demo.setSize(400,400);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setVisible(true);

    }

    private void OpenDemoProject()
    {
        Demo demo = new Demo();
        demo.setTitle("Project");
        demo.setSize(800,850);
        demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        demo.setVisible(true);
        setVisible(false);
    }
}
