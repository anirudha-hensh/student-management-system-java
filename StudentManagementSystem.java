import java.io.*;
import java.util.*;

class Student {
    int id;
    String name;
    int age;
    String course;

    Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    void display() {
        System.out.println("ID: " + id + ", Name: " + name +
                ", Age: " + age + ", Course: " + course);
    }

    // Convert to string for saving in file
    String toFileString() {
        return id + "," + name + "," + age + "," + course;
    }

    // Convert from string line in file
    static Student fromFileString(String line) {
        String[] parts = line.split(",");
        return new Student(Integer.parseInt(parts[0]), parts[1],
                Integer.parseInt(parts[2]), parts[3]);
    }
}

public class StudentManagementSystem {
    static ArrayList<Student> students = new ArrayList<>();
    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadStudents(); // Load previous data

        int choice;
        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Delete Student by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent(sc);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    searchStudent(sc);
                    break;
                case 4:
                    deleteStudent(sc);
                    break;
                case 5:
                    saveStudents(); // Save before exiting
                    System.out.println("Exiting... Data saved!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);

        sc.close();
    }

    // Add Student
    static void addStudent(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        students.add(new Student(id, name, age, course));
        saveStudents(); // save immediately
        System.out.println("Student added successfully!");
    }

    // View all students
    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
        } else {
            System.out.println("\n--- Student List ---");
            for (Student s : students) {
                s.display();
            }
        }
    }

    // Search by ID
    static void searchStudent(Scanner sc) {
        System.out.print("Enter ID to search: ");
        int searchId = sc.nextInt();
        boolean found = false;
        for (Student s : students) {
            if (s.id == searchId) {
                s.display();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student not found!");
        }
    }

    // Delete student
    static void deleteStudent(Scanner sc) {
        System.out.print("Enter ID to delete: ");
        int deleteId = sc.nextInt();
        boolean removed = false;

        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            Student s = it.next();
            if (s.id == deleteId) {
                it.remove();
                removed = true;
                saveStudents(); // save immediately
                System.out.println("Student deleted successfully!");
                break;
            }
        }

        if (!removed) {
            System.out.println("Student not found!");
        }
    }

    // Save data to file
    static void saveStudents() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                pw.println(s.toFileString());
            }
            System.out.println("Data saved to: " + new File(FILE_NAME).getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load data from file
    static void loadStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return; // no file yet

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                students.add(Student.fromFileString(line));
            }
            System.out.println("Loaded data from: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
