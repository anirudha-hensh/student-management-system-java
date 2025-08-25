import dao.StudentDAO;
import db.DBConnection;
import models.Student;
import views.*;  // if needed

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MarkAttendance extends JFrame {

    private JTextField studentIdField, nameField;
    private JComboBox<String> statusCombo;
    private JButton submitButton;

    public MarkAttendance() {
        setTitle("Mark Attendance");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel idLabel = new JLabel("Student ID:");
        studentIdField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel statusLabel = new JLabel("Status:");
        String[] statusOptions = {"Present", "Absent", "Leave"};
        statusCombo = new JComboBox<>(statusOptions);

        submitButton = new JButton("Mark Attendance");
        submitButton.setBackground(new Color(0, 153, 76));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);

        submitButton.addActionListener(e -> insertAttendance());

        panel.add(idLabel);
        panel.add(studentIdField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(statusLabel);
        panel.add(statusCombo);
        panel.add(new JLabel(""));
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    private void insertAttendance() {
        String studentId = studentIdField.getText().trim();
        String name = nameField.getText().trim();
        String status = (String) statusCombo.getSelectedItem();

        if (studentId.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();  // ✅ Centralized connection
            String query = "INSERT INTO attendance (student_id, name, date, status) VALUES (?, ?, CURDATE(), ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentId);
            stmt.setString(2, name);
            stmt.setString(3, status);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Attendance marked successfully!");
                studentIdField.setText("");
                nameField.setText("");
            }

            stmt.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new MarkAttendance();
    }
}
