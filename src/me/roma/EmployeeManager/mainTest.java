package me.roma.EmployeeManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class mainTest extends JFrame{
    private JPanel panelMain;
    private JButton btnAddEmployee;
    private JTabbedPane tpMain;
    private JLabel lbEmpFName;
    private JTextField tfFirstName;
    private JLabel lbEmpLName;
    private JTextField tfLastName;
    private JLabel lbAddress;
    private JTextField tfAddress;
    private JLabel lbSex;
    private JButton btnAdd;
    private JLabel lbVEmpSSN;
    private JTextField tfVEmpSSN;
    private JButton btnViewEmployee;
    private JLabel lbEmployeeData;
    private JTextArea taEmpData;
    private JLabel lbSSN;
    private JTextField tfSSN;
    private JLabel lbSalary;
    private JTextField tfSalary;
    private JComboBox cbSex;
    private JTextArea taLog;
    private JButton btnRemove;
    private JButton btnUpdateEmployee;
    private JButton btnRemoveEmployee;
    private JLabel btnDepESSN;
    private JButton btnESSN;
    private JList lDep;
    private JTextField tfESSN;
    private JLabel lbDepName;
    private JLabel lbDepSex;
    private JTextField tfDepName;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton btnSaveDep;
    private JButton newDependentButton;
    private JButton btnDepRemove;
    private JLabel lbBirthday;
    private JTextField tfBirthday;
    private JLabel lbSuperSSN;
    private JTextField tfMgrSSN;
    private JLabel lbDep;
    private JTextField tfDepartment;
    public EmployeeSQLProcessor processor;

    public mainTest(EmployeeSQLProcessor proc) {
        add(panelMain);
        setTitle("Employee SQL Manager");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        cbSex.addItem("Male");
        cbSex.addItem("Female");
        cbSex.addItem("Other");
        processor=proc;
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(panelMain, "Add Employee clicked");
                taLog.append("Add Employee clicked\n");
                try
                {
                    Employee emp=new Employee(tfFirstName.getText(),"A",tfLastName.getText(),
                            tfAddress.getText(),cbSex.getSelectedItem().toString(), tfBirthday.getText(), Integer.parseInt(tfSSN.getText()),
                            Integer.parseInt(tfSalary.getText()),Integer.parseInt(tfMgrSSN.getText()),Integer.parseInt(tfDepartment.getText()));
                    proc.addEmployee(emp);
                    taLog.append("Employee data for "+emp.Fname+" "+emp.Lname+" was added successfully to database."+"\n");
                }
                catch (Exception ex)
                {
                    taLog.append(ex.getMessage()+"\n");
                }
            }
        });
        btnViewEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(panelMain, "View Employee clicked");
                taLog.append("View Employee clicked\n");
                String ssn=tfVEmpSSN.getText();
                try
                {
                    Employee emp=proc.GetEmployee(ssn);
                    taEmpData.append("First Name: "+emp.Fname+"\n");
                    taEmpData.append("Last Name: "+emp.Lname+"\n");
                    taEmpData.append("MI: "+emp.Minit+"\n");
                    taEmpData.append("First Name: "+emp.Fname+"\n");
                    taEmpData.append("SSN: "+emp.Ssn+"\n");
                    taEmpData.append("Birthday: "+emp.Bdate+"\n");
                    taEmpData.append("Address: "+emp.Address+"\n");
                    taEmpData.append("Sex: "+emp.Sex+"\n");
                    taEmpData.append("Salary: "+emp.Salary+"\n");
                    taEmpData.append("Manager's SSN: "+emp.Super_ssn+"\n");
                    if (! Objects.isNull(emp.Department)) {
                        taEmpData.append("Department Name: " + emp.Department.Name + "\n");
                        if (! Objects.isNull(emp.Department.Locations) && emp.Department.Locations.size()>0) {

                            taEmpData.append("Locations:"+ "\n");
                            for (String s:emp.Department.Locations)
                                taEmpData.append("\t"+s+ "\n");

                        }

                    }

                    taLog.append("Employee data for "+emp.Fname+" "+emp.Lname+" retrieved from database."+"\n");

                }
                catch (Exception ex)
                {
                    taLog.append("Getting employee data for SSN="+ssn+" failed: "+ex.getMessage()+"\n");
                }
            }
        });

        btnESSN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(panelMain, "View Employee clicked");
                taLog.append("View Dependent clicked\n");

            }
        });
        btnSaveDep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.append("Save Dependent clicked\n");
            }
        });
        newDependentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.append("New Dependent clicked\n");
            }
        });
        btnDepRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.append("Remove Dependent clicked\n");
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
