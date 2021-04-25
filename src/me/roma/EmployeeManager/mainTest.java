package me.roma.EmployeeManager;

import com.mysql.cj.util.StringUtils;
import com.sun.deploy.security.SelectableSecurityManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private JTextArea taEmpData;
    private JLabel lbSSN;
    private JTextField tfSSN;
    private JLabel lbSalary;
    private JTextField tfSalary;
    private JComboBox cbSex;
    private JTextArea taLog;
    private JButton btnRemove;
    private JButton btnRemoveEmployee;
    private JButton btnESSN;
    private JList lDep;
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
    private JTextField tfMI;
    private JLabel lbMI;
    private JButton btnSaveEmp;
    private JComboBox cbDepartment;
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
                String ssn = tfVEmpSSN.getText();

                    try {
                        if (proc.EmployeeExists(ssn)) {
                            JOptionPane.showMessageDialog(panelMain, "Employee with SSN = " + ssn + " already exists.");

                        }
                        else
                        {
                            tfFirstName.setText("");
                            tfLastName.setText("");
                            tfMgrSSN.setText("");
                            tfMI.setText("");
                            tfSSN.setText(ssn);
                            tfBirthday.setText("");
                            tfAddress.setText("");
                            cbSex.setSelectedItem("Other");
                            tfSalary.setText("");
                            tfDepartment.setText("");

                        }
                    } catch (Exception ex) {
                        taLog.append("Failed to validate Employee SSN= " + ssn + ": " + ex.getMessage() + "\n");
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
                    ArrayList<Department> departments=proc.GetDepartments();
                    cbDepartment.removeAllItems();
                    for (Department dep: departments) {
                        cbDepartment.addItem(dep.Name);
                        if ( emp.Dno==dep.Number) {

                            cbDepartment.setSelectedItem(dep.Name);
                        }

                    }
                    tfFirstName.setText(emp.Fname);
                    tfLastName.setText(emp.Lname);
                    tfMgrSSN.setText(Integer.toString(emp.Super_ssn));
                    tfMI.setText(emp.Minit);
                    tfSSN.setText(Integer.toString(emp.Ssn));
                    tfBirthday.setText(emp.Bdate);
                    tfAddress.setText(emp.Address);
                    switch (emp.Sex)
                    {
                        case "M":
                            cbSex.setSelectedItem("Male");
                            break;
                        case "F":
                            cbSex.setSelectedItem("Female");
                            break;
                        default:
                            cbSex.setSelectedItem("Other");
                            break;
                    }

                    tfSalary.setText(Integer.toString(emp.Salary));

                    if (! Objects.isNull(emp.Dependents) && emp.Dependents.size()>0) {
                    //todo - add dependent data to Dependents tab
                    }



                    taLog.append("Employee data for "+emp.Fname+" "+emp.Lname+" retrieved from database."+"\n");

                }
                catch (Exception ex)
                {
                    taLog.append("Getting employee data for SSN="+ssn+" failed: "+ex.getMessage()+"\n");
                }
            }
        });

//        btnESSN.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //JOptionPane.showMessageDialog(panelMain, "View Employee clicked");
//                taLog.append("View Dependent clicked\n");
//
//            }
//        });
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
        btnRemoveEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.append("Remove Employee clicked\n");
                String ssn = tfVEmpSSN.getText();
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete Employee with SSN = " + ssn + "?") == 0) {
                    try {
                        proc.DeleteEmployee(ssn);
                    } catch (Exception ex) {
                        taLog.append("Failed to delete Employee with SSN= " + ssn + ": " + ex.getMessage() + "\n");
                    }
                }

            }
        });
        btnSaveEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.append("Save Employee clicked\n");
                int ssn=Integer.parseInt(tfSSN.getText());
                try
                {

                    if (StringUtils.isNullOrEmpty(tfSSN.getText()))
                    {
                        JOptionPane.showMessageDialog(panelMain, "SSN field cannot be empty.");
                    }
                    else {
                        int dno=0;
                        ArrayList<Department> deps=proc.GetDepartments();
                        for (Department dep : deps
                             ) {
                            if (dep.Name== cbDepartment.getSelectedItem().toString()) {
                                dno = dep.Number;
                                break;
                            }
                        }
                        Employee emp = new Employee(tfFirstName.getText(), tfMI.getText(), tfLastName.getText(),
                                tfAddress.getText(), cbSex.getSelectedItem().toString(), tfBirthday.getText(), ssn,
                                Integer.parseInt(tfSalary.getText()), Integer.parseInt(tfMgrSSN.getText()), dno);
                        proc.updateEmployee(emp);
                        taLog.append("Employee data for SSN = " + emp.Ssn +" was updated successfully in database." + "\n");
                    }
                }
                catch (Exception ex)
                {
                    taLog.append(ex.getMessage()+"\n");
                }
            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
