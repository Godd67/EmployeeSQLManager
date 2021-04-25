package me.roma.EmployeeManager;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class EmployeeSQLProcessor {
    private  static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;
    public ArrayList<Department> Departments;

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

    public  EmployeeSQLProcessor(){

    }

    public void addEmployee(Employee emp) throws SQLException, ClassNotFoundException {
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

        }catch(Exception e){ System.out.println(e); throw e; }
    }
    public void updateEmployee(Employee emp) throws SQLException, ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/company","root","1234");
            //here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            String sql="update Employee set Fname = '" +emp.Fname + "'"+
                    ",Minit='"+emp.Minit + "'"+
                    ",Lname='"+emp.Lname + "'"+
                    ",Bdate='"+emp.Bdate + "'"+
                    ",Address='"+emp.Address + "'"+
                    ",Sex='"+emp.Sex + "'"+
                    ",Salary="+Integer.toString(emp.Salary) +
                    ",Super_ssn="+Integer.toString(emp.Super_ssn) +
                    ",Dno="+Integer.toString(emp.Dno) +
                    " where ssn="+emp.Ssn;
            int rs=stmt.executeUpdate(sql);

        }catch(Exception e){ System.out.println(e); throw e; }
    }
    public boolean EmployeeExists(String ssn) throws SQLException, ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/company","root","1234");
            //here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            ResultSet employee=stmt.executeQuery("select ssn from Employee where ssn="+ssn+" limit 1");
            if (employee.next()) {
                return true;
            }
            else
            {
                return false;
            }


        }catch(Exception e){ System.out.println(e); throw e;}
    }

    public void DeleteEmployee(String ssn) throws SQLException, ClassNotFoundException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/company","root","1234");
            //here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            stmt.executeUpdate("delete from Employee where ssn="+ssn);
            stmt.executeUpdate("delete from Dependent where essn="+ssn);



        }catch(Exception e){ System.out.println(e); throw e;}
    }

    public ArrayList<Department> GetDepartments() throws SQLException, ClassNotFoundException
    {
        if (!Objects.isNull(Departments))
            return Departments;
        Departments =new ArrayList<Department>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/company","root","1234");
            //here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            ResultSet deps=stmt.executeQuery("select * from Department");
            while (deps.next()) {
                Department dep=new Department();
                dep.Name=deps.getString("Dname");
                dep.Number=deps.getInt("Dnumber");
                dep.Mgr_ssn=deps.getInt("Mgr_ssn");
                dep.Mgr_start_date=deps.getDate("Mgr_start_date").toString();
                Departments.add(dep);

                }

            return Departments;

        }catch(Exception e){ System.out.println(e); throw e;}
    }


    public Employee GetEmployee(String ssn) throws SQLException, ClassNotFoundException {
        Employee emp=new Employee();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/company","root","1234");
            //here sonoo is database name, root is username and password
            Statement stmt=con.createStatement();
            ResultSet employees=stmt.executeQuery("select * from Employee where ssn="+ssn+" limit 1");
            if (employees.next()) {
                emp.Fname=employees.getString("Fname");
                emp.Minit=employees.getString("Minit");
                emp.Lname=employees.getString("Lname");
                emp.Ssn=Integer.parseInt(ssn);
                emp.Bdate=employees.getDate("Bdate").toString();
                emp.Address=employees.getString("Address");
                emp.Sex=employees.getString("Sex");
                emp.Salary=employees.getInt("Salary");
                emp.Super_ssn=employees.getInt("Super_ssn");
                emp.Dno=employees.getInt("Dno");
                ResultSet dep=stmt.executeQuery("select * from Department where Mgr_ssn="+Integer.toString(emp.Dno)+" limit 1");
                if (dep.next())
                {
                    emp.Department=new Department();
                    emp.Department.Name=dep.getString("Name");
                    emp.Department.Mgr_ssn=dep.getInt(3);
                    emp.Department.Mgr_start_date=dep.getDate(4).toString();
                    ResultSet loc=stmt.executeQuery("select * from dept_locations where Dnumber="+Integer.toString(emp.Dno)+" limit 1");
                    emp.Department.Locations=new ArrayList<String>();


                    while (loc.next())
                    {
                        emp.Department.Locations.add(loc.getString(2));
                    }
                }
                //emp.Fname=employees.getString(1);

                emp.Dependents=new ArrayList<Dependent>();
                ResultSet deps = stmt.executeQuery("select * from Dependent where essn='" + ssn + "'");
                while (deps.next()) {
                    Dependent depend= new Dependent();
                    depend.Name=deps.getString(2);
                    depend.Sex=deps.getString(3);
                    depend.Bdate=deps.getDate(4).toString();
                    depend.Relationship=deps.getString(5);
                    emp.Dependents.add(depend);

                }

            }
            return emp;

        }catch(Exception e){ System.out.println(e); throw e;}
    }



}
