import dao.StudentDAO;
import db.DBConnection;
import models.Student;
import views.*;  // if needed

import javax.swing.*;

public class StudentDashboard extends JFrame {
    private String studentId;

    public StudentDashboard(String studentId) {
        this.studentId = studentId;

        setTitle("Student Dashboard");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton viewAttendanceBtn = new JButton("View My Attendance");
       viewAttendanceBtn.addActionListener(e -> new ViewAttendance().setVisible(true)); // ✅ Pass studentId

        add(viewAttendanceBtn);
    }
}

