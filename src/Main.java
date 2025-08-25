
import dao.StudentDAO;
import db.DBConnection;
import models.Student;
import views.*;  // if needed


import java.sql.*;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static Connection conn;

    public static void main(String[] args) {
        try {
            conn = DBConnection.getConnection();  // ✅ Use central DBConnection
            System.out.println("✅ Connected to MySQL successfully!");

            while (true) {
                System.out.println("\n===== Attendance System Menu =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Exit");
                System.out.print("Choose option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: viewStudents(); break;
                    case 3: System.out.println("👋 Exiting..."); return;
                    default: System.out.println("❌ Invalid choice.");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        }
    }

    static void addStudent() throws SQLException {
        System.out.print("Enter student name: ");
        sc.nextLine(); // consume newline
        String name = sc.nextLine();
        System.out.print("Enter roll number: ");
        String roll = sc.nextLine();

        String query = "INSERT INTO students (name, roll_no) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);
        ps.setString(2, roll);
        ps.executeUpdate();
        System.out.println("✅ Student added successfully!");
    }

    static void viewStudents() throws SQLException {
        String query = "SELECT * FROM students";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        System.out.println("\n📋 Student List:");
        while (rs.next()) {
            System.out.println(rs.getInt("student_id") + ". " + rs.getString("name") + " - " + rs.getString("roll_no"));
        }
    }
}
