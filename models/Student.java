// models/Student.java
package models;

public class Student {
    private int id;
    private String name;
    private String rollNumber;
    private String department;

    public Student(int id, String name, String rollNumber, String department) {
        this.id = id;
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
    }

    public Student(String name, String rollNumber, String department) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getDepartment() { return department; }
}
