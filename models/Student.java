package models;

public class Student {
    private int studentId;
    private String name;
    private String rollNumber;
    private String department;

    // Constructor with ID (for reading from DB)
    public Student(int studentId, String name, String rollNumber, String department) {
        this.studentId = studentId;
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
    }

    // Constructor without ID (for inserting new student)
    public Student(String name, String rollNumber, String department) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
    }

    // ✅ Getters
    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNumber;
    }

    public String getDepartment() {
        return department;
    }
}
