// views/ViewStudentsFrame.java
package views;

import dao.StudentDAO;
import models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewStudentsFrame extends JFrame {

    public ViewStudentsFrame() {
        setTitle("All Students");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = { "ID", "Name", "Roll No", "Department" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try {
            List<Student> students = StudentDAO.getAllStudents();
            for (Student s : students) {
                model.addRow(new Object[]{
                       s.getStudentId(), s.getName(), s.getRollNo(), s.getDepartment()

                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
