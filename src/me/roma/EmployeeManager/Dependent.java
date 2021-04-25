package me.roma.EmployeeManager;

public class Dependent {
    public Dependent(){};
    public Dependent(int Essn, String name, String Sex, String Bdate, String Relationship)
    {
        this.Essn = Essn;
        this.Name = name;
        this.Sex = Sex;
        this.Bdate = Bdate;
        this.Relationship = Relationship;
    }
    public int Essn;
    public String Name, Sex, Bdate, Relationship;
    public String toString() {
        return Name;
    }
}
