package me.roma.EmployeeManager;

import java.util.List;

public class Employee {
    public String Fname, Minit, Lname, Address, Sex, Bdate, MgrName;
    public int Ssn, Salary, Super_ssn, Dno;
    public List<Dependent> Dependents;
    public Department Department;
    public Employee(){}
    public Employee(String Fname, String Minit,String Lname,String Address,String Sex,String Bdate,int Ssn,int Salary,int Super_ssn,int Dno, String MgrName)
    {
        this.MgrName=MgrName;
        this.Fname = Fname;
        this.Minit = Minit;
        this.Lname = Lname;
        this.Address = Address;
        switch (Sex)
        {
            case "Male":
                this.Sex = "M";
                break;
            case "Female":
                this.Sex = "F";
                break;
            default:
                this.Sex = "O";
                break;
        }

        this.Bdate = Bdate;
        this.Ssn = Ssn;
        this.Salary = Salary;
        this.Super_ssn = Super_ssn;
        this.Dno = Dno;
    }

}

