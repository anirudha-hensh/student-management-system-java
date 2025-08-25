// views/AddStudentFrame.java
package views;

import dao.StudentDAO;
import models.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddStudentFrame extends JFrame {

    public AddStudentFrame() {
        setTitle("Add Student");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel rollLabel = new JLabel("Roll Number:");
        JTextField rollField = new JTextField();

        JLabel deptLabel = new JLabel("Department:");
        JTextField deptField = new JTextField();

        JButton saveBtn = new JButton("Save");

        saveBtn.addActionListener((ActionEvent e) -> {
            try {
                String name = nameField.getText();
                String roll = rollField.getText();
                String dept = deptField.getText();

                Student student = new Student(name, roll, dept);
                StudentDAO.addStudent(student);
                JOptionPane.showMessageDialog(this, "Student Added!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        add(nameLabel); add(nameField);
        add(rollLabel); add(rollField);
        add(deptLabel); add(deptField);
        add(new JLabel()); add(saveBtn);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
