package me.roma.EmployeeManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JTextField textField1;
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

    public mainTest() {
        add(panelMain);
        setTitle("Employee SQL Manager");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        cbSex.addItem("Male");
        cbSex.addItem("Female");
        cbSex.addItem("Other");

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(panelMain, "Add Employee clicked");
                taLog.append("Add Employee clicked\n");
            }
        });
        btnViewEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(panelMain, "View Employee clicked");
                taLog.append("View Employee clicked\n");
            }
        });
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(panelMain, "Remove Employee clicked");
                taLog.append("Remove Employee clicked\n");

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
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
