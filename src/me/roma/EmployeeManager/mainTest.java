package me.roma.EmployeeManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
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
    private DefaultListModel modelAddList = new DefaultListModel();
    private JList lDep;
    private JTextField tfESSN;
    private JLabel lbDepName;
    private JLabel lbDepSex;
    private JTextField tfDepName;
    private JTextField tfSex;
    private JTextField tfBday;
    private JTextField tfRelationship;
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
        lDep.setModel(modelAddList);
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
                modelAddList.removeAllElements();
                taLog.append("View Dependent clicked\n");
                String essn=tfESSN.getText();
                try
                {
                    List<Dependent> Dependents=proc.GetDependents(essn);
                    for(int i = 0; i<Dependents.size(); i++){
                        modelAddList.addElement(Dependents.get(i));
                        //taLog.append("Dependents size = " + Dependents.size());
                        taLog.append("Dependent data for "+Dependents.get(i)+" retrieved from database."+"\n");
                    }
                    tfDepName.setText("");
                    tfSex.setText("");
                    tfBday.setText("");
                    tfRelationship.setText("");
//                    if (! Objects.isNull(emp.Department)) {
//                        taEmpData.append("Department Name: " + emp.Department.Name + "\n");
//                        if (! Objects.isNull(emp.Department.Locations) && emp.Department.Locations.size()>0) {
//
//                            taEmpData.append("Locations:"+ "\n");
//                            for (String s:emp.Department.Locations)
//                                taEmpData.append("\t"+s+ "\n");
//
//                        }
//
//                    }



                }
                catch (Exception ex)
                {
                    taLog.append("Getting dependent data for ESSN="+essn+" failed: "+ex.getMessage()+"\n");
                }

            }
        });
        lDep.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if(lDep.getSelectedValue() instanceof Dependent)
                {
                    Dependent dep = (Dependent) lDep.getSelectedValue();
                    String name = dep.Name;
                    String Sex = dep.Sex;
                    String Bdate = dep.Bdate;
                    String Relationship = dep.Relationship;
                    tfDepName.setText(name);
                    tfSex.setText(Sex);
                    tfBday.setText(Bdate);
                    tfRelationship.setText(Relationship);
                }
            }
        });
        btnSaveDep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO call "Exists" method in proc to see if the table has an
                //entry with the correct essn and name, if not then call addDependent;
                taLog.append("Save Dependent clicked\n");
                String name = tfDepName.getText();
                String sex = tfSex.getText();
                String Bdate = tfBday.getText();
                String relationship = tfRelationship.getText();
                String essn = tfESSN.getText();
                try {
                    if (proc.dependentExists(essn,name))
                    {
                        proc.modifyDependent(essn,name,sex,Bdate,relationship);
                    }
                    else
                    {
                        Dependent dep = new Dependent(Integer.parseInt(essn),name,sex,Bdate,relationship);
                        proc.addDependent(dep);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
        newDependentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.append("New Dependent clicked\n");
                modelAddList.addElement("New Dependent");
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
