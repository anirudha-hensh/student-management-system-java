import dao.StudentDAO;
import db.DBConnection;
import models.Student;
import views.*;  // if needed

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Attendance Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton markButton = new JButton("Mark Attendance");
        JButton viewButton = new JButton("View Attendance");

        markButton.setPreferredSize(new Dimension(200, 40));
        viewButton.setPreferredSize(new Dimension(200, 40));

        markButton.addActionListener(e -> {
            System.out.println("Opening MarkAttendance...");
            SwingUtilities.invokeLater(() -> new MarkAttendance());
        });

        viewButton.addActionListener(e -> {
            System.out.println("Opening ViewAttendance...");
            SwingUtilities.invokeLater(() -> {
                new ViewAttendance(); // make sure this constructor includes setVisible(true)
            });
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridy = 0;
        panel.add(markButton, gbc);
        gbc.gridy = 1;
        panel.add(viewButton, gbc);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
