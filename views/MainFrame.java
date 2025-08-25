// views/MainFrame.java
package views;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Attendance Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton addStudentBtn = new JButton("Add Student");
        JButton viewStudentsBtn = new JButton("View Students");

        addStudentBtn.addActionListener((ActionEvent e) -> new AddStudentFrame());
        viewStudentsBtn.addActionListener((ActionEvent e) -> new ViewStudentsFrame());

        JPanel panel = new JPanel();
        panel.add(addStudentBtn);
        panel.add(viewStudentsBtn);

        add(panel);
        setVisible(true);
    }
}
