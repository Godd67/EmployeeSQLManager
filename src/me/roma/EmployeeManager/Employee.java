package me.roma.EmployeeManager;

import java.util.List;

public class Employee {
    public String Fname, Minit, Lname, Address, Sex, Bdate;
    public int Ssn, Salary, Super_ssn, Dno;
    public List<Dependent> Dependents;
    public Department Department;
    public Employee(String Fname, String Minit,String Lname,String Address,String Sex,String Bdate,int Ssn,int Salary,int Super_ssn,int Dno)
    {
        this.Fname = Fname;
        this.Minit = Minit;
        this.Lname = Lname;
        this.Address = Address;
        this.Sex = Sex;
        this.Bdate = Bdate;
        this.Ssn = Ssn;
        this.Salary = Salary;
        this.Super_ssn = Super_ssn;
        this.Dno = Dno;
    }

}

