🎓 Attendance Management System (Java Swing + JDBC + MySQL)

A Java-based Attendance Management System with a GUI built using Swing and database support using JDBC + MySQL.
It allows role-based login, attendance marking, student management, record viewing/editing, and CSV export.

🚀 Features

🔑 Role-Based Login – Separate dashboards for Admin and Student.

📝 Mark Attendance – Record attendance by Student ID, Date, and Status.

📊 View & Manage Records – Search, sort, update, and delete attendance.

🧑‍🎓 Student Management – Add, view, and manage student details.

📥 Export to CSV – Save attendance reports as .csv files.

🖥️ GUI with Swing – Responsive forms and tables with custom buttons.

🛠️ Tech Stack

Java 8+ (Swing) – Frontend GUI

JDBC – Database connectivity

MySQL 8+ – Backend database

MySQL Connector/J 9.3.0 – JDBC driver

Java Collections – Data handling (ArrayList, HashMap)

CSV Export – Attendance reports

📂 Project Structure
Attendance-Management-System/
│── dao/                 # Data Access Objects
│   └── StudentDAO.java
│── db/                  # Database Connection
│   └── DBConnection.java
│── Export_CSV_files/    # Exported CSV files
│   └── test.csv
│── lib/                 # External Libraries
│   └── mysql-connector-j-9.3.0.jar
│── models/              # Data Models
│   └── Student.java
│── out/                 # Build Output
│── src/                 # Core Source Files
│   ├── Main.java           # Entry point (launches MainFrame)
│   ├── Login.java
│   ├── AdminDashboard.java
│   ├── StudentDashboard.java
│   ├── MarkAttendance.java
│   ├── ViewAttendance.java
│   ├── ButtonEditor.java
│   └── ButtonRenderer.java
│── views/               # Swing UI Forms
│   ├── AddStudentFrame.java
│   ├── MainFrame.java
│   └── ViewStudentsFrame.java
│── README.md

⚙️ Setup & Installation
1️⃣ Clone the Repository
git clone https://github.com/anirudha-hensh/attendance-management-system.git
cd attendance-management-system

2️⃣ MySQL Setup

1. Start MySQL server.

2. Create database:

CREATE DATABASE attendance_db;


3. Create tables students and attendance:

CREATE TABLE students (
    student_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100),
    roll_number VARCHAR(50),
    department VARCHAR(100),
    PRIMARY KEY (student_id)
);

CREATE TABLE attendance (
    attendance_id INT NOT NULL AUTO_INCREMENT,
    student_id INT,
    date DATE,
    status ENUM('Present','Absent','Leave'),
    PRIMARY KEY (attendance_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id)
);


4. Ensure MySQL user root has password 1234 and uses mysql_native_password plugin:

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '1234';
FLUSH PRIVILEGES;

3️⃣ Configure Database Connection

Update db/DBConnection.java with your MySQL credentials:

private static final String URL = "jdbc:mysql://localhost:3306/attendance_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "1234";

4️⃣ Build & Run in VS Code / Terminal
Compile all Java files:
javac -cp "lib/mysql-connector-j-9.3.0.jar;." -d out src/*.java db/*.java dao/*.java models/*.java views/*.java

Run the application:
java -cp "lib/mysql-connector-j-9.3.0.jar;out" Main


The MainFrame will open, allowing you to:

"Log in as Admin/Student"

"Add and manage students"

"Mark attendance"

"View, update, and delete attendance records"

"Export reports to CSV"

⚠️ Common Issues

❌ Access denied: Make sure your MySQL password matches DBConnection.java.

❌ Compilation errors: Ensure Student.java getters match usage (getStudentId() not getId()).

❌ Missing JDBC driver: Ensure mysql-connector-j-9.3.0.jar exists in lib/.

🎯 Future Improvements

Add password-protected student login.

Add attendance reports by date/month.

Add graphical charts for attendance visualization.