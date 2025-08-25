// dao/StudentDAO.java
package dao;

import db.DBConnection;
import models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public static void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (name, roll_number, department) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getRollNumber());
            stmt.setString(3, student.getDepartment());
            stmt.executeUpdate();
        }
    }

    public static List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Statement stmt = DBConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("roll_number"),
                        rs.getString("department")
                ));
            }
        }
        return list;
    }
}
