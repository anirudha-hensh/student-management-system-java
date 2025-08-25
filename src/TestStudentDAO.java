import dao.StudentDAO;
import models.Student;

import java.util.List;

public class TestStudentDAO {
    public static void main(String[] args) {
        try {
            // Insert a new student
            Student s1 = new Student(0, "Anirudha Hensh", "CSE101", "CSE");
            StudentDAO.addStudent(s1);
            System.out.println("✅ Student inserted successfully!");

            // Fetch all students
            List<Student> students = StudentDAO.getAllStudents();
            System.out.println("📋 Student List:");
            for (Student s : students) {
                System.out.println(s.getStudentId() + " | " + s.getName() + " | " + s.getRollNo() + " | " + s.getDepartment());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
