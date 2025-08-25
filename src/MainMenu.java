import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Attendance Management System");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel with layout and padding
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Styled buttons
        JButton markAttendanceBtn = new JButton("➕ Mark Attendance");
        markAttendanceBtn.setFont(new Font("Arial", Font.BOLD, 16));
        markAttendanceBtn.setBackground(new Color(70, 130, 180));
        markAttendanceBtn.setForeground(Color.WHITE);

        JButton viewAttendanceBtn = new JButton("📋 View Attendance");
        viewAttendanceBtn.setFont(new Font("Arial", Font.BOLD, 16));
        viewAttendanceBtn.setBackground(new Color(46, 139, 87));
        viewAttendanceBtn.setForeground(Color.WHITE);

        JButton exitBtn = new JButton("🚪 Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        exitBtn.setBackground(new Color(220, 20, 60));
        exitBtn.setForeground(Color.WHITE);

        // Add listeners
        markAttendanceBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MarkAttendance());
        });

        viewAttendanceBtn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new ViewAttendance());
        });

        exitBtn.addActionListener(e -> System.exit(0));

        // Add buttons to panel
        panel.add(markAttendanceBtn);
        panel.add(viewAttendanceBtn);
        panel.add(exitBtn);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}
