package me.roma.EmployeeManager;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame mainFrame=new mainTest();
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //mainFrame.pack(); -uncomment it if you like compact window view
                mainFrame.setVisible(true);
            }
        });

    }
}
