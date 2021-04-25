package me.roma.EmployeeManager;

import com.mysql.cj.util.StringUtils;
import com.sun.deploy.security.SelectableSecurityManager;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
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
    private JTextField tfDepSex;
    private JTextField tfDepBday;
    private JTextField tfDepRel;
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
    private JLabel lbMgrName;
    private JTextField tfMgrName;
    private JCheckBox cbEdit;
    private JCheckBox cbDepEdit;
    public EmployeeSQLProcessor processor;
    private boolean newEmployee;
    private boolean newDependent;
    private DefaultListModel<Dependent> modelAddList = new DefaultListModel();

    public mainTest(EmployeeSQLProcessor proc) {
        add(panelMain);
        setTitle("Employee SQL Manager");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        cbSex.addItem("Male");
        cbSex.addItem("Female");
        cbSex.addItem("Other");
        lDep.setModel(modelAddList);
        processor=proc;
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(panelMain, "Add Employee clicked");
                taLog.append("Add Employee clicked\n");
                String ssn = tfVEmpSSN.getText();
                if (StringUtils.isNullOrEmpty(ssn)) {
                    JOptionPane.showMessageDialog(panelMain, "Please provide new Employee's SSN.");
                    return;
                }
                    try {
                        if (  proc.EmployeeExists(ssn)) {
                            JOptionPane.showMessageDialog(panelMain, "Employee with SSN = " + ssn + " already exists.");

                        }
                        else
                        {
                            ArrayList<Department> departments=proc.GetDepartments();
                            cbDepartment.removeAllItems();
                            for (Department dep: departments) {
                                cbDepartment.addItem(dep.Name);


                            }
                            newEmployee=true;
                            cbEdit.setEnabled(true);
                            cbEdit.setSelected(true);

                            tfFirstName.setText("");
                            tfLastName.setText("");
                            tfMgrSSN.setText("");
                            tfMI.setText("");
                            tfSSN.setText(ssn);
                            tfBirthday.setText("");
                            tfAddress.setText("");
                            cbSex.setSelectedItem("Other");
                            tfSalary.setText("");


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
                    if (emp.Ssn==0) {
                        if (JOptionPane.showConfirmDialog(null, "Employee with SSN = " + ssn + " does not exist. Do you want to add new?") == 0) {
                            btnAddEmployee.doClick();
                        } else
                            return;
                    }
                    newEmployee=false;
                    cbEdit.setEnabled(true);
                    cbDepEdit.setEnabled(true);
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
                    tfMgrName.setText(emp.MgrName);
                    tfSalary.setText(Integer.toString(emp.Salary));

                    if (! Objects.isNull(emp.Dependents) && emp.Dependents.size()>0) {
                        modelAddList.removeAllElements();
                        taLog.append("View Dependent clicked\n");
                        String essn=tfVEmpSSN.getText();
                        try
                        {

                            for(int i = 0; i<emp.Dependents.size(); i++){
                                modelAddList.addElement(emp.Dependents.get(i));
                                //taLog.append("Dependents size = " + Dependents.size());
                                //taLog.append("Dependent data for "+emp.Dependents.get(i)+" retrieved from database."+"\n");
                            }
                            tfDepName.setText("");
                            tfDepSex.setText("");
                            tfDepBday.setText("");
                            tfDepRel.setText("");



                        }
                        catch (Exception ex)
                        {
                            taLog.append("Getting dependent data for ESSN="+essn+" failed: "+ex.getMessage()+"\n");
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

                String name = tfDepName.getText();
                String sex = tfDepSex.getText();
                String Bdate = tfDepBday.getText();
                String relationship = tfDepRel.getText();
                String essn = tfVEmpSSN.getText();
                try {
                    if (proc.dependentExists(essn,name))
                    {
                        proc.modifyDependent(essn,name,sex,Bdate,relationship);
                    }
                    else
                    {
                        Dependent dep = new Dependent(Integer.parseInt(essn),name,sex,Bdate,relationship);
                        proc.addDependent(dep);
                        //modelAddList.remove(modelAddList.f"New dependent");
                        modelAddList.addElement(dep);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    taLog.append(throwables.getMessage());
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                    taLog.append(classNotFoundException.getMessage());
                }
            }
        });
        newDependentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.append("New Dependent clicked\n");
                Dependent newDep=new Dependent();
                newDep.Name="New Dependent";
                modelAddList.addElement(newDep);

                newDependent=true;
                tfDepName.setText(newDep.Name);
                cbDepEdit.setSelected(true);
            }
        });
        btnDepRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.append("Remove Dependent clicked\n");
                String ssn = tfVEmpSSN.getText();
                if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete Dependent "+ tfDepName.getText()+ "?") == 0) {
                    try {
                        proc.DeleteDependent(ssn, tfDepName.getText());
                        for (int i=0;i<modelAddList.size();i++)
                        if (((Dependent) modelAddList.get(i)).Name==tfDepName.getText())
                            modelAddList.remove(i);
                    } catch (Exception ex) {
                        taLog.append("Failed to delete Dependent with SSN= " + ssn + ": " + ex.getMessage() + "\n");
                    }
                }
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
                    tfDepSex.setText(Sex);
                    tfDepBday.setText(Bdate);
                    tfDepRel.setText(Relationship);
                }
            }
        });
        btnSaveEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.append("Save Employee clicked\n");
                int ssn=Integer.parseInt(tfVEmpSSN.getText());
                try
                {

                    if (ssn==0)
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
                                Integer.parseInt(tfSalary.getText()), Integer.parseInt(tfMgrSSN.getText()), dno, tfMgrName.getText());
                        if (newEmployee)
                        {
                            proc.addEmployee(emp);
                            taLog.append("Employee data for SSN = " + emp.Ssn +" was added successfully to database." + "\n");
                        }
                         else {
                            proc.updateEmployee(emp);
                            taLog.append("Employee data for SSN = " + emp.Ssn + " was updated successfully in database." + "\n");
                        }
                        cbEdit.setSelected(false);
                    }
                }
                catch (Exception ex)
                {
                    taLog.append(ex.getMessage()+"\n");
                }
            }
        });
        cbEdit.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    tfSalary.setEditable(true);
                    cbSex.setEditable(true);
                    cbDepartment.setEditable(true);
                    tfMgrSSN.setEditable(true);
                    tfAddress.setEditable(true);
                    if (newEmployee)
                    {
                        tfFirstName.setEditable(true);
                        tfLastName.setEditable(true);
                        tfMgrSSN.setEditable(true);
                        tfMI.setEditable(true);
                        //tfSSN.setText(ssn);
                        tfBirthday.setEditable(true);
                        tfAddress.setEditable(true);
                        cbSex.setEditable(true);
                        tfSalary.setEditable(true);
                        cbDepartment.setEditable(true);
                    }
                    else
                        proc.LockEmployee(Integer.parseInt(tfVEmpSSN.getText()));
                } else {//checkbox has been deselected
                    tfSalary.setEditable(false);
                    cbSex.setEditable(false);
                    cbDepartment.setEditable(false);
                    tfMgrSSN.setEditable(false);
                    tfAddress.setEditable(false);
                    tfFirstName.setEditable(false);
                    tfLastName.setEditable(false);
                    tfMgrSSN.setEditable(false);
                    tfMI.setEditable(false);
                    //tfSSN.setText(ssn);
                    tfBirthday.setEditable(false);
                    tfAddress.setEditable(false);
                    cbSex.setEditable(false);
                    tfSalary.setEditable(false);
                    cbDepartment.setEditable(false);
                };
            }
        });
        cbDepEdit.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    tfDepBday.setEditable(true);
                    tfDepBday.setEditable(true);
                    tfDepRel.setEditable(true);
                    tfDepSex.setEditable(true);
                    if (newDependent)
                        tfDepName.setEditable(true);
                }
                else
                {
                    tfDepBday.setEditable(false);
                    tfDepBday.setEditable(false);
                    tfDepRel.setEditable(false);
                    tfDepSex.setEditable(false);
                    tfDepName.setEditable(false);
                }

            }
        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
