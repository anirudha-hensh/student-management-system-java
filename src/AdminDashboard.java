import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton markBtn = new JButton("Mark Attendance");
        JButton viewBtn = new JButton("View Attendance");
        JButton logoutBtn = new JButton("Logout");

        markBtn.addActionListener(e -> new MarkAttendance().setVisible(true));
        viewBtn.addActionListener(e -> new ViewAttendance().setVisible(true));
        logoutBtn.addActionListener(e -> {
            new Login();
            dispose();
        });

        panel.add(markBtn);
        panel.add(viewBtn);
        panel.add(logoutBtn);
        add(panel);
    }
}
