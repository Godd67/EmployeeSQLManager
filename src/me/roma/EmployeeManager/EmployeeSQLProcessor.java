package me.roma.EmployeeManager;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
public class EmployeeSQLProcessor {
    private  static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;


    public static DataSource  getSource()
    {
        MysqlDataSource source = new MysqlDataSource();
        source.setServerName("localhost");
        source.setPort(3306);
        source.setDatabaseName("company");
        source.setUser("root");
        source.setPassword("1234");
        return source;
    }

    public static Connection getConnection()
    {
        if(connect == null)
        {
            try
            {
                connect = getSource().getConnection();
                return connect;
            }
            catch  (SQLException e)
            {
                e.printStackTrace();;
            }
        }
        return connect;
    }

    public  Connector(){
        JFrame frame = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();
        JButton button = new JButton("Click me");
        JLabel label = new JLabel("Number of clicks");
        JPanel panel = new JPanel();
        JPanel addEmployee = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout(new GridLayout(0,1));
        panel.add(button);
        panel.add(label);

        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Management Software");
        frame.pack();
        frame.setVisible(true);
    }

    public void addEmployee(Employee emp)
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/company","root","1234");
            //here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            int rs=stmt.executeUpdate("insert Employee (Fname,Minit,Lname,Ssn,Bdate,Address,Sex,Salary,Super_ssn,Dno) values " +
                    "('"+emp.Fname + "','"
                    +emp.Minit + "','"
                    +emp.Lname + "',"
                    +Integer.toString(emp.Ssn) + ",'"
                    +emp.Bdate + "','"
                    +emp.Address + "','"
                    +emp.Sex + "',"
                    +Integer.toString(emp.Salary) + ","
                    +Integer.toString(emp.Super_ssn) + ","
                    +Integer.toString(emp.Dno) + ")");

        }catch(Exception e){ System.out.println(e);}
    }



}