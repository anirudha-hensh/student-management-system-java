import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

public class MarkAttendance extends JFrame {

    static final String DB_URL = "jdbc:mysql://localhost:3306/attendance_db";
    static final String USER = "root";
    static final String PASS = "root1234";

    JTextField studentIdField, nameField, dateField;
    JComboBox<String> statusBox;

    public MarkAttendance() {
        setTitle("Mark Attendance");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Student ID
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        studentIdField = new JTextField(20);
        panel.add(studentIdField, gbc);

        // Student Name
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        panel.add(nameField, gbc);

        // Date
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1;
        dateField = new JTextField(LocalDate.now().toString());
        dateField.setEditable(false);
        panel.add(dateField, gbc);

        // Status Dropdown
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        statusBox = new JComboBox<>(new String[]{"Present", "Absent", "Leave"});
        panel.add(statusBox, gbc);

        // Submit Button
        JButton submitBtn = new JButton("Submit");
        submitBtn.setBackground(new Color(0, 123, 255));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        submitBtn.setFocusPainted(false);
        submitBtn.addActionListener(e -> insertAttendance());

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(submitBtn, gbc);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void insertAttendance() {
        String studentId = studentIdField.getText().trim();
        String name = nameField.getText().trim();
        String date = dateField.getText().trim();
        String status = (String) statusBox.getSelectedItem();

        if (studentId.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID and Name are required.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO attendance (student_id, name, date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentId);
            stmt.setString(2, name);
            stmt.setString(3, date);
            stmt.setString(4, status);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Attendance marked successfully.");
                studentIdField.setText("");
                nameField.setText("");
                statusBox.setSelectedIndex(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred.");
        }
    }

   public static void main(String[] args) {
        new MarkAttendance();
    }
}
